package hu.procyon.sudokuvalidator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class implements the back-tracking algorithm for {@code SudokuState}.
 * Instantiate and use its {@link #findSolution(SudokuState)} method to find a solution
 * for a current Sudoku state.
 */
public class SudokuBacktracker {

    private SudokuEvaluator eval = new SudokuEvaluator();

    /**
     * Finds a solution for the given {@code SudokuState} if there is one or
     * returns null if there isn't.
     *
     * @param initial the starting Sudoku table state
     * @return final state or null if there is no solution
     */
    public SudokuState findSolution(final SudokuState initial) {
        Deque<BacktrackState> stack = new LinkedList<>();
        stack.push(getBacktrackState(initial.copy()));
        while (!stack.isEmpty()) {
            SudokuState state = stack.peek().state;
            if (state.isFull()) {
                return state;
            }
            Iterator<SudokuStep> stepIterator = stack.peek().stepIterator;
            if (stepIterator != null && stepIterator.hasNext()) {
                SudokuStep step = stepIterator.next();
                SudokuState newState = state.copy();
                newState.setDigit(step.coordinate.row, step.coordinate.column, step.digit);
                stack.push(getBacktrackState(newState));
            }
            else {
                stack.remove();
            }
        }
        return null;
    }

    /**
     * Returns new state for the back-track based on the given Sudoku state.
     * @param state the newly reached {@link SudokuState}.
     * @return back-track state with Sudoku state and an iterator with the possible steps.
     */
    private BacktrackState getBacktrackState(SudokuState state) {
        Iterator<SudokuStep> stepIterator = eval.getStepIterator(state);
        return new BacktrackState(state, stepIterator);
    }

    /**
     * Data class for storing state for the back-tracking algorithm. It contains the
     * Sudoku state (in a {@link SudokuState} class) and in iterator for the possible steps.
     */
    private class BacktrackState {
        final SudokuState state;
        final Iterator<SudokuStep> stepIterator;
        private BacktrackState(SudokuState state, Iterator<SudokuStep> stepIterator) {
            this.state = state;
            this.stepIterator = stepIterator;
        }
    }
}