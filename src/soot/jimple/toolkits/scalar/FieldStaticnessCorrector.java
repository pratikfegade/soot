package soot.jimple.toolkits.scalar;

import soot.Body;
import soot.BodyTransformer;
import soot.Unit;
import soot.jimple.*;

import java.util.Iterator;

public class FieldStaticnessCorrector extends BodyTransformer {

	@Override
	protected void internalTransform(Body b) {
		// Some apps reference static fields as instance fields. We need to fix
		// this for not breaking the client analysis.
		for (Iterator<Unit> unitIt = b.getUnits().iterator(); unitIt.hasNext(); ) {
			Stmt s = (Stmt) unitIt.next();
			if (s.containsFieldRef()) {
				FieldRef ref = s.getFieldRef();
				if (ref instanceof InstanceFieldRef && ref.getField().isStatic()) {
					if (s instanceof AssignStmt) {
						AssignStmt assignStmt = (AssignStmt) s;
						if (assignStmt.getLeftOp() == ref)
							assignStmt.setLeftOp(Jimple.newStaticFieldRef(ref.getField().makeRef()));
						else if (assignStmt.getRightOp() == ref)
							assignStmt.setRightOp(Jimple.newStaticFieldRef(ref.getField().makeRef()));
					}
				}
			}
		}
	}
}
