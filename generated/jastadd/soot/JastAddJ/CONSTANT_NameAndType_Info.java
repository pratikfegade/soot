package soot.JastAddJ;

/**
 * @ast class
 */
public class CONSTANT_NameAndType_Info extends CONSTANT_Info {

    public int name_index;


    public int descriptor_index;


    public CONSTANT_NameAndType_Info(BytecodeParser parser) {
        super(parser);
        name_index = p.u2();
        descriptor_index = p.u2();
    }


    public String toString() {
        return "NameAndTypeInfo: " + name_index + " " + descriptor_index;
    }


}
