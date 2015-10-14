public class InnerInConstCallWithQualifier {

    public InnerInConstCallWithQualifier() {
        this(new QualifierClass().new QInner() {
            public void run() {
            }
        });
    }

    public InnerInConstCallWithQualifier(Object r) {
    }

    public static void main(String[] args) {
        new InnerInConstCallWithQualifier().go();
    }

    private void go() {
        System.out.println("running go");
    }
}

class QualifierClass {

    public class QInner {
        public void run() {

        }
    }
}
