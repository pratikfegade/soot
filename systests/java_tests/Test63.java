public class Test63 {

    public Test63() {
        this(5);
    }

    private Test63(int x) {
        System.out.println("private constructor invoke with: " + x);
    }

    public static void main(String[] args) {
        Test63 t63 = new Test63();
        t63.run();
    }

    public void run() {
        Inner in = new Inner();
        in.run();
    }

    public class Inner {
        public void run() {
            Test63 innerT63 = new Test63(4);
        }
    }
}
