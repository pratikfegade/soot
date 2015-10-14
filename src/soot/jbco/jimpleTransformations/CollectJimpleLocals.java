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

package soot.jbco.jimpleTransformations;

import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.jbco.IJbcoTransform;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Michael Batchelder
 *         <p/>
 *         Created on 7-Feb-2006
 */
public class CollectJimpleLocals extends BodyTransformer implements
        IJbcoTransform {

    public static String dependancies[] = new String[]{"jtp.jbco_jl"};
    public static String name = "jtp.jbco_jl";

    public void outputSummary() {
    }

    public String[] getDependancies() {
        return dependancies;
    }

    public String getName() {
        return name;
    }

    protected void internalTransform(Body body, String phaseName, Map<String, String> options) {
        soot.jbco.Main.methods2JLocals.put(body.getMethod(), new ArrayList<Local>(body.getLocals()));
    }
}