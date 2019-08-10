package hu.procyon.sudokuvalidator;

/**
 * Class to represent an error while parsing a {@code SudokuState}.
 */
public class SudokuParseException extends Exception {
    private static final long serialVersionUID = 5371475755903521566L;

    private final int line;
    private final int position;

    public SudokuParseException(String message, Throwable e, int line, int position) {
        super(message, e);
        this.line = line;
        this.position = position;
    }

    public SudokuParseException(String message, int line, int position) {
        super(message);
        this.line = line;
        this.position = position;
    }

    public SudokuParseException(String message, Throwable e) {
        this(message, e, -1, -1);
    }

    public int getLine() {
        return line;
    }

    public int getPosition() {
        return position;
    }

}