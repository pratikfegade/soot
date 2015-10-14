public class ThisTest {

    final int i = 1;
    int j = m(this);

    public static void main(String[] args) {
        System.out.print(new ThisTest().j);
    }

    int m(ThisTest t) {
        return t.i;
    }

}
