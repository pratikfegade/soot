/* Soot - a J*va Optimization Framework
 * Copyright (C) 2007 Eric Bodden
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
package soot.jimple.spark.sets;

import soot.PointsToSet;

/**
 * A points-to set supporting deep equals and hashCode operations.
 *  
 * @author Eric Bodden
 * @see PointsToSetEqualsWrapper
 */
public interface EqualsSupportingPointsToSet extends PointsToSet {

    /**
     * Computes a hash code based on the contents of the points-to set.
     * Note that hashCode() is not overwritten on purpose.
     * This is because Spark relies on comparison by object identity.
     */
    int pointsToSetHashCode();

    /**
     * Returns <code>true</code> if and only if other holds the same alloc nodes as this.
     * Note that equals() is not overwritten on purpose.
     * This is because Spark relies on comparison by object identity.
     */
    boolean pointsToSetEquals(Object other);

}