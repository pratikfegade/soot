public class Position {

    int line;
    int col;

    public Position(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public static void main(String[] args) {
        Position p = new Position(3, 4);
    }

    public int line() {
        return line;
    }

    public int col() {
        return col;
    }
}
