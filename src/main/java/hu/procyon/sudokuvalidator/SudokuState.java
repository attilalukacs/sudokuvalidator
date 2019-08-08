package hu.procyon.sudokuvalidator;

/**
 * SudokuState
 */
public class SudokuState {
    private static final int N = 3;
    private static final int SIZE = N * N;

    private byte[] table = new byte[SIZE * SIZE];
    private short[] excluded = new short[SIZE * SIZE];

    public boolean isDigitSet(final int row, final int column) {
        return table[row * SIZE + column] != 0;
    }

    public byte getDigit(final int row, final int column) {
        return table[row * SIZE + column];
    }

    public boolean isDigitExcluded(final int row, final int column, final byte digit) {
        short mask = (short)(1 << digit);
        return (excluded[row * SIZE + column] & mask) != 0;
    }

    public boolean canSetDigit(final int row, final int column, final byte digit) {
        boolean cannotSet = isDigitSet(row, column) || isDigitExcluded(row, column, digit);
        return !cannotSet;
    }

    public boolean setDigit(final int row, final int column, final byte digit) {
        boolean isValidMove = canSetDigit(row, column, digit) && isDigitValid(digit);
        if (isValidMove) {
            table[row * SIZE + column] = digit;
            excludeColumn(column, digit);
            excludeRow(row, digit);
            excludeBlock(row, column, digit);
        }
        return isValidMove;
    }

    public boolean isDigitValid(final byte digit) {
        return 1 <= digit && digit <= SIZE;
    }

    private void excludeColumn(final int column, final byte digit) {
        short mask = (short)(1 << digit);
        for (int row = 0; row < SIZE; row++) {
            excluded[row * SIZE + column] |= mask;
        }
    }

    private void excludeRow(final int row, final byte digit) {
        short mask = (short)(1 << digit);
        for (int column = 0; column < SIZE; column++) {
            excluded[row * SIZE + column] |= mask;
        }
    }

    private void excludeBlock(final int row, final int column, final byte digit) {
        int blockRow = row / N * N;
        int blockColumn = column / N * N;
        short mask = (short)(1 << digit);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                excluded[(blockRow + i) * SIZE + (blockColumn + j)] |= mask;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isDigitSet(i, j)) {
                    sb.append(getDigit(i, j)).append(" ");
                }
                else {
                    sb.append("  ");
                }
            }
        }
        return sb.toString();
    }
}