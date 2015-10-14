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


package soot.jimple;

public interface ExprSwitch extends soot.util.Switch {
    void caseAddExpr(AddExpr v);

    void caseAndExpr(AndExpr v);

    void caseCmpExpr(CmpExpr v);

    void caseCmpgExpr(CmpgExpr v);

    void caseCmplExpr(CmplExpr v);

    void caseDivExpr(DivExpr v);

    void caseEqExpr(EqExpr v);

    void caseNeExpr(NeExpr v);

    void caseGeExpr(GeExpr v);

    void caseGtExpr(GtExpr v);

    void caseLeExpr(LeExpr v);

    void caseLtExpr(LtExpr v);

    void caseMulExpr(MulExpr v);

    void caseOrExpr(OrExpr v);

    void caseRemExpr(RemExpr v);

    void caseShlExpr(ShlExpr v);

    void caseShrExpr(ShrExpr v);

    void caseUshrExpr(UshrExpr v);

    void caseSubExpr(SubExpr v);

    void caseXorExpr(XorExpr v);

    void caseInterfaceInvokeExpr(InterfaceInvokeExpr v);

    void caseSpecialInvokeExpr(SpecialInvokeExpr v);

    void caseStaticInvokeExpr(StaticInvokeExpr v);

    void caseVirtualInvokeExpr(VirtualInvokeExpr v);

    void caseDynamicInvokeExpr(DynamicInvokeExpr v);

    void caseCastExpr(CastExpr v);

    void caseInstanceOfExpr(InstanceOfExpr v);

    void caseNewArrayExpr(NewArrayExpr v);

    void caseNewMultiArrayExpr(NewMultiArrayExpr v);

    void caseNewExpr(NewExpr v);

    void caseLengthExpr(LengthExpr v);

    void caseNegExpr(NegExpr v);

    void defaultCase(Object obj);
}
