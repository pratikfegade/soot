/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Navindra Umanee <navindra@cs.mcgill.ca>
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

package soot.shimple;

import soot.shimple.toolkits.graph.GlobalValueNumberer;
import soot.toolkits.graph.*;

/**
 * @author Navindra Umanee
 **/
public interface ShimpleFactory
{

    /**
     * Constructors should memoize their return value.  Call clearCache()
     * to force recomputations if body has changed and setBody()
     * hasn't been called again.
     **/
    void clearCache();

    UnitGraph getUnitGraph();
    BlockGraph getBlockGraph();
    DominatorsFinder<Block> getDominatorsFinder();
    DominatorTree<Block> getDominatorTree();
    DominanceFrontier<Block> getDominanceFrontier();

    GlobalValueNumberer getGlobalValueNumberer();
    ReversibleGraph<Block> getReverseBlockGraph();
    DominatorsFinder<Block> getReverseDominatorsFinder();
    DominatorTree<Block> getReverseDominatorTree();
    DominanceFrontier<Block> getReverseDominanceFrontier();
}
