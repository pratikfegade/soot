package soot.JastAddJ;

/**
 * @ast class
 */
public class CONSTANT_Info extends java.lang.Object {

    protected BytecodeParser p;


    public CONSTANT_Info(BytecodeParser parser) {
        p = parser;

    }


    public Expr expr() {
        throw new Error("CONSTANT_info.expr() should not be computed for " + getClass().getName());
    }


    public Expr exprAsBoolean() {
        return expr();
    }


}
