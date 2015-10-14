public class ConsEx {

    public ConsEx() throws MyException {
        throw new MyException();
    }

    public static void main(String[] agrs) {
    }
}

class MyException extends Exception {

}
