/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 John Jorgensen
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package soot.toolkits.exceptions;

import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import heros.solver.IDESolver;
import soot.*;
import soot.baf.*;
import soot.grimp.GrimpValueSwitch;
import soot.grimp.NewInvokeExpr;
import soot.jimple.*;
import soot.shimple.PhiExpr;
import soot.shimple.ShimpleValueSwitch;
import soot.singletons.Singletons;
import soot.toolkits.exceptions.ThrowableSet.Pair;

import java.util.*;

/**
 * A {@link ThrowAnalysis} which returns the set of runtime exceptions
 * and errors that might be thrown by the bytecode instructions
 * represented by a unit, as indicated by the Java Virtual Machine
 * specification.  I.e. this analysis is based entirely on the
 * &ldquo;opcode&rdquo; of the unit, the types of its arguments, and
 * the values of constant arguments.
 *
 * <p>The <code>mightThrow</code> methods could be declared static.
 * They are left virtual to facilitate testing. For example,
 * to verify that the expressions in a method call are actually being
 * examined, a test case can override the mightThrow(SootMethod)
 * with an implementation which returns the empty set instead of
 * all possible exceptions.
 */
public class UnitThrowAnalysis extends AbstractThrowAnalysis {
	
	protected final ThrowableSet.Manager mgr = ThrowableSet.Manager.v();

    // Cache the response to mightThrowImplicitly():
    private final ThrowableSet implicitThrowExceptions
	= ThrowableSet.Manager.v().VM_ERRORS
	.add(ThrowableSet.Manager.v().NULL_POINTER_EXCEPTION)
	.add(ThrowableSet.Manager.v().ILLEGAL_MONITOR_STATE_EXCEPTION);

    /**
     * Constructs a <code>UnitThrowAnalysis</code> for inclusion in
     * Soot's global variable manager, {@link G}.
     *
     * @param g guarantees that the constructor may only be called
     * from {@link Singletons}.
     */
    public UnitThrowAnalysis(Singletons.Global g) {
    	this(false);
    }
    
    /**
     * A protected constructor for use by unit tests.
     */
    protected UnitThrowAnalysis() {
    	this(false);
    }

    /**
     * Returns the single instance of <code>UnitThrowAnalysis</code>.
     *
     * @return Soot's <code>UnitThrowAnalysis</code>.
     */
    public static UnitThrowAnalysis v() { return G.v().soot_toolkits_exceptions_UnitThrowAnalysis(); }
    
    protected final boolean isInterproc;
    
    protected UnitThrowAnalysis(boolean isInterproc) {
    	this.isInterproc = isInterproc;
    }
    
    public static UnitThrowAnalysis interproceduralAnalysis = null;
    
    public static UnitThrowAnalysis interproc() {
    	if (interproceduralAnalysis == null) {
    		interproceduralAnalysis = new UnitThrowAnalysis(true);
    	}
    	return interproceduralAnalysis;
    }

	protected ThrowableSet defaultResult() {
		return mgr.VM_ERRORS;
	}

	protected UnitSwitch unitSwitch() {
		return new UnitSwitch();
	}

	protected ValueSwitch valueSwitch() {
		return new ValueSwitch();
	}

	public ThrowableSet mightThrow(Unit u) {
	UnitSwitch sw = unitSwitch();
	u.apply(sw);
	return sw.getResult();
    }

    public ThrowableSet mightThrowImplicitly(ThrowInst t) {
	return implicitThrowExceptions;
    }


    public ThrowableSet mightThrowImplicitly(ThrowStmt t) {
	return implicitThrowExceptions;
    }


    protected ThrowableSet mightThrow(Value v) {
	ValueSwitch sw = valueSwitch();
	v.apply(sw);
	return sw.getResult();
    }

    protected ThrowableSet mightThrow(SootMethodRef m) {
    	// The throw analysis is used in the front-ends. Conseqeuently, some
    	// methods might not yet be loaded. If this is the case, we make
    	// conservative assumptions.
    	SootMethod sm = m.tryResolve();
    	if (sm != null)
    		return mightThrow(sm);
    	else
    		return mgr.ALL_THROWABLES;
    }
    
    /**
     * Returns the set of types that might be thrown as a result of
     * calling the specified method.
     *
     * @param sm method whose exceptions are to be returned.
     *
     * @return a representation of the set of {@link
     * java.lang.Throwable Throwable} types that <code>m</code> might
     * throw.
     */
    protected ThrowableSet mightThrow(SootMethod sm) {
    	if (!isInterproc)
    		return ThrowableSet.Manager.v().ALL_THROWABLES;
    	return methodToThrowSet.getUnchecked(sm);
    }
    
	protected final LoadingCache<SootMethod,ThrowableSet> methodToThrowSet =
			IDESolver.DEFAULT_CACHE_BUILDER.build( new CacheLoader<SootMethod,ThrowableSet>() {
				@Override
				public ThrowableSet load(SootMethod sm) throws Exception {
					return mightThrow(sm, new HashSet<SootMethod>());
				}
			});

    /**
     * Returns the set of types that might be thrown as a result of
     * calling the specified method.
     *
     * @param sm method whose exceptions are to be returned.
     * @param doneSet The set of methods that were already processed
     *
     * @return a representation of the set of {@link
     * java.lang.Throwable Throwable} types that <code>m</code> might
     * throw.
     */
    private ThrowableSet mightThrow(SootMethod sm, Set<SootMethod> doneSet) {
    	// Do not run in loops
    	if (!doneSet.add(sm))
    		return ThrowableSet.Manager.v().EMPTY;
    	
    	// If we don't have body, we silently ignore the method. This is
    	// unsound, but would otherwise always bloat our result set.
    	if (!sm.hasActiveBody())
    		return ThrowableSet.Manager.v().EMPTY;
    	
    	// We need a mapping between unit and exception
    	final PatchingChain<Unit> units = sm.getActiveBody().getUnits();
    	Map<Unit, Collection<Trap>> unitToTraps = sm.getActiveBody().getTraps().isEmpty()
    			? null : new HashMap<>();
    	for (Trap t : sm.getActiveBody().getTraps()) {
			for (Iterator<Unit> unitIt = units.iterator(t.getBeginUnit(),
					units.getPredOf(t.getEndUnit())); unitIt.hasNext();) {
				Unit unit = unitIt.next();
				
	    		Collection<Trap> unitsForTrap = unitToTraps.get(unit);
	    		if (unitsForTrap == null) {
	    			unitsForTrap = new ArrayList<Trap>();
	    			unitToTraps.put(unit, unitsForTrap);
	    		}
	    		unitsForTrap.add(t);
			}
    	}
    	
    	ThrowableSet methodSet = ThrowableSet.Manager.v().EMPTY;
		if(sm.hasActiveBody()){
			Body methodBody = sm.getActiveBody();
			
			for(Unit u: methodBody.getUnits()) {
				if (u instanceof Stmt) {
			    	Stmt stmt = (Stmt) u;
			    	ThrowableSet curStmtSet;
					if (stmt.containsInvokeExpr()) {
						InvokeExpr inv = stmt.getInvokeExpr();
						curStmtSet = mightThrow(inv.getMethod(), doneSet);
					}
					else
						curStmtSet = mightThrow(u);
					
					// The exception might be caught along the way
					if (unitToTraps != null) {
						Collection<Trap> trapsForUnit = unitToTraps.get(stmt);
						if (trapsForUnit != null)
							for (Trap t : trapsForUnit) {
								Pair p = curStmtSet.whichCatchableAs(t.getException().getType());
								curStmtSet = curStmtSet.remove(p.getCaught());
							}
					}
					
					methodSet = methodSet.add(curStmtSet);
				}
			}
		}
		return methodSet;
    }

    private static final IntConstant INT_CONSTANT_ZERO = IntConstant.v(0);
    private static final LongConstant LONG_CONSTANT_ZERO = LongConstant.v(0);


    protected class UnitSwitch implements InstSwitch, StmtSwitch {

	// Asynchronous errors are always possible:
	protected ThrowableSet result = defaultResult();

	ThrowableSet getResult() {
	    return result;
	}

	@Override
	public void caseReturnVoidInst(ReturnVoidInst i) {
	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
	}

	@Override
	public void caseReturnInst(ReturnInst i) {
	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
	}

	@Override
	public void caseNopInst(NopInst i) {
	}

	@Override
	public void caseGotoInst(GotoInst i) {
	}

	@Override
	public void caseJSRInst(JSRInst i) {
	}

	@Override
	public void casePushInst(PushInst i) {
	}

	@Override
	public void casePopInst(PopInst i) {
	}

	@Override
	public void caseIdentityInst(IdentityInst i) {
	}

	@Override
	public void caseStoreInst(StoreInst i) {
	}

	@Override
	public void caseLoadInst(LoadInst i) {
	}

	@Override
	public void caseArrayWriteInst(ArrayWriteInst i) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mgr.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
	    if (i.getOpType() instanceof RefType) {
		result = result.add(mgr.ARRAY_STORE_EXCEPTION);
	    }
	}

	@Override
	public void caseArrayReadInst(ArrayReadInst i) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mgr.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
	}

	@Override
	public void caseIfNullInst(IfNullInst i) {
	}

	@Override
	public void caseIfNonNullInst(IfNonNullInst i) {
	}

	@Override
	public void caseIfEqInst(IfEqInst i) {
	}

	@Override
	public void caseIfNeInst(IfNeInst i) {
	}

	@Override
	public void caseIfGtInst(IfGtInst i) {
	}

	@Override
	public void caseIfGeInst(IfGeInst i) {
	}

	@Override
	public void caseIfLtInst(IfLtInst i) {
	}

	@Override
	public void caseIfLeInst(IfLeInst i) {
	}

	@Override
	public void caseIfCmpEqInst(IfCmpEqInst i) {
	}

	@Override
	public void caseIfCmpNeInst(IfCmpNeInst i) {
	}

	@Override
	public void caseIfCmpGtInst(IfCmpGtInst i) {
	}

	@Override
	public void caseIfCmpGeInst(IfCmpGeInst i) {
	}

	@Override
	public void caseIfCmpLtInst(IfCmpLtInst i) {
	}

	@Override
	public void caseIfCmpLeInst(IfCmpLeInst i) {
	}

	@Override
	public void caseStaticGetInst(StaticGetInst i) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	}

	@Override
	public void caseStaticPutInst(StaticPutInst i) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	}

	@Override
	public void caseFieldGetInst(FieldGetInst i) {
	    result = result.add(mgr.RESOLVE_FIELD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	}

	@Override
	public void caseFieldPutInst(FieldPutInst i) {
	    result = result.add(mgr.RESOLVE_FIELD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	}

	@Override
	public void caseInstanceCastInst(InstanceCastInst i) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    result = result.add(mgr.CLASS_CAST_EXCEPTION);
	}

	@Override
	public void caseInstanceOfInst(InstanceOfInst i) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	}

	@Override
	public void casePrimitiveCastInst(PrimitiveCastInst i) {
	}

	@Override
	public void caseDynamicInvokeInst(DynamicInvokeInst i) {
		result = result.add(mgr.RESOLVE_METHOD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	    //might throw anything
	    result = result.add(ThrowableSet.Manager.v().ALL_THROWABLES);
	}

	@Override
	public void caseStaticInvokeInst(StaticInvokeInst i) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	    result = result.add(mightThrow(i.getMethodRef()));
	}

	@Override
	public void caseVirtualInvokeInst(VirtualInvokeInst i) {
	    result = result.add(mgr.RESOLVE_METHOD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(i.getMethodRef()));
	}

	@Override
	public void caseInterfaceInvokeInst(InterfaceInvokeInst i) {
	    result = result.add(mgr.RESOLVE_METHOD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(i.getMethodRef()));
	}

	@Override
	public void caseSpecialInvokeInst(SpecialInvokeInst i) {
	    result = result.add(mgr.RESOLVE_METHOD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(i.getMethodRef()));
	}

	@Override
	public void caseThrowInst(ThrowInst i) {
	    result = mightThrowImplicitly(i);
	    result = result.add(mightThrowExplicitly(i));
	}

	@Override
	public void caseAddInst(AddInst i) {
	}

	@Override
	public void caseAndInst(AndInst i) {
	}

	@Override
	public void caseOrInst(OrInst i) {
	}

	@Override
	public void caseXorInst(XorInst i) {
	}

	@Override
	public void caseArrayLengthInst(ArrayLengthInst i) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	}

	@Override
	public void caseCmpInst(CmpInst i) {
	}

	@Override
	public void caseCmpgInst(CmpgInst i) {
	}

	@Override
	public void caseCmplInst(CmplInst i) {
	}

	@Override
	public void caseDivInst(DivInst i) {
	    if (i.getOpType() instanceof IntegerType ||
		i.getOpType() == LongType.v()) {
		result = result.add(mgr.ARITHMETIC_EXCEPTION);
	    }
	}

	@Override
	public void caseIncInst(IncInst i) {
	}

	@Override
	public void caseMulInst(MulInst i) {
	}

	@Override
	public void caseRemInst(RemInst i) {
	    if (i.getOpType() instanceof IntegerType ||
		i.getOpType() == LongType.v()) {
		result = result.add(mgr.ARITHMETIC_EXCEPTION);
	    }
	}

	@Override
	public void caseSubInst(SubInst i) {
	}

	@Override
	public void caseShlInst(ShlInst i) {
	}

	@Override
	public void caseShrInst(ShrInst i) {
	}

	@Override
	public void caseUshrInst(UshrInst i) {
	}

	@Override
	public void caseNewInst(NewInst i) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	}

	@Override
	public void caseNegInst(NegInst i) {
	}

	@Override
	public void caseSwapInst(SwapInst i) {
	}

	@Override
	public void caseDup1Inst(Dup1Inst i) {
	}

	@Override
	public void caseDup2Inst(Dup2Inst i) {
	}

	@Override
	public void caseDup1_x1Inst(Dup1_x1Inst i) {
	}

	@Override
	public void caseDup1_x2Inst(Dup1_x2Inst i) {
	}

	@Override
	public void caseDup2_x1Inst(Dup2_x1Inst i) {
	}

	@Override
	public void caseDup2_x2Inst(Dup2_x2Inst i) {
	}

	@Override
	public void caseNewArrayInst(NewArrayInst i) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS); // Could be omitted for primitive arrays.
	    result = result.add(mgr.NEGATIVE_ARRAY_SIZE_EXCEPTION);
	}

	@Override
	public void caseNewMultiArrayInst(NewMultiArrayInst i) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    result = result.add(mgr.NEGATIVE_ARRAY_SIZE_EXCEPTION);
	}

	@Override
	public void caseLookupSwitchInst(LookupSwitchInst i) {
	}

	@Override
	public void caseTableSwitchInst(TableSwitchInst i) {
	}

	@Override
	public void caseEnterMonitorInst(EnterMonitorInst i) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	}

	@Override
	public void caseExitMonitorInst(ExitMonitorInst i) {
	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	}

	@Override
	public void caseAssignStmt(AssignStmt s) {
	    Value lhs = s.getLeftOp();
	    if (lhs instanceof ArrayRef &&
		(lhs.getType() instanceof UnknownType ||
		 lhs.getType() instanceof RefType)) {
		// This corresponds to an aastore byte code.
		result = result.add(mgr.ARRAY_STORE_EXCEPTION);
	    }
	    result = result.add(mightThrow(s.getLeftOp()));
	    result = result.add(mightThrow(s.getRightOp()));
	}

	@Override
	public void caseBreakpointStmt(BreakpointStmt s) {}

	@Override
	public void caseEnterMonitorStmt(EnterMonitorStmt s) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(s.getOp()));
	}

	@Override
	public void caseExitMonitorStmt(ExitMonitorStmt s) {
	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(s.getOp()));
	}

	@Override
	public void caseGotoStmt(GotoStmt s) {
	}

	@Override
	public void caseIdentityStmt(IdentityStmt s) {}
	// Perhaps IdentityStmt shouldn't even return VM_ERRORS,
	// since it corresponds to no bytecode instructions whatsoever.

	@Override
	public void caseIfStmt(IfStmt s) {
	    result = result.add(mightThrow(s.getCondition()));
	}

	@Override
	public void caseInvokeStmt(InvokeStmt s) {
	    result = result.add(mightThrow(s.getInvokeExpr()));
	}

	@Override
	public void caseLookupSwitchStmt(LookupSwitchStmt s) {
	    result = result.add(mightThrow(s.getKey()));
	}

	@Override
	public void caseNopStmt(NopStmt s) {
	}

	@Override
	public void caseRetStmt(RetStmt s) {
	    // Soot should never produce any RetStmt, since
	    // it implements jsr with gotos.
	}

	@Override
	public void caseReturnStmt(ReturnStmt s) {
//	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
//	    result = result.add(mightThrow(s.getOp()));
	}

	@Override
	public void caseReturnVoidStmt(ReturnVoidStmt s) {
//	    result = result.add(mgr.ILLEGAL_MONITOR_STATE_EXCEPTION);
	}

	@Override
	public void caseTableSwitchStmt(TableSwitchStmt s) {
	    result = result.add(mightThrow(s.getKey()));
	}

	@Override
	public void caseThrowStmt(ThrowStmt s) {
	    result = mightThrowImplicitly(s);
	    result = result.add(mightThrowExplicitly(s));
	}

	@Override
	public void defaultCase(Object obj) {
	}
    }


    protected class ValueSwitch implements GrimpValueSwitch, ShimpleValueSwitch {

	// Asynchronous errors are always possible:
	private ThrowableSet result = defaultResult();

	ThrowableSet getResult() {
	    return result;
	}


	// Declared by ConstantSwitch interface:

	public void caseDoubleConstant(DoubleConstant c) {
	}

	public void caseFloatConstant(FloatConstant c) {
	}

	public void caseIntConstant(IntConstant c) {
	}

	public void caseLongConstant(LongConstant c) {
	}

	public void caseNullConstant(NullConstant c) {
	}

	public void caseStringConstant(StringConstant c) {
	}

	public void caseClassConstant(ClassConstant c) {
	}
	
	public void caseMethodHandle(MethodHandle handle) {
	}


	// Declared by ExprSwitch interface:

	public void caseAddExpr(AddExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseAndExpr(AndExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseCmpExpr(CmpExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseCmpgExpr(CmpgExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseCmplExpr(CmplExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseDivExpr(DivExpr expr) {
	    caseBinopDivExpr(expr);
	}

	public void caseEqExpr(EqExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseNeExpr(NeExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseGeExpr(GeExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseGtExpr(GtExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseLeExpr(LeExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseLtExpr(LtExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseMulExpr(MulExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseOrExpr(OrExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseRemExpr(RemExpr expr) {
	    caseBinopDivExpr(expr);
	}

	public void caseShlExpr(ShlExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseShrExpr(ShrExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseUshrExpr(UshrExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseSubExpr(SubExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseXorExpr(XorExpr expr) {
	    caseBinopExpr(expr);
	}

	public void caseInterfaceInvokeExpr(InterfaceInvokeExpr expr) {
	    caseInstanceInvokeExpr(expr);
	}

	public void caseSpecialInvokeExpr(SpecialInvokeExpr expr) {
	    caseInstanceInvokeExpr(expr);
	}

	public void caseStaticInvokeExpr(StaticInvokeExpr expr) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	    for (int i = 0; i < expr.getArgCount(); i++) {
		result = result.add(mightThrow(expr.getArg(i)));
	    }
	    result = result.add(mightThrow(expr.getMethodRef()));
	}

	public void caseVirtualInvokeExpr(VirtualInvokeExpr expr) {
	    caseInstanceInvokeExpr(expr);
	}
	//INSERTED for invokedynamic UnitThrowAnalysis.java
	public void caseDynamicInvokeExpr(DynamicInvokeExpr expr) {
		//caseInstanceInvokeExpr(expr);
	}

	public void caseCastExpr(CastExpr expr) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    Type fromType = expr.getOp().getType();
	    Type toType = expr.getCastType();
	    if (toType instanceof RefLikeType) {
		// fromType might still be unknown when we are called,
		// but toType will have a value.
		FastHierarchy h = Scene.v().getOrMakeFastHierarchy();
		if (fromType == null || fromType instanceof UnknownType ||
		    ((! (fromType instanceof NullType)) &&
		     (! h.canStoreType(fromType, toType)))) {
		    result = result.add(mgr.CLASS_CAST_EXCEPTION);
		}
	    }
	    result = result.add(mightThrow(expr.getOp()));
	}

	public void caseInstanceOfExpr(InstanceOfExpr expr) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    result = result.add(mightThrow(expr.getOp()));
	}

	public void caseNewArrayExpr(NewArrayExpr expr) {
	    if (expr.getBaseType() instanceof RefLikeType) {
		result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    }
	    Value count = expr.getSize();
	    if ((! (count instanceof IntConstant)) ||
		(((IntConstant) count).lessThan(INT_CONSTANT_ZERO)
		                      .equals(INT_CONSTANT_ZERO))) {
		result = result.add(mgr.NEGATIVE_ARRAY_SIZE_EXCEPTION);
	    }
	    result = result.add(mightThrow(count));
	}

	public void caseNewMultiArrayExpr(NewMultiArrayExpr expr) {
	    result = result.add(mgr.RESOLVE_CLASS_ERRORS);
	    for (int i = 0; i < expr.getSizeCount(); i++) {
		Value count = expr.getSize(i);
		if ((! (count instanceof IntConstant)) ||
		    (((IntConstant) count).lessThan(INT_CONSTANT_ZERO)
		                          .equals(INT_CONSTANT_ZERO))) {
		    result = result.add(mgr.NEGATIVE_ARRAY_SIZE_EXCEPTION);
		}
		result = result.add(mightThrow(count));
	    }
	}

	@SuppressWarnings("rawtypes")
	public void caseNewExpr(NewExpr expr) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	    for (Iterator i = expr.getUseBoxes().iterator(); i.hasNext(); ) {
		ValueBox box = (ValueBox) i.next();
		result = result.add(mightThrow(box.getValue()));
	    }
	}

	public void caseLengthExpr(LengthExpr expr) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(expr.getOp()));
	}

	public void caseNegExpr(NegExpr expr) {
	    result = result.add(mightThrow(expr.getOp()));
	}


	// Declared by RefSwitch interface:

	public void caseArrayRef(ArrayRef ref) {
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mgr.ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION);
	    result = result.add(mightThrow(ref.getBase()));
	    result = result.add(mightThrow(ref.getIndex()));
	}

	public void caseStaticFieldRef(StaticFieldRef ref) {
	    result = result.add(mgr.INITIALIZATION_ERRORS);
	}

	public void caseInstanceFieldRef(InstanceFieldRef ref) {
	    result = result.add(mgr.RESOLVE_FIELD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    result = result.add(mightThrow(ref.getBase()));
	}

	public void caseParameterRef(ParameterRef v) {
	}

	public void caseCaughtExceptionRef(CaughtExceptionRef v) {
	}

	public void caseThisRef(ThisRef v) {
	}

	public void caseLocal(Local l) {
	}

	public void caseNewInvokeExpr(NewInvokeExpr e) {
	    caseStaticInvokeExpr(e);
	}

	@SuppressWarnings("rawtypes")
	public void casePhiExpr(PhiExpr e) {
	    for (Iterator i = e.getUseBoxes().iterator(); i.hasNext(); ) {
		ValueBox box = (ValueBox) i.next();
		result = result.add(mightThrow(box.getValue()));
	    }
	}

	public void defaultCase(Object obj) {
	}

	// The remaining cases are not declared by GrimpValueSwitch,
	// but are used to factor out code common to several cases.

	private void caseBinopExpr(BinopExpr expr) {
	    result = result.add(mightThrow(expr.getOp1()));
	    result = result.add(mightThrow(expr.getOp2()));
	}

	private void caseBinopDivExpr(BinopExpr expr) {
	    // Factors out code common to caseDivExpr and caseRemExpr.
	    // The checks against constant divisors would perhaps be
	    // better performed in a later pass, post-constant-propagation.
	    Value divisor = expr.getOp2();
	    Type divisorType = divisor.getType();
	    if (divisorType instanceof UnknownType) {
		result = result.add(mgr.ARITHMETIC_EXCEPTION);
	    } else if ((divisorType instanceof IntegerType) &&
		((! (divisor instanceof IntConstant)) ||
		 (divisor.equals(INT_CONSTANT_ZERO)))) {
		result = result.add(mgr.ARITHMETIC_EXCEPTION);
	    } else if ((divisorType == LongType.v()) &&
		       ((! (divisor instanceof LongConstant)) ||
			(divisor.equals(LONG_CONSTANT_ZERO)))) {
		result = result.add(mgr.ARITHMETIC_EXCEPTION);
	    }
	    caseBinopExpr(expr);
	}

	private void caseInstanceInvokeExpr(InstanceInvokeExpr expr) {
	    result = result.add(mgr.RESOLVE_METHOD_ERRORS);
	    result = result.add(mgr.NULL_POINTER_EXCEPTION);
	    for (int i = 0; i < expr.getArgCount(); i++) {
		result = result.add(mightThrow(expr.getArg(i)));
	    }
	    result = result.add(mightThrow(expr.getBase()));
	    result = result.add(mightThrow(expr.getMethodRef()));
	}
    }
}
