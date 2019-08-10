package hu.procyon.sudokuvalidator;

/**
 * Class to represent an error situation while parsing a {@code SudokuState}, in case
 * the state to be parsed is invalid.
 */
public class SudokuStateException extends Exception {
    private static final long serialVersionUID = -8728080594488518624L;

    private final int row;
    private final int column;
    private final int digit;

    public SudokuStateException(String message, int row, int column, int digit) {
        super(message);
        this.row = row;
        this.column = column;
        this.digit = digit;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getDigit() {
        return digit;
    }

}