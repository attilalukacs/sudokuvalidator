package hu.procyon.sudokuvalidator;

/**
 * Data class to represent a cell's coordinates in a Sudoku table.
 */
public class SudokuCoordinate {

    public final int row;
    public final int column;

    public SudokuCoordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return new StringBuilder("(").append(row).append(",").append(column).append(")").toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SudokuCoordinate) {
            SudokuCoordinate o = (SudokuCoordinate) obj;
            return (o.row == this.row) && (o.column == this.column);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return row * 31 + column;
    }
}