package hu.procyon.sudokuvalidator;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * SudokuBacktracker
 */
public class SudokuBacktracker {

    SudokuEvaluator eval = new SudokuEvaluator();

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

    private BacktrackState getBacktrackState(SudokuState state) {
        Iterator<SudokuStep> stepIterator = eval.getStepIterator(state);
        return new BacktrackState(state, stepIterator);
    }


    private class BacktrackState {
        final SudokuState state;
        final Iterator<SudokuStep> stepIterator;
        private BacktrackState(SudokuState state, Iterator<SudokuStep> stepIterator) {
            this.state = state;
            this.stepIterator = stepIterator;
        }
    }
}