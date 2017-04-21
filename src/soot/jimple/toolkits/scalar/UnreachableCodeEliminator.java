/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Phong Co
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

/*
 * Modified by the Sable Research Group and others 1997-2003.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */



package soot.jimple.toolkits.scalar;

import soot.*;
import soot.options.Options;
import soot.toolkits.exceptions.PedanticThrowAnalysis;
import soot.toolkits.exceptions.ThrowAnalysis;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.util.Chain;

import java.util.*;


public class UnreachableCodeEliminator extends BodyTransformer
{
	protected ThrowAnalysis throwAnalysis = null;
	public UnreachableCodeEliminator( ThrowAnalysis ta ) {
		this.throwAnalysis = ta;
	}

	protected void internalTransform(Body body)
	{		
		if (Options.getInstance().verbose()) {
			System.out.println("[" + body.getMethod().getName() + "] Eliminating unreachable code...");
		}
		
		// Force a conservative ExceptionalUnitGraph() which
		// necessarily includes an edge from every trapped Unit to
		// its handler, so that we retain Traps in the case where
		// trapped units remain, but the default ThrowAnalysis
		// says that none of them can throw the caught exception.
		if (this.throwAnalysis == null)
			this.throwAnalysis = new PedanticThrowAnalysis();
		ExceptionalUnitGraph graph =  new ExceptionalUnitGraph(body, throwAnalysis, false);

		Chain<Unit> units = body.getUnits();
		int numPruned = units.size();
		
		Set<Unit> reachable = units.isEmpty()
			? Collections.emptySet()
			: reachable(units.getFirst(), graph)
			;
		
		// Now eliminate empty traps. (and unreachable handlers)
		//
		// For the most part, this is an atavism, an an artifact of
		// pre-ExceptionalUnitGraph code, when the only way for a trap to 
		// become unreachable was if all its trapped units were removed, and
		// the stmtIt loop did not remove Traps as it removed handler units.
		// We've left this separate test for empty traps here, even though 
		// most such traps would already have been eliminated by the preceding
		// loop, because in arbitrary bytecode you could have
		// handler unit that was still reachable by normal control flow, even
		// though it no longer trapped any units (though such code is unlikely
		// to occur in practice, and certainly no in code generated from Java
		// source.		
		body.getTraps().removeIf(trap -> (trap.getBeginUnit() == trap.getEndUnit()) || !reachable.contains(trap.getHandlerUnit()));
		
		// We must make sure that the end units of all traps which are still
		// alive are kept in the code
		for (Trap t : body.getTraps())
			if (t.getEndUnit() == body.getUnits().getLast())
				reachable.add(t.getEndUnit());

		Set<Unit> notReachable = new HashSet<>();
		if (Options.getInstance().verbose()) {
			for (Unit u : units) {
				if (!reachable.contains(u))
					notReachable.add(u);
			}
		}
			
		units.retainAll(reachable);   
	  	
		numPruned -= units.size();
		
		if (Options.getInstance().verbose()) {
			System.out.println("[" + body.getMethod().getName() + "]	 Removed " + numPruned + " statements: ");
			for (Unit u : notReachable) {
				System.out.println("[" + body.getMethod().getName() + "]	         " + u);
			}

		}
	}
	
	// Used to be: "mark first statement and all its successors, recursively"
	// Bad idea! Some methods are extremely long. It broke because the recursion reached the
	// 3799th level.
	private <T> Set<T> reachable(T first, DirectedGraph<T> g) {
		if ( first == null || g == null ) {
			return Collections.emptySet();
		}
		Set<T> visited = new HashSet<T>(g.size());
		Deque<T> q = new ArrayDeque<T>();
		q.addFirst(first);
		do {
			T t = q.removeFirst();
			if ( visited.add(t) ) {				
				q.addAll(g.getSuccsOf(t));
			}
		}
		while (!q.isEmpty());
		
		return visited;
	}
}
