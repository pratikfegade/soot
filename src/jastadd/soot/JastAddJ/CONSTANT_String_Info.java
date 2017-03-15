
package jastadd.soot.JastAddJ;

public class CONSTANT_String_Info extends CONSTANT_Info {
    // Declared in BytecodeCONSTANT.jrag at line 205

    public int string_index;

    // Declared in BytecodeCONSTANT.jrag at line 207


    public CONSTANT_String_Info(BytecodeParser parser) {
      super(parser);
      string_index = p.u2();
    }

    // Declared in BytecodeCONSTANT.jrag at line 212


    public Expr expr() {
      CONSTANT_Utf8_Info i = (CONSTANT_Utf8_Info)p.constantPool[string_index];
      return new StringLiteral(i.string);
    }

    // Declared in BytecodeCONSTANT.jrag at line 217


    public String toString() {
      return "StringInfo: " + p.constantPool[string_index];
    }


}
