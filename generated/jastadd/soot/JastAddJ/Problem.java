package soot.JastAddJ;

/**
 * @ast class
 */
public class Problem extends java.lang.Object implements Comparable {

    protected int line = -1;
    protected int column = -1;
    protected int endLine = -1;
    protected int endColumn = -1;
    protected String fileName;
    protected String message;
    protected Severity severity = Severity.ERROR;
    protected Kind kind = Kind.OTHER;


    public Problem(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }


    public Problem(String fileName, String message, int line) {
        this(fileName, message);
        this.line = line;
    }


    public Problem(String fileName, String message, int line, Severity severity) {
        this(fileName, message);
        this.line = line;
        this.severity = severity;
    }


    public Problem(String fileName, String message, int line, int column, Severity severity) {
        this(fileName, message);
        this.line = line;
        this.column = column;
        this.severity = severity;
    }


    public Problem(String fileName, String message, int line, Severity severity, Kind kind) {
        this(fileName, message);
        this.line = line;
        this.kind = kind;
        this.severity = severity;
    }


    public Problem(String fileName, String message, int line, int column, Severity severity, Kind kind) {
        this(fileName, message);
        this.line = line;
        this.column = column;
        this.kind = kind;
        this.severity = severity;
    }


    public Problem(String fileName, String message, int line, int column, int endLine, int endColumn, Severity severity, Kind kind) {
        this(fileName, message);
        this.line = line;
        this.column = column;
        this.endLine = endLine;
        this.endColumn = endColumn;
        this.kind = kind;
        this.severity = severity;
    }

    public int compareTo(Object o) {
        if (o instanceof Problem) {
            Problem other = (Problem) o;
            if (!fileName.equals(other.fileName))
                return fileName.compareTo(other.fileName);
            if (line != other.line)
                return line - other.line;
            return message.compareTo(other.message);
        }
        return 0;
    }

    public int line() {
        return line;
    }

    public int column() {
        return column;
    }

    public int endLine() {
        return endLine;
    }

    public int endColumn() {
        return endColumn;
    }

    public String fileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String message() {
        return message;
    }

    public Severity severity() {
        return severity;
    }

    public Kind kind() {
        return kind;
    }

    public String toString() {
        String location = "";
        if (line != -1 && column != -1)
            location = line + "," + column + ":";
        else if (line != -1)
            location = line + ":";
        String s = "";
        if (this.kind == Kind.LEXICAL)
            s = "Lexical Error: ";
        else if (this.kind == Kind.SYNTACTIC)
            s = "Syntactic Error: ";
        else if (this.kind == Kind.SEMANTIC)
            s = "Semantic Error: ";
        return fileName + ":" + location + "\n" + "  " + s + message;
    }

    public static class Severity {
        public static final Severity ERROR = new Severity();
        public static final Severity WARNING = new Severity();

        private Severity() {
        }
    }

    public static class Kind {
        public static final Kind OTHER = new Kind();
        public static final Kind LEXICAL = new Kind();
        public static final Kind SYNTACTIC = new Kind();
        public static final Kind SEMANTIC = new Kind();

        private Kind() {
        }
    }


}
