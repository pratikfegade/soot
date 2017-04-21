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

@SuppressWarnings("serial")
public abstract class AbstractFloatBinopExpr extends AbstractBinopExpr
{
    public Type getType()
    {
        Value op1 = op1Box.getValue();
        Value op2 = op2Box.getValue();
        Type op1t = op1.getType();
        Type op2t = op2.getType();
        if((op1t.toString().equals("int") ||
                op1t.toString().equals("byte") ||
                op1t.toString().equals("short") ||
                op1t.toString().equals("char") ||
                op1t.toString().equals("boolean")) &&
                (op1t.toString().equals("int") ||
                        op1t.toString().equals("byte") ||
                        op1t.toString().equals("short") ||
                        op1t.toString().equals("char") ||
                        op1t.toString().equals("boolean")))
            return IntType.getInstance();
        else if(op1t.toString().equals("long") || op2t.toString().equals("long"))
            return LongType.getInstance();
        else if(op1t.toString().equals("double") || op2t.toString().equals("double"))
            return DoubleType.getInstance();
        else if(op1t.toString().equals("float") || op2t.toString().equals("float"))
            return FloatType.getInstance();
        else
            return UnknownType.getInstance();
    }
}
