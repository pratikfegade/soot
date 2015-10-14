import java.lang.reflect.Field;

interface I {
}

public class ReflectProblem {
    public static void main(String[] args) throws Exception {
        Field f = C.class.getField("cf");
        System.out.println(f);
    }
}

class C implements I {
    public int cf;
}
