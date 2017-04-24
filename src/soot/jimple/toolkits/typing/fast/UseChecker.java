/* Soot - a J*va Optimization Framework
 * Copyright (C) 2008 Ben Bellamy
 *
 * All rights reserved.
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
package soot.jimple.toolkits.typing.fast;

import soot.*;
import soot.jimple.*;
import soot.toolkits.scalar.LocalDefs;
import soot.toolkits.scalar.LocalUses;

import java.util.Iterator;

/**
 * This checks all uses against the rules in Jimple, except some uses are not
 * checked where the bytecode verifier guarantees use validity.
 * @author Ben Bellamy
 */
public class UseChecker extends AbstractStmtSwitch
{
	private JimpleBody jb;

	private Typing tg;
	private IUseVisitor uv;
	
	private LocalDefs defs = null;
	private LocalUses uses = null;
	
	public UseChecker(JimpleBody jb)
	{
		this.jb = jb;
	}

	public void check(Typing tg, IUseVisitor uv)
	{
		this.tg = tg;
		this.uv = uv;
		if (this.tg == null)
			throw new RuntimeException("null typing passed to useChecker");

		for ( Iterator<Unit> i = this.jb.getUnits().snapshotIterator();
			i.hasNext(); )
		{
			if ( uv.finish() )
				return;
			i.next().apply(this);
		}
	}

	private void handleInvokeExpr(InvokeExpr ie, Stmt stmt)
	{
		SootMethodRef m = ie.getMethodRef();

		if ( ie instanceof InstanceInvokeExpr )
		{
			InstanceInvokeExpr iie = (InstanceInvokeExpr)ie;
			iie.setBase(this.uv.visit(
				iie.getBase(),m.declaringClass().getType(), stmt));
		}

		for ( int i = 0; i < ie.getArgCount(); i++ )
			ie.setArg(i, this.uv.visit(
				ie.getArg(i), m.parameterType(i), stmt));
	}

	private void handleBinopExpr(BinopExpr be, Stmt stmt, Type tlhs)
	{
		Value opl = be.getOp1(), opr = be.getOp2();
		Type tl = AugEvalFunction.eval_(this.tg, opl, stmt, this.jb),
			tr = AugEvalFunction.eval_(this.tg, opr, stmt, this.jb);

		if ( be instanceof AddExpr
			|| be instanceof SubExpr
			|| be instanceof MulExpr
			|| be instanceof DivExpr
			|| be instanceof RemExpr
			|| be instanceof GeExpr
			|| be instanceof GtExpr
			|| be instanceof LeExpr
			|| be instanceof LtExpr
			|| be instanceof ShlExpr
			|| be instanceof ShrExpr
			|| be instanceof UshrExpr )
		{
			if ( tlhs instanceof IntegerType )
			{
				be.setOp1(this.uv.visit(opl, IntType.getInstance(), stmt));
				be.setOp2(this.uv.visit(opr, IntType.getInstance(), stmt));
			}
		}
		else if ( be instanceof CmpExpr
			|| be instanceof CmpgExpr
			|| be instanceof CmplExpr )
		{
			// No checks in the original assigner
		}
		else if ( be instanceof AndExpr
			|| be instanceof OrExpr
			|| be instanceof XorExpr )
		{
			be.setOp1(this.uv.visit(opl, tlhs, stmt));
			be.setOp2(this.uv.visit(opr, tlhs, stmt));
		}
		else if ( be instanceof EqExpr
			|| be instanceof NeExpr )
		{
			if ( tl instanceof BooleanType && tr instanceof BooleanType )
			{ }
			else if ( tl instanceof Integer1Type || tr instanceof Integer1Type )
			{ }
			else if ( tl instanceof IntegerType )
			{
				be.setOp1(this.uv.visit(opl, IntType.getInstance(), stmt));
				be.setOp2(this.uv.visit(opr, IntType.getInstance(), stmt));
			}
		}
	}

	private void handleArrayRef(ArrayRef ar, Stmt stmt)
	{
		ar.setIndex(this.uv.visit(ar.getIndex(), IntType.getInstance(), stmt));
	}

	private void handleInstanceFieldRef(InstanceFieldRef ifr, Stmt stmt)
	{
		ifr.setBase(this.uv.visit(ifr.getBase(),
			ifr.getFieldRef().declaringClass().getType(), stmt));
	}

	public void caseBreakpointStmt(BreakpointStmt stmt) { }

	public void caseInvokeStmt(InvokeStmt stmt)
	{
		this.handleInvokeExpr(stmt.getInvokeExpr(), stmt);
	}

	public void caseAssignStmt(AssignStmt stmt)
	{
		Value lhs = stmt.getLeftOp();
		Value rhs = stmt.getRightOp();
		Type tlhs = null;

		if ( lhs instanceof Local )
			tlhs = this.tg.get((Local)lhs);
		else if ( lhs instanceof ArrayRef )
		{
			ArrayRef aref = (ArrayRef) lhs;
			Local base = (Local) aref.getBase();
			
			// Try to force Type integrity. The left side must agree on the
			// element type of the right side array reference.
			ArrayType at = null;
			Type tgType = this.tg.get(base);
			if (tgType instanceof ArrayType)
				at = (ArrayType) tgType;
			else {
				// If the right-hand side is a primitive and the left-side type
				// is java.lang.Object
				if (tgType == Scene.getInstance().getObjectType() && rhs instanceof Local) {
					Type rhsType = this.tg.get((Local) rhs);
					if (rhsType instanceof PrimType) {
						if (defs == null) {
					        defs = LocalDefs.Factory.newLocalDefs(jb);
							uses = LocalUses.Factory.newLocalUses(jb, defs);
						}
						
						// Check the original type of the array from the alloc site
						for (Unit defU : defs.getDefsOfAt(base, stmt)) {
							if (defU instanceof AssignStmt) {
								AssignStmt defUas = (AssignStmt) defU;
								if (defUas.getRightOp() instanceof NewArrayExpr) {
									at = (ArrayType) defUas.getRightOp().getType();
									break;
								}
							}
						}
					}
				}
				
				if (at == null)
					at = tgType.makeArrayType();
			}
			tlhs = at.getElementType();

			this.handleArrayRef(aref, stmt);

			aref.setBase((Local) this.uv.visit(aref.getBase(), at, stmt));
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
			stmt.setLeftOp(this.uv.visit(lhs, tlhs, stmt));
		}
		else if ( lhs instanceof FieldRef )
		{
			tlhs = ((FieldRef)lhs).getFieldRef().type();
			if ( lhs instanceof InstanceFieldRef )
				this.handleInstanceFieldRef((InstanceFieldRef)lhs, stmt);
		}

		// They may have been changed above
		rhs = stmt.getRightOp();

		if ( rhs instanceof Local )
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		else if ( rhs instanceof ArrayRef )
		{
			ArrayRef aref = (ArrayRef) rhs;
			Local base = (Local) aref.getBase();

			//try to force Type integrity
			ArrayType at;
			Type et;
			if (this.tg.get(base) instanceof ArrayType)
				at = (ArrayType)this.tg.get(base);
			else {
				et = this.tg.get(base);
				at = et.makeArrayType();
			}
			Type trhs = at.getElementType();

			this.handleArrayRef(aref, stmt);

			aref.setBase((Local) this.uv.visit(aref.getBase(), at, stmt));
			stmt.setRightOp(this.uv.visit(rhs, trhs, stmt));
		}
		else if ( rhs instanceof InstanceFieldRef )
		{
			this.handleInstanceFieldRef((InstanceFieldRef)rhs, stmt);
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof BinopExpr )
			this.handleBinopExpr((BinopExpr)rhs, stmt, tlhs);
		else if ( rhs instanceof InvokeExpr )
		{
			this.handleInvokeExpr((InvokeExpr)rhs, stmt);
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof CastExpr )
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		else if ( rhs instanceof InstanceOfExpr )
		{
			InstanceOfExpr ioe = (InstanceOfExpr)rhs;
			ioe.setOp(this.uv.visit(
				ioe.getOp(), RefType.getInstance("java.lang.Object"), stmt));
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof NewArrayExpr )
		{
			NewArrayExpr nae = (NewArrayExpr)rhs;
			nae.setSize(this.uv.visit(nae.getSize(), IntType.getInstance(), stmt));
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof NewMultiArrayExpr )
		{
			NewMultiArrayExpr nmae = (NewMultiArrayExpr)rhs;
			for ( int i = 0; i < nmae.getSizeCount(); i++ )
				nmae.setSize(i, this.uv.visit(
					nmae.getSize(i), IntType.getInstance(), stmt));
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof LengthExpr )
		{
			stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
		}
		else if ( rhs instanceof NegExpr )
		{
			((NegExpr)rhs).setOp(this.uv.visit(
				((NegExpr)rhs).getOp(), tlhs, stmt));
		}
		else if ( rhs instanceof Constant )
			if (!(rhs instanceof NullConstant))
				stmt.setRightOp(this.uv.visit(rhs, tlhs, stmt));
	}

	public void caseIdentityStmt(IdentityStmt stmt) { }

	public void caseEnterMonitorStmt(EnterMonitorStmt stmt)
	{
		stmt.setOp(this.uv.visit(
			stmt.getOp(), RefType.getInstance("java.lang.Object"), stmt));
	}

	public void caseExitMonitorStmt(ExitMonitorStmt stmt)
	{
		stmt.setOp(this.uv.visit(
			stmt.getOp(), RefType.getInstance("java.lang.Object"), stmt));
	}

	public void caseGotoStmt(GotoStmt stmt) { }

	public void caseIfStmt(IfStmt stmt)
	{
		this.handleBinopExpr((BinopExpr)stmt.getCondition(), stmt,
			BooleanType.getInstance());
	}

	public void caseLookupSwitchStmt(LookupSwitchStmt stmt)
	{
		stmt.setKey(this.uv.visit(stmt.getKey(), IntType.getInstance(), stmt));
	}

	public void caseNopStmt(NopStmt stmt) { }

	public void caseReturnStmt(ReturnStmt stmt)
	{
		stmt.setOp(this.uv.visit(
			stmt.getOp(), this.jb.getMethod().getReturnType(), stmt));
	}

	public void caseReturnVoidStmt(ReturnVoidStmt stmt) { }

	public void caseTableSwitchStmt(TableSwitchStmt stmt)
	{
		stmt.setKey(this.uv.visit(stmt.getKey(), IntType.getInstance(), stmt));
	}

	public void caseThrowStmt(ThrowStmt stmt)
	{
		stmt.setOp(this.uv.visit(
			stmt.getOp(), RefType.getInstance("java.lang.Throwable"), stmt));
	}

	public void defaultCase(Stmt stmt)
	{
		throw new RuntimeException(
			"Unhandled stgtement type: " + stmt.getClass());
	}
}