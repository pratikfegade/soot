public class EmptyInnerClass {

    public static void main(String[] args) {
        Empty e = new EmptyInnerClass().new Empty();
    }

    public void run() {
        int x = 9;
    }

    class Empty {

    }
}

