/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997 Clark Verbrugge
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







package soot.coffi;

import soot.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Util
{
    private static final ArrayList<Type> conversionTypes = new ArrayList<>();

    private static final Map<String, Type[]> cache = new HashMap<>();
    /* Concurrent modification of 'cache' and 'conversionTypes' leads
     * to errors but these are only used by this method, so we make it
     * synchronized. */
    public static synchronized Type[] jimpleTypesOfFieldOrMethodDescriptor(String descriptor)
    {
        Type[] ret = cache.get(descriptor);
        if( ret != null ) return ret;
        char[] d = descriptor.toCharArray();
        int p = 0;
        conversionTypes.clear();

        outer:
        while(p<d.length)
        {
            boolean isArray = false;
            int numDimensions = 0;
            Type baseType = null;

            swtch:
            while(p<d.length) {
                switch( d[p] ) {
                    // Skip parenthesis
                    case '(': case ')':
                        p++;
                        continue outer;

                    case '[':
                        isArray = true;
                        numDimensions++;
                        p++;
                        continue swtch;
                    case 'B':
                        baseType = ByteType.getInstance();
                        p++;
                        break swtch;
                    case 'C':
                        baseType = CharType.getInstance();
                        p++;
                        break swtch;
                    case 'D':
                        baseType = DoubleType.getInstance();
                        p++;
                        break swtch;
                    case 'F':
                        baseType = FloatType.getInstance();
                        p++;
                        break swtch;
                    case 'I':
                        baseType = IntType.getInstance();
                        p++;
                        break swtch;
                    case 'J':
                        baseType = LongType.getInstance();
                        p++;
                        break swtch;
                    case 'L':
                        int index = p+1;
                        while(index < d.length && d[index] != ';') {
                            if(d[index] == '/') d[index] = '.';
                            index++;
                        }
                        if( index >= d.length )
                            throw new RuntimeException("Class reference has no ending ;");
                        String className = new String(d, p+1, index - p - 1);
                        baseType = RefType.newInstance(className);
                        p = index+1;
                        break swtch;
                    case 'S':
                        baseType = ShortType.getInstance();
                        p++;
                        break swtch;
                    case 'Z':
                        baseType = BooleanType.getInstance();
                        p++;
                        break swtch;
                    case 'V':
                        baseType = new VoidType();
                        p++;
                        break swtch;
                    default:
                        throw new RuntimeException("Unknown field type!");
                }
            }
            if( baseType == null ) continue;

            // Determine type
            Type t;
            if(isArray)
                t = ArrayType.getInstance(baseType, numDimensions);
            else
                t = baseType;

            conversionTypes.add(t);
        }

        ret = conversionTypes.toArray(new Type[0]);
        cache.put(descriptor, ret);
        return ret;
    }

    public static Type jimpleTypeOfFieldDescriptor(String descriptor)
    {
        boolean isArray = false;
        int numDimensions = 0;
        Type baseType;

        // Handle array case
        while(descriptor.startsWith("["))
        {
            isArray = true;
            numDimensions++;
            descriptor = descriptor.substring(1);
        }

        // Determine base type
        if(descriptor.equals("B"))
            baseType = ByteType.getInstance();
        else if(descriptor.equals("C"))
            baseType = CharType.getInstance();
        else if(descriptor.equals("D"))
            baseType = DoubleType.getInstance();
        else if(descriptor.equals("F"))
            baseType = FloatType.getInstance();
        else if(descriptor.equals("I"))
            baseType = IntType.getInstance();
        else if(descriptor.equals("J"))
            baseType = LongType.getInstance();
        else if(descriptor.equals("V"))
            baseType = new VoidType();
        else if(descriptor.startsWith("L"))
        {
            if(!descriptor.endsWith(";"))
                throw new RuntimeException("Class reference does not end with ;");

            String className = descriptor.substring(1, descriptor.length() - 1);

            baseType = RefType.newInstance(className.replace('/', '.'));
        }
        else if(descriptor.equals("S"))
            baseType = ShortType.getInstance();
        else if(descriptor.equals("Z"))
            baseType = BooleanType.getInstance();
        else
            throw new RuntimeException("Unknown field type: " + descriptor);

        // Return type
        if(isArray)
            return ArrayType.getInstance(baseType, numDimensions);
        else
            return baseType;
    }


}
