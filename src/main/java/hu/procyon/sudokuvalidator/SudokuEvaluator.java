package hu.procyon.sudokuvalidator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Used to evaluate a Sudoku state in order to let the back-tracking algorithm traverse
 * the state space in an efficient manner.
 */
public class SudokuEvaluator {

    /**
     * Finds a coordinate in the Sudoku state which is empty and the number of
     * possible digits to fill is minimum, or null if there is no empty cell in the state.
     *
     * @param state the Sudoku state to be scanned
     * @return coordinates of the selected empty cell in the Sudoku state, or null in case there is no such cell
     */
    public SudokuCoordinate findLeastAmbigousEmptyCoordinate(SudokuState state) {
        SudokuCoordinate result = null;
        int noPossibleDigits = Integer.MAX_VALUE;
        for (int row = 0; row < state.getSize(); row++) {
            for (int column = 0; column < state.getSize(); column++) {
                if (!state.isDigitSet(row, column)) {
                    int tempNoDigits = state.getNumberOfPossibleDigits(row, column);
                    if (tempNoDigits < noPossibleDigits) {
                        result = new SudokuCoordinate(row, column);
                        noPossibleDigits = tempNoDigits;
                    }
                    if (tempNoDigits < 1) {
                        return result;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Utility method to get the possible digits given a coordinate in a Sudoku state.
     *
     * @param state
     * @param coordinate
     * @return
     */
    private int noSteps(SudokuState state, SudokuCoordinate coordinate) {
        return state.getNumberOfPossibleDigits(coordinate.row, coordinate.column);
    }

    /**
     * Gives an iterator for possible steps in a given state, or null if there are no more steps.
     *
     * @param state
     * @return
     */
    public Iterator<SudokuStep> getStepIterator(SudokuState state) {
        SudokuCoordinate coordinate = findLeastAmbigousEmptyCoordinate(state);
        return coordinate != null && 1 <= noSteps(state, coordinate)
            ? new SudokuStepIterator(state, coordinate)
            : null;
    }

    /**
     * Iterator class for Sudoku steps given a Sudoku states and coordinate.
     */
    private class SudokuStepIterator implements Iterator<SudokuStep> {
        private final SudokuState state;
        private final SudokuCoordinate coord;
        private int nextDigit;

        private SudokuStepIterator(SudokuState state, SudokuCoordinate coordinate) {
            this.state = state;
            this.coord = coordinate;
            this.nextDigit = 0;
            findNext();
        }

        private void findNext() {
            do {
                nextDigit++;
            } while (nextDigit <= state.getSize() && state.isDigitExcluded(coord.row, coord.column, nextDigit));
        }

        @Override
        public boolean hasNext() {
            return nextDigit <= state.getSize();
        }

        @Override
        public SudokuStep next() {
            if (hasNext()) {
                int digit = nextDigit;
                findNext();
                return new SudokuStep(coord, digit, hasNext());
            }
            throw new NoSuchElementException();
        }
    }
}