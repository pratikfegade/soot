public strictfp class StrictClass {

    public static int j;
    protected int y;
    volatile int k;
    private int x;

    public StrictClass() {
        float f = 0.9F;
    }

    public static void main(String[] args) {
        StrictClass s = new StrictClass();
    }
}
