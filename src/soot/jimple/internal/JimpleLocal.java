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

package soot.jimple.internal;

import soot.*;
import soot.jimple.JimpleValueSwitch;
import soot.util.Switch;

import java.util.Collections;
import java.util.List;

public class JimpleLocal implements Local {
    private String name;
    private Type type;

    public void setScopeStart(int scopeStart) {
        this.scopeStart = scopeStart;
    }

    public void setScopeEnd(int scopeEnd) {
        this.scopeEnd = scopeEnd;
    }

    private int scopeStart;
    private int scopeEnd;

    /** Constructs a JimpleLocal of the given name and type. */
    public JimpleLocal(String name, Type type, int scopeStart, int scopeEnd) {
        this.type = type;
        this.scopeStart = scopeStart;
        this.scopeEnd = scopeEnd;

//        if (scopeStart > -1 && scopeEnd > -1) {
//            // FIX: Bytecode does not assign labels to line numbers monotonically so we may need to swap the lines
//            if (scopeStart > scopeEnd) {
//                int temp = scopeEnd;
//                scopeEnd = scopeStart;
//                scopeStart = temp;
//            }
//            this.name = name + "[" + --scopeStart + ", " + --scopeEnd + "]";
//        }
//        else if (scopeStart > -1)
//            this.name = name + "[" + --scopeStart + "]";
//        else
        this.name = name;
        Scene.getInstance().getLocalNumberer().add(this);
    }

    /** Returns true if the given object is structurally equal to this one. */
    public boolean equivTo(Object o) {
        return this.equals(o);
    }

    /**
     * Returns a hash code for this object, consistent with structural equality.
     */
    public int equivHashCode() {
        return this.hashCode();
    }

    /** Returns a clone of the current JimpleLocal. */
    public Object clone() {
        // do not intern the name again
        JimpleLocal local = new JimpleLocal(null, type, scopeStart, scopeEnd);
        local.setName(name);
        return local;
    }

    /** Returns the name of this object. */
    public String getName() {
        return name;
    }

    /** Sets the name of this object as given. */
    public void setName(String name) {
        this.name = (name == null) ? null : name.intern();
    }

    /** Returns the type of this local. */
    public Type getType() {
        return type;
    }

    /** Sets the type of this local. */
    public void setType(Type t) {
        if (this.type instanceof UnknownType || !(this.type instanceof RefType))
            this.type = t;
//  TODO: Avoid this hack
//	    else {
//	        System.out.println("Keeping existing type " + this.type + "  provided by debugging information instead of inferred: " + t);
//        }
    }

    public String toString() {
        return getName();
    }

    public void toString(UnitPrinter up) {
        up.local(this);
    }

    @Override
    public final List<ValueBox> getUseBoxes() {
        return Collections.emptyList();
    }

    public void apply(Switch sw) {
        ((JimpleValueSwitch) sw).caseLocal(this);
    }

    public final int getNumber() {
        return number;
    }

    public final void setNumber(int number) {
        this.number = number;
    }

    private int number = 0;
}
