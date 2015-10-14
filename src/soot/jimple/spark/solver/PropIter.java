/* Soot - a J*va Optimization Framework
 * Copyright (C) 2002 Ondrej Lhotak
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

package soot.jimple.spark.solver;

import soot.G;
import soot.jimple.spark.pag.*;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.jimple.spark.sets.PointsToSetInternal;
import soot.util.queue.QueueReader;

import java.util.TreeSet;

/**
 * Propagates points-to sets along pointer assignment graph using iteration.
 *
 * @author Ondrej Lhotak
 */

public final class PropIter extends Propagator {
    protected PAG pag;

    public PropIter(PAG pag) {
        this.pag = pag;
    }

    /* End of public methods. */
    /* End of package methods. */

    /**
     * Actually does the propagation.
     */
    public final void propagate() {
        final OnFlyCallGraph ofcg = pag.getOnFlyCallGraph();
        new TopoSorter(pag, false).sort();
        for (Object object : pag.allocSources()) {
            handleAllocNode((AllocNode) object);
        }
        int iteration = 1;
        boolean change;
        do {
            change = false;
            TreeSet<Object> simpleSources = new TreeSet<Object>(pag.simpleSources());
            if (pag.getOpts().verbose()) {
                G.v().out.println("Iteration " + (iteration++));
            }
            for (Object object : simpleSources) {
                change = handleSimples((VarNode) object) | change;
            }
            if (ofcg != null) {
                QueueReader<Node> addedEdges = pag.edgeReader();
                for (VarNode src : pag.getVarNodeNumberer()) {
                    ofcg.updatedNode(src);
                }
                ofcg.build();

                while (addedEdges.hasNext()) {
                    Node addedSrc = addedEdges.next();
                    Node addedTgt = addedEdges.next();
                    change = true;
                    if (addedSrc instanceof VarNode) {
                        PointsToSetInternal p2set = addedSrc.getP2Set();
                        if (p2set != null) p2set.unFlushNew();
                    } else if (addedSrc instanceof AllocNode) {
                        addedTgt.makeP2Set().add(addedSrc);
                    }
                }
                if (change) {
                    new TopoSorter(pag, false).sort();
                }
            }
            for (Object object : pag.loadSources()) {
                change = handleLoads((FieldRefNode) object) | change;
            }
            for (Object object : pag.storeSources()) {
                change = handleStores((VarNode) object) | change;
            }
        } while (change);
    }

    /**
     * Propagates new points-to information of node src to all its
     * successors.
     */
    protected final boolean handleAllocNode(AllocNode src) {
        boolean ret = false;
        Node[] targets = pag.allocLookup(src);
        for (Node element : targets) {
            ret = element.makeP2Set().add(src) | ret;
        }
        return ret;
    }

    protected final boolean handleSimples(VarNode src) {
        boolean ret = false;
        PointsToSetInternal srcSet = src.getP2Set();
        if (srcSet.isEmpty()) return false;
        Node[] simpleTargets = pag.simpleLookup(src);
        for (Node element : simpleTargets) {
            ret = element.makeP2Set().addAll(srcSet, null) | ret;
        }
        return ret;
    }

    protected final boolean handleStores(VarNode src) {
        boolean ret = false;
        final PointsToSetInternal srcSet = src.getP2Set();
        if (srcSet.isEmpty()) return false;
        Node[] storeTargets = pag.storeLookup(src);
        for (Node element : storeTargets) {
            final FieldRefNode fr = (FieldRefNode) element;
            final SparkField f = fr.getField();
            ret = fr.getBase().getP2Set().forall(new P2SetVisitor() {
                public final void visit(Node n) {
                    AllocDotField nDotF = pag.makeAllocDotField(
                            (AllocNode) n, f);
                    if (nDotF.makeP2Set().addAll(srcSet, null)) {
                        returnValue = true;
                    }
                }
            }) | ret;
        }
        return ret;
    }

    protected final boolean handleLoads(FieldRefNode src) {
        boolean ret = false;
        final Node[] loadTargets = pag.loadLookup(src);
        final SparkField f = src.getField();
        ret = src.getBase().getP2Set().forall(new P2SetVisitor() {
            public final void visit(Node n) {
                AllocDotField nDotF = ((AllocNode) n).dot(f);
                if (nDotF == null) return;
                PointsToSetInternal set = nDotF.getP2Set();
                if (set.isEmpty()) return;
                for (Node element : loadTargets) {
                    VarNode target = (VarNode) element;
                    if (target.makeP2Set().addAll(set, null)) {
                        returnValue = true;
                    }
                }
            }
        }) | ret;
        return ret;
    }
}



