class Aaa {
    Ccc ccc;

    public Aaa() {
        ccc = new Ccc();
    }

    class Ccc {
    }

    class Bbb {
    }

    public class Ddd {
    }
}

public class Test30 {

    public static void main(String[] args) {
        Aaa aaa = new Aaa();
    }

}

