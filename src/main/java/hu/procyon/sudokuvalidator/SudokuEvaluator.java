package hu.procyon.sudokuvalidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * SudokuEvaluator
 */
public class SudokuEvaluator {

    public List<SudokuCoordinate> findEmptyCoordinates(SudokuState state) {
        List<SudokuCoordinate> result = new ArrayList<>();
        for (int row = 0; row < state.getSize(); row++) {
            for (int column = 0; column < state.getSize(); column++) {
                if (!state.isDigitSet(row, column)) {
                    if (state.getNumberOfPossibleDigits(row, column) > 0) {
                        result.add(new SudokuCoordinate(row, column));
                    }
                    else {
                        return Collections.emptyList();
                    }
                }
            }
        }
        result.sort((c1, c2) -> Integer.compare(noSteps(state, c1), noSteps(state, c2)));
        return result;
    }

    private int noSteps(SudokuState state, SudokuCoordinate coordinate) {
        return state.getNumberOfPossibleDigits(coordinate.row, coordinate.column);
    }

    public Iterator<SudokuStep> getStepIterator(SudokuState state) {
        List<SudokuCoordinate> coordinates = findEmptyCoordinates(state);
        return new SudokuStepIterator(state, coordinates);
    }

    private class SudokuStepIterator implements Iterator<SudokuStep> {
        private final Iterator<SudokuCoordinate> coordIter;
        private final SudokuState state;
        private SudokuCoordinate coord;
        private List<Integer> availableDigits;
        private int digitIndex;

        private SudokuStepIterator(SudokuState state, List<SudokuCoordinate> coordinates) {
            this.coordIter = new ArrayList<>(coordinates).iterator();
            this.state = state;
            this.coord = null;
            this.availableDigits = null;
            this.digitIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return (coord != null && digitIndex < availableDigits.size()) || coordIter.hasNext();
        }

        @Override
        public SudokuStep next() {
            if ((coord == null || availableDigits.size() <= digitIndex) && coordIter.hasNext()) {
                coord = coordIter.next();
                availableDigits = getAvailableDigits(coord);
                digitIndex = 0;
            }
            if (coord != null && digitIndex < availableDigits.size()) {
                SudokuStep step = new SudokuStep(coord, availableDigits.get(digitIndex), availableDigits.size() < 2);
                digitIndex++;
                return step;
            }
            throw new NoSuchElementException();
        }

		private List<Integer> getAvailableDigits(SudokuCoordinate coordinate) {
            List<Integer> result = new ArrayList<>();
            for (int digit = 1; digit <= state.getSize(); digit++) {
                if (state.canSetDigit(coordinate.row, coordinate.column, digit)) {
                    result.add(digit);
                }
            }
			return result;
		}

    }
}