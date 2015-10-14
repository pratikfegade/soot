package soot.JastAddJ;

/**
 * @ast class
 */
public class CONSTANT_Fieldref_Info extends CONSTANT_Info {

    public int class_index;


    public int name_and_type_index;


    public CONSTANT_Fieldref_Info(BytecodeParser parser) {
        super(parser);
        class_index = p.u2();
        name_and_type_index = p.u2();
    }


    public String toString() {
        return "FieldRefInfo: " + p.constantPool[class_index] + " "
                + p.constantPool[name_and_type_index];
    }


}
