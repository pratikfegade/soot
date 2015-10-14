/* Soot - a J*va Optimization Framework
 * Copyright (C) 2004 Jennifer Lhotak
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

package soot.toolkits.graph.interaction;

public interface IInteractionConstants {

    int NEW_ANALYSIS = 0;
    int WANT_ANALYSIS = 1;
    int NEW_CFG = 2;
    int CONTINUE = 3;
    int NEW_BEFORE_ANALYSIS_INFO = 4;
    int NEW_AFTER_ANALYSIS_INFO = 5;
    int DONE = 6;
    int FORWARDS = 7;
    int BACKWARDS = 8;
    int CLEARTO = 9;
    int REPLACE = 10;
    int NEW_BEFORE_ANALYSIS_INFO_AUTO = 11;
    int NEW_AFTER_ANALYSIS_INFO_AUTO = 12;
    int STOP_AT_NODE = 13;

    int CALL_GRAPH_START = 50;
    int CALL_GRAPH_NEXT_METHOD = 51;
    int CALL_GRAPH_PART = 52;
    int CALL_GRAPH_DONE = 53;
}
