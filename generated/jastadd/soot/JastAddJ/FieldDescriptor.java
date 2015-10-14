package soot.JastAddJ;

/**
 * @ast class
 */
public class FieldDescriptor extends java.lang.Object {

    String typeDescriptor;
    private BytecodeParser p;


    public FieldDescriptor(BytecodeParser parser, String name) {
        p = parser;
        int descriptor_index = p.u2();
        typeDescriptor = ((CONSTANT_Utf8_Info) p.constantPool[descriptor_index]).string();
        if (BytecodeParser.VERBOSE)
            p.println("  Field: " + name + ", " + typeDescriptor);
    }


    public Access type() {
        return new TypeDescriptor(p, typeDescriptor).type();
    }


    public boolean isBoolean() {
        return new TypeDescriptor(p, typeDescriptor).isBoolean();
    }


}
