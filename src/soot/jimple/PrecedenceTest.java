package soot.jimple;

import soot.Value;
import soot.ValueBox;

public class PrecedenceTest {
    public PrecedenceTest() {
    }

    public static boolean needsBrackets(ValueBox subExprBox, Value expr) {
        Value sub = subExprBox.getValue();
        if(!(sub instanceof Precedence)) {
            return false;
        } else {
            Precedence subP = (Precedence)sub;
            Precedence exprP = (Precedence)expr;
            return subP.getPrecedence() < exprP.getPrecedence();
        }
    }

    public static boolean needsBracketsRight(ValueBox subExprBox, Value expr) {
        Value sub = subExprBox.getValue();
        if(!(sub instanceof Precedence)) {
            return false;
        } else {
            Precedence subP = (Precedence)sub;
            Precedence exprP = (Precedence)expr;
            return subP.getPrecedence() <= exprP.getPrecedence();
        }
    }
}