
package jastadd.soot.JastAddJ;

public class CONSTANT_Info extends java.lang.Object {
    // Declared in BytecodeCONSTANT.jrag at line 109

    protected BytecodeParser p;

    // Declared in BytecodeCONSTANT.jrag at line 110

    public CONSTANT_Info(BytecodeParser parser) {
      p = parser;

    }

    // Declared in BytecodeCONSTANT.jrag at line 114

    public Expr expr() {
      throw new Error("CONSTANT_info.expr() should not be computed for " + getClass().getName());
    }

    // Declared in BytecodeCONSTANT.jrag at line 117

    public Expr exprAsBoolean() {
      return expr();
    }


}
