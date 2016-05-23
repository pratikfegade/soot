/* Soot - a J*va Optimization Framework
 * Copyright (C) 1999 Patrick Lam
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

package soot;

import soot.util.Chain;

import java.util.*;

/**
 * Utility methods for dealing with traps.
 */
public class TrapManager {
    /**
     * If exception e is caught at unit u in body b, return true;
     * otherwise, return false.
     */
    public static boolean isExceptionCaughtAt(SootClass e, Unit u, Body b) {
        /* Look through the traps t of b, checking to see if:
         *  - caught exception is e;
         *  - and, unit lies between t.beginUnit and t.endUnit */

        Hierarchy h = Scene.v().getActiveHierarchy();
        Chain<Unit> units = b.getUnits();

        for (Trap t : b.getTraps()) {
            /* Ah ha, we might win. */
            if (h.isClassSubclassOfIncluding(e, t.getException())) {
                Iterator<Unit> it = units.iterator(t.getBeginUnit(),
                        units.getPredOf(t.getEndUnit()));
                while (it.hasNext())
                    if (u.equals(it.next()))
                        return true;
            }
        }

        return false;
    }

    /**
     * Returns a set of units which lie inside the range of any trap.
     */
    public static Set<Unit> getTrappedUnitsOf(Body b) {
        Set<Unit> trapsSet = new HashSet<>();
        Chain<Unit> units = b.getUnits();

        for (Trap t : b.getTraps()) {
            Iterator<Unit> it = units.iterator(t.getBeginUnit(),
                    units.getPredOf(t.getEndUnit()));
            while (it.hasNext())
                trapsSet.add(it.next());
        }
        return trapsSet;
    }

    /**
     * Given a body and a unit handling an exception,
     * returns the list of exception types possibly caught
     * by the handler.
     */
    public static List<RefType> getExceptionTypesOf(Unit u, Body body) {
        List<RefType> possibleTypes = new ArrayList<>();

        for (Trap trap : body.getTraps()) {
            if (trap.getHandlerUnit() == u) {
                possibleTypes.add(RefType.v(trap.getException().getName()));
            }
        }

        return possibleTypes;
    }
}
