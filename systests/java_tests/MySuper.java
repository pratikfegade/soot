public class MySuper {
    public int x = 9;
    protected int y = 8;

    public static void main(String[] args) {
        MySuper m = new MySuper();
        m.go();
        m.going();
    }

    public void go() {
        System.out.println("go from MySuper");
    }

    protected void going() {
        System.out.println("going from MySuper");
    }
}
