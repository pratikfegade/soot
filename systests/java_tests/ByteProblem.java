public class ByteProblem {

    public final static byte TheConstant = 3;

    public static void main(String[] args) {
        new ByteProblem().reffoo();
    }

    public void foo(byte x) {
    }

    public void reffoo() {
        foo(TheConstant);
    }
}

