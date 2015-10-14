package soot.JastAddJ;

/**
 * @ast class
 */
public class CONSTANT_Long_Info extends CONSTANT_Info {

    public long value;


    public CONSTANT_Long_Info(BytecodeParser parser) {
        super(parser);
        value = p.readLong();
    }


    public String toString() {
        return "LongInfo: " + Long.toString(value);
    }


    public Expr expr() {
        return Literal.buildLongLiteral(value);
    }


}
