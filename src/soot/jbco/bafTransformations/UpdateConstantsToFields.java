/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
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

package soot.jbco.bafTransformations;

import soot.*;
import soot.baf.Baf;
import soot.baf.PushInst;
import soot.jbco.IJbcoTransform;
import soot.jbco.jimpleTransformations.CollectConstants;
import soot.jbco.util.BodyBuilder;
import soot.jbco.util.Rand;

import java.util.Iterator;
import java.util.Map;

/**
 * @author Michael Batchelder
 *         <p/>
 *         Created on 31-May-2006
 */
public class UpdateConstantsToFields extends BodyTransformer implements IJbcoTransform {

    public static String dependancies[] = new String[]{"wjtp.jbco_cc", "bb.jbco_ecvf", "bb.jbco_ful", "bb.lp"};
    public static String name = "bb.jbco_ecvf";
    static int updated = 0;

    public String[] getDependancies() {
        return dependancies;
    }

    public String getName() {
        return name;
    }

    public void outputSummary() {
        out.println("Updated constant references: " + updated);
    }

    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
        if (b.getMethod().getName().indexOf("<clinit>") >= 0)
            return;

        int weight = soot.jbco.Main.getWeight(phaseName, b.getMethod().getSignature());
        if (weight == 0) return;

        PatchingChain<Unit> units = b.getUnits();
        Iterator<Unit> iter = units.snapshotIterator();
        while (iter.hasNext()) {
            Unit u = iter.next();
            if (u instanceof PushInst) {
                SootField f = CollectConstants.constantsToFields.get(((PushInst) u).getConstant());
                if (f != null && Rand.getInt(10) <= weight) {
                    Unit get = Baf.v().newStaticGetInst(f.makeRef());
                    units.insertBefore(get, u);
                    BodyBuilder.updateTraps(get, u, b.getTraps());
                    units.remove(u);
                    updated++;
                }
            }
        }
    }
}