
package jastadd.soot.JastAddJ;

public class MethodDescriptor extends java.lang.Object {
    // Declared in BytecodeDescriptor.jrag at line 97

    private BytecodeParser p;

    // Declared in BytecodeDescriptor.jrag at line 98

    private String parameterDescriptors;

    // Declared in BytecodeDescriptor.jrag at line 99

    private String typeDescriptor;

    // Declared in BytecodeDescriptor.jrag at line 101


    public MethodDescriptor(BytecodeParser parser, String name) {
      p = parser;
      int descriptor_index = p.u2();
      String descriptor = ((CONSTANT_Utf8_Info) p.constantPool[descriptor_index]).string();
      if(BytecodeParser.VERBOSE)
        p.println("  Method: " + name + ", " + descriptor);
      //String[] strings = descriptor.substring(1).split("\\)");
      //parameterDescriptors = strings[0];
      //typeDescriptor = strings[1];
      int pos = descriptor.indexOf(')');
      parameterDescriptors = descriptor.substring(1, pos);
      typeDescriptor = descriptor.substring(pos+1, descriptor.length());
    }

    // Declared in BytecodeDescriptor.jrag at line 115


    public List parameterList() {
      TypeDescriptor d = new TypeDescriptor(p, parameterDescriptors);
      return d.parameterList();
    }

    // Declared in BytecodeDescriptor.jrag at line 119

    public List parameterListSkipFirst() {
      TypeDescriptor d = new TypeDescriptor(p, parameterDescriptors);
      return d.parameterListSkipFirst();
    }

    // Declared in BytecodeDescriptor.jrag at line 124


    public Access type() {
      TypeDescriptor d = new TypeDescriptor(p, typeDescriptor);
      return d.type();
    }


}
