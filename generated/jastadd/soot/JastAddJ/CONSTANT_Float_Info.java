package soot.JastAddJ;

/**
 * @ast class
 */
public class CONSTANT_Float_Info extends CONSTANT_Info {

    public float value;


    public CONSTANT_Float_Info(BytecodeParser parser) {
        super(parser);
        value = p.readFloat();
    }


    public String toString() {
        return "FloatInfo: " + Float.toString(value);
    }


    public Expr expr() {
        return Literal.buildFloatLiteral(value);
    }


}
