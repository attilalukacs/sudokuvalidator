package hu.procyon.sudokuvalidator;

import java.util.Arrays;

/**
 * Represents a state in a 9×9 Sudoku solution, without the distinction between
 * cells originally filled and those filled during solving the puzzle.
 *
 * The class stores which digit is written in which Sudoku cell, which cells are
 * still empty and what digits can still be filled in the empty cells.
 *
 * Only empty {@code SudokuState} can be instantiated. From this empty state,
 * the user of the class can build a puzzle (based on some sort of input).
 * The class makes sure that no invalid state can be reached, that is,
 * same digits in the same row, column or block cannot be used.
 *
 * The rows and columns are identified by 0 to 8
 * from top to bottom and left to right, respectively.
 * Valid digits go from 1 to 9.
 */
public class SudokuState implements Cloneable {
    private static final int N = 3;
    private static final int SIZE = N * N;

    private byte[] table = new byte[SIZE * SIZE];
    private short[] excluded = new short[SIZE * SIZE];
    private int noDigits = 0;

    /**
     * Returns whether a digit is set in the given row and column
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @return true if a digit is set, false otherwise
     */
    public boolean isDigitSet(final int row, final int column) {
        return table[row * SIZE + column] != 0;
    }

    /**
     * Returns the digit set at the given position position or 0 if it's not set.
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @return the digit 1-9 if it's set or 0 if it is not
     */
    public int getDigit(final int row, final int column) {
        return table[row * SIZE + column];
    }

    /**
     * Returns whether a digit is excluded in a given empty cell. A digit is excluded
     * if the same digit is set somewhere in the same row, column or block.
     *
     * The digit exclusion is independent of the digit set in the column. If a digit is
     * set in the position, it still returns false if a digit is not excluded by
     * the affected neighbouring cells.
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @param digit the digit 1-9 to be tested for exclusion
     * @return whether the given digit is excluded by the same row, column or block
     */
    public boolean isDigitExcluded(final int row, final int column, final int digit) {
        short mask = (short)(1 << digit);
        return (excluded[row * SIZE + column] & mask) != 0;
    }

    /**
     * Gives the number of excluded digits.
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @return number of digits excluded
     */
    public int getNumberOfExcludedDigits(final int row, final int column) {
        int noExcluded = 0;
        for (int digit = 1; digit <= SIZE; digit++) {
            if (isDigitExcluded(row, column, digit)) {
                noExcluded++;
            }
        }
        return noExcluded;
    }

    /**
     * Gives the number of digits that can still be set in the given cell,
     * or -1 if the cell is not empty.
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @return number of possible digits or -1 if the cell is not empty.
     */
    public int getNumberOfPossibleDigits(final int row, final int column) {
        return isDigitSet(row, column) ? -1 : SIZE - getNumberOfExcludedDigits(row, column);
    }

    /**
     * Returns whether the given digit can be set in the given cell.
     *
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @param digit the digit 1-9 to be tested
     * @return true if the cell is empty and the digit is not excluded, false otherwise
     */
    public boolean canSetDigit(final int row, final int column, final int digit) {
        boolean cannotSet = isDigitSet(row, column) || isDigitExcluded(row, column, digit);
        return !cannotSet;
    }

    /**
     * Tries to modify the Sudoku state by entering the given digit in the given
     * cell. If the move is valid, that is, the cell is empty and the digit is not excluded
     * by the same row, column or block, then the state will be modified and the method
     * returns true. Otherwise, the state stays intact and false is returned.
     * @param row the number of row (0-8 from top to bottom)
     * @param column the number of column (0-8 from left to right)
     * @param digit the digit 1-9 to be set in the given cell
     * @return true if the move is valid and the state is modified, false otherwise
     */
    public boolean setDigit(final int row, final int column, final int digit) {
        boolean isValidMove = canSetDigit(row, column, digit) && isDigitValid(digit);
        if (isValidMove) {
            table[row * SIZE + column] = (byte) digit;
            excludeColumn(column, digit);
            excludeRow(row, digit);
            excludeBlock(row, column, digit);
            noDigits++;
        }
        return isValidMove;
    }

    /**
     * Gives whether a digit is valid (is between 1 and 9, inclusive.)
     * @param digit the digit to be tested
     * @return true if the digit is valid, false otherwise
     */
    public boolean isDigitValid(final int digit) {
        return 1 <= digit && digit <= SIZE;
    }

    /**
     * Called internally, this method accounts for exclusions by the column.
     * @param column
     * @param digit
     */
    private void excludeColumn(final int column, final int digit) {
        short mask = (short)(1 << digit);
        for (int row = 0; row < SIZE; row++) {
            excluded[row * SIZE + column] |= mask;
        }
    }

    /**
     * Called internally, this method accounts for exclusions by the row.
     * @param row
     * @param digit
     */
    private void excludeRow(final int row, final int digit) {
        short mask = (short)(1 << digit);
        for (int column = 0; column < SIZE; column++) {
            excluded[row * SIZE + column] |= mask;
        }
    }

    /**
     * Called internally, this method accounts for exclusions by the block.
     * @param row
     * @param column
     * @param digit
     */
    private void excludeBlock(final int row, final int column, final int digit) {
        int blockRow = row / N * N;
        int blockColumn = column / N * N;
        short mask = (short)(1 << digit);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                excluded[(blockRow + i) * SIZE + (blockColumn + j)] |= mask;
            }
        }
    }

    /**
     * Gives the size of the table (9)
     * @return 9
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Tells if the Sudoku table represented by this state is completely filled.
     * @return true if the state is full, false otherwise.
     */
    public boolean isFull() {
        return noDigits == SIZE * SIZE;
    }

    /**
     * Gives a canonical textual representation of this state, which can also be parsed by {@code SudokuStateParser}.
     *
     * Example for the puzzle taken from Wikipedia:
     * <pre>
     * 5,3, , ,7
     * 6, , ,1,9,5, , ,
     *  ,9,8, , , , ,6,
     * 8, , , ,6, , , ,3
     * 4, , ,8, ,3, , ,1
     * 7, , , ,2, , , ,6
     *  ,6, , , , ,2,8,
     *  , , ,4,1,9, , ,5
     *  , , , ,8, , ,7,9
     * </pre>
     * @see SudokuStateParser
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (isDigitSet(row, column)) {
                    sb.append(getDigit(row, column));
                }
                else {
                    sb.append(' ');
                }
                if (column < SIZE - 1) {
                    sb.append(',');
                }
            }
            if (row < SIZE - 1) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    /**
     * Gives a copy of the Sudoku state.
     * @return a new instance of {@code SudokuState} that equals to this.
     */
    public SudokuState copy() {
        SudokuState clone = new SudokuState();
        clone.table = table.clone();
        clone.excluded = excluded.clone();
        clone.noDigits = noDigits;
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SudokuState) {
            SudokuState o = (SudokuState) obj;
            return Arrays.equals(o.table, this.table) &&
                Arrays.equals(o.excluded, this.excluded) &&
                o.noDigits == this.noDigits;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(table);
    }
}