package hu.procyon.sudokuvalidator;

/**
 * Data class for storing a possible step in a Sudoku state.
 */
public class SudokuStep {

    public final SudokuCoordinate coordinate;
    public final int digit;
    public final boolean single;

    public SudokuStep(final SudokuCoordinate coordinate, final int digit, final boolean single) {
        this.coordinate = coordinate;
        this.digit = digit;
        this.single = single;
    }

    @Override
    public String toString() {
        return new StringBuilder(coordinate.toString()).append(" ").append(digit).append(single? "!" : "").toString();
    }
}