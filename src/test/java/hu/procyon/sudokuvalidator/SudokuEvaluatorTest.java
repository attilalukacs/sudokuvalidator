package hu.procyon.sudokuvalidator;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 * SudokuEvaluatorTest
 */
public class SudokuEvaluatorTest {
    private SudokuState state;
    private SudokuEvaluator eval;

    @Before
    public void setup() {
        state = mock(SudokuState.class);
        eval = new SudokuEvaluator();
    }

    @Test
    public void testSortCoordinates() {
        //TODO
    }

}