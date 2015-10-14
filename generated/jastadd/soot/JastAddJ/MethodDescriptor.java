package soot.JastAddJ;

/**
 * @ast class
 */
public class MethodDescriptor extends java.lang.Object {

    private BytecodeParser p;


    private String parameterDescriptors;


    private String typeDescriptor;


    public MethodDescriptor(BytecodeParser parser, String name) {
        p = parser;
        int descriptor_index = p.u2();
        String descriptor = ((CONSTANT_Utf8_Info) p.constantPool[descriptor_index]).string();
        if (BytecodeParser.VERBOSE)
            p.println("  Method: " + name + ", " + descriptor);
        //String[] strings = descriptor.substring(1).split("\\)");
        //parameterDescriptors = strings[0];
        //typeDescriptor = strings[1];
        int pos = descriptor.indexOf(')');
        parameterDescriptors = descriptor.substring(1, pos);
        typeDescriptor = descriptor.substring(pos + 1, descriptor.length());
    }


    public List parameterList() {
        TypeDescriptor d = new TypeDescriptor(p, parameterDescriptors);
        return d.parameterList();
    }


    public List parameterListSkipFirst() {
        TypeDescriptor d = new TypeDescriptor(p, parameterDescriptors);
        return d.parameterListSkipFirst();
    }


    public Access type() {
        TypeDescriptor d = new TypeDescriptor(p, typeDescriptor);
        return d.type();
    }


}
