
package jastadd.soot.JastAddJ;

public class CONSTANT_Methodref_Info extends CONSTANT_Info {
    // Declared in BytecodeCONSTANT.jrag at line 175

    public int class_index;

    // Declared in BytecodeCONSTANT.jrag at line 176

    public int name_and_type_index;

    // Declared in BytecodeCONSTANT.jrag at line 178


    public CONSTANT_Methodref_Info(BytecodeParser parser) {
      super(parser);
      class_index = p.u2();
      name_and_type_index = p.u2();
    }

    // Declared in BytecodeCONSTANT.jrag at line 184


    public String toString() {
      return "MethodRefInfo: " + class_index + " " + name_and_type_index;
    }


}
