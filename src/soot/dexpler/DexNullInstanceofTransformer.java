package soot.dexpler;

import soot.Body;
import soot.BodyTransformer;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.jimple.InstanceOfExpr;
import soot.jimple.IntConstant;
import soot.jimple.NullConstant;

import java.util.Iterator;

/**
 * Transformer that swaps
 * 
 * 		a = 0 instanceof _class_;
 * 
 * with
 * 
 * 		a = false
 * 
 * @author Steven Arzt
 *
 */
public class DexNullInstanceofTransformer extends BodyTransformer {

	public static DexNullInstanceofTransformer v() {
		return new DexNullInstanceofTransformer();
	}
	
	@Override
	protected void internalTransform(Body b) {
		for (Iterator<Unit> unitIt = b.getUnits().snapshotIterator(); unitIt.hasNext(); ) {
			Unit u = unitIt.next();
			if (u instanceof AssignStmt) {
				AssignStmt assignStmt = (AssignStmt) u;
				if (assignStmt.getRightOp() instanceof InstanceOfExpr) {
					InstanceOfExpr iof = (InstanceOfExpr) assignStmt.getRightOp();
					
					// If the operand of the "instanceof" expression is null or
					// the zero constant, we replace the whole operation with
					// its outcome "false"
					if (iof.getOp() == NullConstant.getInstance())
						assignStmt.setRightOp(IntConstant.v(0));
					if (iof.getOp() instanceof IntConstant
							&& ((IntConstant) iof.getOp()).value == 0)
						assignStmt.setRightOp(IntConstant.v(0));
				}
			}
		}
	}

}
