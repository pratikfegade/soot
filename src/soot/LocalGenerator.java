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

package soot;

import soot.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class LocalGenerator{

    private final soot.Body body;
    public LocalGenerator(Body b){
        body = b;
    }

    private transient Set<String> localNames = null;

    private boolean bodyContainsLocal(String name){
        return localNames.contains(name);
    }

    private void initLocalNames() {
        localNames = new HashSet<>();
        for (Local o : body.getLocals()) {
            localNames.add((o).getName());
        }
    }

    /**
     * generates a new soot local given the type
     */
    public soot.Local generateLocal(soot.Type type){

        //store local names for enhanced performance
        initLocalNames();

        String name;
        if (type instanceof soot.IntType) {
            while (true){
                name = nextIntName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.ByteType) {
            while (true){
                name = nextByteName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof ShortType) {
            while (true){
                name = nextShortName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.BooleanType) {
            while (true){
                name = nextBooleanName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof VoidType) {
            while (true){
                name = nextVoidName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof CharType) {
            while (true){
                name = nextCharName();
                if (!bodyContainsLocal(name)) break;
            }
            type = CharType.getInstance();
        }
        else if (type instanceof soot.DoubleType) {
            while (true){
                name = nextDoubleName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.FloatType) {
            while (true){
                name = nextFloatName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.LongType) {
            while (true){
                name = nextLongName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.RefLikeType) {
            while (true){
                name = nextRefLikeTypeName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else if (type instanceof soot.UnknownType) {
            while (true){
                name = nextUnknownTypeName();
                if (!bodyContainsLocal(name)) break;
            }
        }
        else {
            localNames = null;
            throw new RuntimeException("Unhandled Type of Local variable to Generate - Not Implemented");
        }

        localNames = null;
        return createLocal(name, type);
    }

    private int tempInt = -1;
    private int tempVoid = -1;
    private int tempBoolean = -1;
    private int tempLong = -1;
    private int tempDouble = -1;
    private int tempFloat = -1;
    private int tempRefLikeType = -1;
    private int tempByte = -1;
    private int tempShort = -1;
    private int tempChar = -1;
    private int tempUnknownType = -1;

    private String nextIntName(){
        tempInt++;
        return "$i"+tempInt;
    }

    private String nextCharName(){
        tempChar++;
        return "$c"+tempChar;
    }

    private String nextVoidName(){
        tempVoid++;
        return "$getInstance"+tempVoid;
    }

    private String nextByteName(){
        tempByte++;
        return "$b"+tempByte;
    }

    private String nextShortName(){
        tempShort++;
        return "$s"+tempShort;
    }

    private String nextBooleanName(){
        tempBoolean++;
        return "$z"+tempBoolean;
    }

    private String nextDoubleName(){
        tempDouble++;
        return "$d"+tempDouble;
    }

    private String nextFloatName(){
        tempFloat++;
        return "$f"+tempFloat;
    }

    private String nextLongName(){
        tempLong++;
        return "$l"+tempLong;
    }

    private String nextRefLikeTypeName(){
        tempRefLikeType++;
        return "$r"+tempRefLikeType;
    }

    private String nextUnknownTypeName(){
        tempUnknownType++;
        return "$u"+tempUnknownType;
    }

    // this should be used for generated locals only
    private soot.Local createLocal(String name, soot.Type sootType) {
        soot.Local sootLocal = soot.jimple.Jimple.newLocal(name, sootType, -1, -1);
        body.getLocals().add(sootLocal);
        return sootLocal;
    }
}