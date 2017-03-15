
package jastadd.soot.JastAddJ;

public class CONSTANT_InterfaceMethodref_Info extends CONSTANT_Info {
    // Declared in BytecodeCONSTANT.jrag at line 143

    public int class_index;

    // Declared in BytecodeCONSTANT.jrag at line 144

    public int name_and_type_index;

    // Declared in BytecodeCONSTANT.jrag at line 146


    public CONSTANT_InterfaceMethodref_Info(BytecodeParser parser) {
      super(parser);
      class_index = p.u2();
      name_and_type_index = p.u2();
    }

    // Declared in BytecodeCONSTANT.jrag at line 152


    public String toString() {
      return "InterfaceMethodRefInfo: " + p.constantPool[class_index] + " "
        + p.constantPool[name_and_type_index];
    }


}
