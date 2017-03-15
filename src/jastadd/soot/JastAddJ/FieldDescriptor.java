
package jastadd.soot.JastAddJ;

public class FieldDescriptor extends java.lang.Object {
    // Declared in BytecodeDescriptor.jrag at line 13

    private BytecodeParser p;

    // Declared in BytecodeDescriptor.jrag at line 14

    String typeDescriptor;

    // Declared in BytecodeDescriptor.jrag at line 16


    public FieldDescriptor(BytecodeParser parser, String name) {
      p = parser;
      int descriptor_index = p.u2();
      typeDescriptor = ((CONSTANT_Utf8_Info) p.constantPool[descriptor_index]).string();
      if(BytecodeParser.VERBOSE)
        p.println("  Field: " + name + ", " + typeDescriptor);
    }

    // Declared in BytecodeDescriptor.jrag at line 24


    public Access type() {
      return new TypeDescriptor(p, typeDescriptor).type();
    }

    // Declared in BytecodeDescriptor.jrag at line 28


    public boolean isBoolean() {
      return new TypeDescriptor(p, typeDescriptor).isBoolean();
    }


}
