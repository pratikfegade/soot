/* Soot - a J*va Optimization Framework
 * Copyright (C) 2002 Sable Research Group
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


package soot.util.dot;

/**
 * Defines several constants used to generate a Dot graph.
 *
 * @author Feng Qian
 */

public interface DotGraphConstants {
    String NODE_SHAPE_BOX = "box";
    String NODE_SHAPE_ELLIPSE = "ellipse";
    String NODE_SHAPE_CIRCLE = "circle";
    String NODE_SHAPE_DIAMOND = "diamond";
    String NODE_SHAPE_PLAINTEXT = "plaintext";

    String NODE_STYLE_SOLID = "solid";
    String NODE_STYLE_DASHED = "dashed";
    String NODE_STYLE_DOTTED = "dotted";
    String NODE_STYLE_BOLD = "bold";
    String NODE_STYLE_INVISIBLE = "invis";
    String NODE_STYLE_FILLED = "filled";
    String NODE_STYLE_DIAGONALS = "diagonals";
    String NODE_STYLE_ROUNDED = "rounded";

    String EDGE_STYLE_DOTTED = "dotted";
    String EDGE_STYLE_SOLID = "solid";

    String GRAPH_ORIENT_PORTRAIT = "portrait";
    String GRAPH_ORIENT_LANDSCAPE = "landscape";

}
