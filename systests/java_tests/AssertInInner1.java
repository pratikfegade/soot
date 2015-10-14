public class AssertInInner1 {

    public static void main(String[] args) {

    }

    static class InnerStaticInit {
        static {
            int x = 0;
            assert x > 1 : "STATIC throwing assert during class init";
        }
    }

    class InnerNonStaticInit {
        void run() {
            int x = 0;
            assert x > 1 : "NONSTATIC throwing assert during class init";
        }

        class InnerDeepNonStaticInit {
            void run() {
                int x = 0;
                assert x > 1 : "DEEP throwing assert";
            }
        }
    }

}
