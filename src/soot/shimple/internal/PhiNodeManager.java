/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Navindra Umanee <navindra@cs.mcgill.ca>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package soot.shimple.internal;

import soot.*;
import soot.jimple.AssignStmt;
import soot.jimple.Jimple;
import soot.shimple.PhiExpr;
import soot.shimple.Shimple;
import soot.shimple.ShimpleBody;
import soot.shimple.ShimpleFactory;
import soot.toolkits.graph.*;
import soot.toolkits.scalar.GuaranteedDefs;
import soot.toolkits.scalar.ValueUnitPair;
import soot.util.Chain;
import soot.util.HashMultiMap;
import soot.util.MultiMap;

import java.util.*;

/**
 * @author Navindra Umanee
 * @see soot.shimple.ShimpleBody
 * @see <a
 * href="http://citeseer.nj.nec.com/cytron91efficiently.html">Efficiently
 * Computing Static Single Assignment Form and the Control Dependence
 * Graph</a>
 **/
public class PhiNodeManager
{
    protected ShimpleBody body;
    protected ShimpleFactory sf;
    private DominatorTree<Block> dt;
    private DominanceFrontier<Block> df;
    protected BlockGraph cfg;

    PhiNodeManager(ShimpleBody body, ShimpleFactory sf)
    {
        this.body = body;
        this.sf = sf;
    }

    public void update()
    {
        new GuaranteedDefs(sf.getUnitGraph());
        cfg = sf.getBlockGraph();
        dt = sf.getDominatorTree();
        df = sf.getDominanceFrontier();
    }

    private MultiMap<Local, Block> varToBlocks;
    
    /**
     * Phi node Insertion Algorithm from Cytron et al 91, P24-5,
     *
     * <p>Special Java case: If a variable is not defined along all
     * paths of entry to a node, a Phi node is not needed.</p>
     **/
    boolean insertTrivialPhiNodes()
    {
		update();
		boolean change = false;
		varToBlocks = new HashMultiMap<>();
		Map<Local, List<Block>> localsToDefPoints = new HashMap<>();

		// compute localsToDefPoints and varToBlocks
		for (Block block : cfg) {
			for (Unit unit : block) {
				List<ValueBox> defBoxes = unit.getDefBoxes();
				for (ValueBox vb : defBoxes) {
					Value def = vb.getValue();
					if (def instanceof Local) {
						Local local = (Local) def;
						List<Block> def_points;
						if (localsToDefPoints.containsKey(local)) {
							def_points = localsToDefPoints.get(local);
						} else {
							def_points = new ArrayList<>();
							localsToDefPoints.put(local, def_points);
						}
						def_points.add(block);
					}
				}

				if (Shimple.isPhiNode(unit))
					varToBlocks.put(Shimple.getLhsLocal(unit), block);
			}
		}

        /* Routine initialisations. */
        
        int[] workFlags = new int[cfg.size()];
        int iterCount = 0;
        Stack<Block> workList = new Stack<>();

        Map<Integer, Integer> has_already = new HashMap<>();
        for (Block block : cfg) {
            has_already.put(block.getIndexInMethod(), 0);
        }

        /* Main Cytron algorithm. */
        
        {
        	for (Local local : localsToDefPoints.keySet()) {
                iterCount++;

                // initialise worklist
                {
                    List<Block> def_points = localsToDefPoints.get(local);
                    //if the local is only defined once, no need for phi nodes
                    if(def_points.size() == 1){
                      continue;
                    }
                    for(Block block : def_points){
                        workFlags[block.getIndexInMethod()] = iterCount;
                        workList.push(block);
                    }
                }

                while(!workList.empty()){
                    Block block = workList.pop();
                    DominatorNode<Block> node = dt.getDode(block);

                    for (DominatorNode<Block> blockDominatorNode : df.getDominanceFrontierOf(node)) {
                        Block frontierBlock = blockDominatorNode.getGode();
                        int fBIndex = frontierBlock.getIndexInMethod();

                        Iterator<Unit> unitsIt = frontierBlock.iterator();
                        if (!unitsIt.hasNext()) {
                            continue;
                        }

                        if (has_already.get(frontierBlock.getIndexInMethod()) < iterCount) {
                            has_already.put(frontierBlock.getIndexInMethod(), iterCount);
                            prependTrivialPhiNode(local, frontierBlock);
                            change = true;

                            if (workFlags[fBIndex] < iterCount) {
                                workFlags[fBIndex] = iterCount;
                                workList.push(frontierBlock);
                            }
                        }
                    }
                }
            }
        }

        return change;
    }

    /**
     * Inserts a trivial Phi node with the appropriate number of
     * arguments.
     **/
    private void prependTrivialPhiNode(Local local, Block frontierBlock)
    {
        List<Block> preds = frontierBlock.getPreds();
        PhiExpr pe = Shimple.getInstance().newPhiExpr(local, preds);
        pe.setBlockId(frontierBlock.getIndexInMethod());
        Unit trivialPhi = Jimple.newAssignStmt(local, pe);
        Unit blockHead = frontierBlock.getHead();

        // is it a catch block?
        if(blockHead instanceof IdentityUnit)
            frontierBlock.insertAfter(trivialPhi, frontierBlock.getHead());
        else
            frontierBlock.insertBefore(trivialPhi, frontierBlock.getHead());

        varToBlocks.put(local, frontierBlock);
    }

    /**
     * Exceptional Phi nodes have a huge number of arguments and control
     * flow predecessors by default.  Since it is useless trying to keep
     * the number of arguments and control flow predecessors in synch,
     * we might as well trim out all redundant arguments and eliminate
     * a huge number of copy statements when we get out of SSA form in
     * the process.
     **/
    void trimExceptionalPhiNodes()
    {
        Set<Unit> handlerUnits = new HashSet<>();

        for (Trap trap : body.getTraps()) {
            handlerUnits.add(trap.getHandlerUnit());
        }

        for (Block block : cfg) {
            // trim relevant Phi expressions
            if(handlerUnits.contains(block.getHead())){
            	for (Unit unit : block) {
                    //if(!(newPhiNodes.contains(unit)))
                    PhiExpr phi = Shimple.getPhiExpr(unit);

                    if(phi == null)
                        continue;

                    trimPhiNode(phi);
                }
            }
        }
    }

    /**
     * @see #trimExceptionalPhiNodes()
     **/
    private void trimPhiNode(PhiExpr phiExpr)
    {
        /* A value may appear many times in an exceptional Phi. Hence,
           the same value may be associated with many UnitBoxes. We
           build the MultiMap valueToPairs for convenience.  */

        MultiMap<Value, ValueUnitPair> valueToPairs = new HashMultiMap<Value, ValueUnitPair>();
        for (ValueUnitPair argPair : phiExpr.getArgs()) {
            Value value = argPair.getValue();
            valueToPairs.put(value, argPair);
        }

        /* Consider each value and see if we can find the dominating
           UnitBoxes.  Once we have found all the dominating
           UnitBoxes, the rest of the redundant arguments can be
           trimmed.  */

        for (Value value : valueToPairs.keySet()) {
            // although the champs list constantly shrinks, guaranteeing
            // termination, the challengers list never does.  This could
            // be optimised.
            Set<ValueUnitPair> pairsSet = valueToPairs.get(value);
            List<ValueUnitPair> champs = new LinkedList<ValueUnitPair>(pairsSet);
            List<ValueUnitPair> challengers = new LinkedList<ValueUnitPair>(pairsSet);

            // champ is the currently assumed dominator
            ValueUnitPair champ = champs.remove(0);
            Unit champU = champ.getUnit();

            // hopefully everything will work out the first time, but
            // if not, we will have to try a new champion just in case
            // there is more that can be trimmed.
            boolean retry = true;
            while (retry) {
                retry = false;

                // go through each challenger and see if we dominate them
                // if not, the challenger becomes the new champ
                for (Iterator<ValueUnitPair> iterator = challengers.iterator(); iterator.hasNext(); ) {
                    ValueUnitPair challenger = iterator.next();
                    if (challenger.equals(champ))
                        continue;
                    Unit challengerU = challenger.getUnit();

                    // kill the challenger
                    if (dominates(champU, challengerU)) {
                        phiExpr.removeArg(challenger);
                        iterator.remove();
                    }
                    // we die, find a new champ
                    else if (dominates(challengerU, champU)) {
                        phiExpr.removeArg(champ);
                        champ = challenger;
                        champU = champ.getUnit();
                    }

                    // neither wins, oops!  we'll have to try the next
                    // available champ at the next pass.  It may very
                    // well be inevitable that we will have two
                    // identical value args in an exceptional PhiExpr,
                    // but the more we can trim the better.
                    else
                        retry = true;
                }

                if (retry) {
                    if (champs.isEmpty())
                        break;
                    champ = champs.remove(0);
                    champU = champ.getUnit();
                }
            }
        }
    }
    
    private Map<Unit, Block> unitToBlock;

    /**
     * Returns true if champ dominates challenger.  Note that false
     * doesn't necessarily mean that challenger dominates champ.
     **/
    private boolean dominates(Unit champ, Unit challenger)
    {
        if(champ == null || challenger == null)
            throw new RuntimeException("Assertion failed.");
        
        // self-domination
        if(champ.equals(challenger))
            return true;
        
        if(unitToBlock == null)
            unitToBlock = getUnitToBlockMap(cfg);

        Block champBlock = unitToBlock.get(champ);
        Block challengerBlock = unitToBlock.get(challenger);

        if(champBlock.equals(challengerBlock)){

            for (Unit unit : champBlock) {
                if (unit.equals(champ))
                    return true;
                if (unit.equals(challenger))
                    return false;
            }

            throw new RuntimeException("Assertion failed.");
        }

        DominatorNode<Block> champNode = dt.getDode(champBlock);
        DominatorNode<Block> challengerNode = dt.getDode(challengerBlock);
        
        return(dt.isDominatorOf(champNode, challengerNode));
    }

    /**
     * Convenience function that maps units to blocks.  Should
     * probably be in BlockGraph.
     **/
    private Map<Unit, Block> getUnitToBlockMap(BlockGraph blocks)
    {
        Map<Unit, Block> unitToBlock = new HashMap<>();

        for (Block block : blocks) {
            for (Unit unit : block) {
                unitToBlock.put(unit, block);
            }
        }

        return unitToBlock;
    }
}
