public strictfp class Strictfp {

    static float m = 9F;
    float n = 8F;

    public static void main(String[] args) {
        Strictfp s = new Strictfp();
        s.run();
    }

    public void run() {
        float f = 9F;
        float d = f / 2F;
        new Strictfp() {
            public void run() {
                float g = 9F;
            }
        }.run();
    }
}
