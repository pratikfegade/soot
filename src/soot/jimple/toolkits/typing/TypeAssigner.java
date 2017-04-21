/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-2000 Etienne Gagnon.
 * Copyright (C) 2008 Ben Bellamy 
 * Copyright (C) 2008 Eric Bodden 
 * 
 * All rights reserved.
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
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot.jimple.toolkits.typing;

import soot.*;
import soot.jimple.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This transformer assigns types to local variables.
 * @author Etienne Gagnon
 * @author Ben Bellamy
 * @author Eric Bodden 
 */
public class TypeAssigner extends BodyTransformer {

	/** Assign types to local variables. * */
	protected void internalTransform(Body b) {
		if (b == null) {
			throw new NullPointerException();
		}

		(new soot.jimple.toolkits.typing.fast.TypeResolver(
				(JimpleBody) b)).inferTypes();

		replaceNullType(b);

		if (typingFailed((JimpleBody) b))
			throw new RuntimeException("type inference failed!");
	}

	/**
	 * Replace statements using locals with null_type type and that would 
	 * throw a NullPointerException at runtime by a set of instructions
	 * throwing a NullPointerException.
	 *
	 * This is done to remove locals with null_type type.
	 *
	 * @param b
	 */
	private void replaceNullType(Body b) {
		List<Local> localsToRemove = new ArrayList<>();
		boolean hasNullType = false;

		// check if any local has null_type
		for (Local l: b.getLocals()) {
			if (l.getType() instanceof NullType) {
				localsToRemove.add(l);
				hasNullType = true;
			}
		}

		// No local with null_type
		if (!hasNullType)
			return;


		List<Unit> unitToReplaceByException = new ArrayList<>();
		for (Unit u: b.getUnits()) {
			for (ValueBox vb : u.getUseBoxes()) {
				if( vb.getValue() instanceof Local && vb.getValue().getType() instanceof NullType) {

					Local l = (Local)vb.getValue();
					Stmt s = (Stmt)u;

					boolean replace = false;
					if (s.containsArrayRef()) {
						ArrayRef r = s.getArrayRef();
						if (r.getBase() == l) { replace = true; }
					} else if (s.containsFieldRef()) {
						FieldRef r = s.getFieldRef();
						if (r instanceof InstanceFieldRef) {
							InstanceFieldRef ir = (InstanceFieldRef)r;
							if (ir.getBase() == l) { replace = true; }
						}
					} else if (s.containsInvokeExpr()) {
						InvokeExpr ie = s.getInvokeExpr();
						if (ie instanceof InstanceInvokeExpr) {
							InstanceInvokeExpr iie = (InstanceInvokeExpr) ie;
							if (iie.getBase() == l) { replace = true; }
						}
					}

					if (replace) {
						unitToReplaceByException.add(u);
					}
				}
			}
		}

		for (Unit u: unitToReplaceByException) {
			soot.dexpler.Util.addExceptionAfterUnit(b, "java.lang.NullPointerException", u, "This statement would have triggered an Exception: "+ u);
			b.getUnits().remove(u);
		}
	}

	private boolean typingFailed(JimpleBody b) {
		// Check to see if any locals are untyped
		{
			Iterator<Local> localIt = b.getLocals().iterator();

			while (localIt.hasNext()) {
				Local l = localIt.next();

				if (l.getType().equals(UnknownType.getInstance())
						|| l.getType().equals(ErroneousType.getInstance())) {
					return true;
				}
			}
		}

		return false;
	}

}
