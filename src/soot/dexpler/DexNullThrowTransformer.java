package soot.dexpler;

import soot.*;
import soot.jimple.*;
import soot.jimple.toolkits.scalar.LocalCreation;

import java.util.Collections;
import java.util.Iterator;

/**
 * Some Android applications throw null references, e.g.,
 * 
 * 		a = null; throw a;
 * 
 * This will make unit graph construction fail as no targets of the throw
 * statement can be found. We therefore replace such statements with direct
 * NullPointerExceptions which would happen at runtime anyway.
 * 
 * @author Steven Arzt
 * 
 */
public class DexNullThrowTransformer extends BodyTransformer {
	
	public static DexNullThrowTransformer v() {
		return new DexNullThrowTransformer();
	}
	
	@Override
	protected void internalTransform(Body b) {
		LocalCreation lc = new LocalCreation(b.getLocals(), "ex");
		
		for (Iterator<Unit> unitIt = b.getUnits().snapshotIterator(); unitIt.hasNext(); ) {
			Unit u = unitIt.next();
			
			// Check for a null exception
			if (u instanceof ThrowStmt) {
				ThrowStmt throwStmt = (ThrowStmt) u;
				if (throwStmt.getOp() == NullConstant.v()
						|| throwStmt.getOp().equals(IntConstant.v(0))
						|| throwStmt.getOp().equals(LongConstant.v(0))) {
					createThrowStmt(b, throwStmt, lc);
				}
			}
		}
	}

	/**
	 * Creates a new statement that throws a NullPointerException
	 * @param body The body in which to create the statement
	 * @param oldStmt The old faulty statement that shall be replaced with the
	 * exception
	 * @param lc The object for creating new locals
	 */
	private void createThrowStmt(Body body, Unit oldStmt, LocalCreation lc) {
		RefType tp = RefType.newInstance("java.lang.NullPointerException");
		Local lcEx = lc.newLocal(tp);
		
		SootMethodRef constructorRef = Scene.v().makeConstructorRef(tp.getSootClass(),
				Collections.singletonList(RefType.newInstance("java.lang.String")));
		
		// Create the exception instance
		Stmt newExStmt = Jimple.newAssignStmt(lcEx, Jimple.newNewExpr(tp));
		body.getUnits().insertBefore(newExStmt, oldStmt);
		Stmt invConsStmt = Jimple.newInvokeStmt(Jimple.newVirtualInvokeExpr(lcEx,
				constructorRef, Collections.singletonList(StringConstant.v(
						"Null throw statement replaced by Soot"))));
		body.getUnits().insertBefore(invConsStmt, oldStmt);
		
		// Throw the exception
		body.getUnits().swapWith(oldStmt, Jimple.newThrowStmt(lcEx));
	}
	
}
