package soot.asm.backend.targets;

public class InnerClass {

    public int getA() {
        return Inner.a;
    }

    public void doInner() {
        new Measurable() {
        };

    }

    private class Inner {
        static final int a = 3;
    }

}
