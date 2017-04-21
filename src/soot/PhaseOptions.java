/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Ondrej Lhotak
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

package soot;

import java.util.*;

/** Manages the phase options of the various soot phases. */
public class PhaseOptions {

    /** This method returns true iff key "name" is in options
        and maps to "true". */
    public static boolean getBoolean(Map<String, String> options, String name) {
    	String val = options.get(name);
        return val != null && val.equals("true");
    }

    /**
     * If key "name" is in options, this method returns true iff it maps to
     * "true". If the key "name" is not in options, the given default value
     * is returned.
     */
    public static boolean getBoolean(Map<String, String> options, String name,
    		boolean defaultValue) {
    	String val = options.get(name);
    	if (val == null)
    		return defaultValue;
        return val.equals("true");
    }


    /** This method returns the value of "name" in options 
        or "" if "name" is not found. */
    public static String getString(Map<String, String> options, String name) {
    	String val = options.get(name);
        return val != null ? val : "";
    }



    /** This method returns the float value of "name" in options 
        or 1.0 if "name" is not found. */
    public static float getFloat(Map<String, String> options, String name)
    {
        return options.containsKey(name) ?
            new Float(options.get(name)).floatValue() : 1.0f;
    }



    /** This method returns the integer value of "name" in options 
        or 0 if "name" is not found. */
    public static int getInt(Map<String, String> options, String name)
    {
        return options.containsKey(name) ?
            new Integer(options.get(name)).intValue() : 0;
    }
}
