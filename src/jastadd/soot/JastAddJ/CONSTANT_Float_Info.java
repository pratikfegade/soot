
package jastadd.soot.JastAddJ;

public class CONSTANT_Float_Info extends CONSTANT_Info {
    // Declared in BytecodeCONSTANT.jrag at line 92

    public float value;

    // Declared in BytecodeCONSTANT.jrag at line 94


    public CONSTANT_Float_Info(BytecodeParser parser) {
      super(parser);
      value = p.readFloat();
    }

    // Declared in BytecodeCONSTANT.jrag at line 99


    public String toString() {
      return "FloatInfo: " + Float.toString(value);
    }

    // Declared in BytecodeCONSTANT.jrag at line 103


    public Expr expr() {
      return new FloatingPointLiteral(Float.toString(value));
    }


}
