public class EnclosingClass {

    public static void main(String[] args) {
        EnclosingClass e = new EnclosingClass();
        e.run();
    }

    private void happy() {
        System.out.println("smile");
    }

    public void run() {
        PubClass p = new PubClass();
        p.run();
    }

    private class PriClass {
        public void run() {
            System.out.println("go");
        }
    }

    class PubClass {
        public void run() {
            new PriClass().run();
            happy();
        }
    }
}
