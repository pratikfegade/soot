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

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */

package soot;

/**
 * Provides methods common to Soot objects belonging to classes,
 * namely SootField and SootMethod.
 */
public interface ClassMember {
    /**
     * Returns the SootClass declaring this one.
     */
    SootClass getDeclaringClass();

    /**
     * Returns true when some SootClass object declares this object.
     */
    boolean isDeclared();

    /**
     * Returns true when this object is from a phantom class.
     */
    boolean isPhantom();

    /**
     * Sets the phantom flag
     */
    void setPhantom(boolean value);

    /**
     * Convenience method returning true if this class member is protected.
     */
    boolean isProtected();

    /**
     * Convenience method returning true if this class member is private.
     */
    boolean isPrivate();

    /**
     * Convenience method returning true if this class member is public.
     */
    boolean isPublic();

    /**
     * Convenience method returning true if this class member is static.
     */
    boolean isStatic();

    /**
     * Returns modifiers of this class member.
     */
    int getModifiers();

    /**
     * Sets modifiers of this class member.
     */
    void setModifiers(int modifiers);


}




