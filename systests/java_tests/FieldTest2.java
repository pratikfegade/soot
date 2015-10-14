public class FieldTest2 {

    public static int SMILE = 0;
    public String hi = "HI";
    private int x = 9;

    public static void main(String[] args) {

        FieldTest2 ft = new FieldTest2();
        ft.run();
    }

    private void run() {
        System.out.println(hi);
        x = 10;
    }
}
