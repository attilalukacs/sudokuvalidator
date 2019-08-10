package hu.procyon.sudokuvalidator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * SudokuEvaluator
 */
public class SudokuEvaluator {

    public SudokuCoordinate findLeastAmbigousEmptyCoordinates(SudokuState state) {
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

    private int noSteps(SudokuState state, SudokuCoordinate coordinate) {
        return state.getNumberOfPossibleDigits(coordinate.row, coordinate.column);
    }

    public Iterator<SudokuStep> getStepIterator(SudokuState state) {
        SudokuCoordinate coordinate = findLeastAmbigousEmptyCoordinates(state);
        return coordinate != null && 1 <= noSteps(state, coordinate)
            ? new SudokuStepIterator(state, coordinate)
            : null;
    }

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
                return new SudokuStep(coord, digit, hasNext()); //TODO: hasNext() is not exactly single
            }
            throw new NoSuchElementException();
        }
    }
}