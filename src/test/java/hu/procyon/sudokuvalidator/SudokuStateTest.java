package hu.procyon.sudokuvalidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * SudokuStateTest
 */
public class SudokuStateTest {
    private SudokuState state;

    @Before
    public void setup() {
        state = new SudokuState();
    }

    @Test
    public void testSetDigit() {
        assertTrue(state.setDigit(1, 2, 9));
        assertTrue(state.isDigitSet(1, 2));
        assertEquals(9, state.getDigit(1, 2));
    }

    @Test
    public void testRowExclusion() {
        assertTrue(state.setDigit(1, 2, 9));
        assertTrue(state.isDigitExcluded(1, 1, 9));
        assertTrue(state.isDigitExcluded(1, 2, 9));
        assertTrue(state.isDigitExcluded(1, 3, 9));
        assertTrue(state.isDigitExcluded(1, 5, 9));
        assertTrue(state.isDigitExcluded(1, 8, 9));
        assertFalse(state.isDigitExcluded(0, 3, 9));
        assertFalse(state.isDigitExcluded(7, 0, 9));
    }

    @Test
    public void testColumnExclusion() {
        assertTrue(state.setDigit(1, 2, 9));
        assertTrue(state.isDigitExcluded(0, 2, 9));
        assertTrue(state.isDigitExcluded(1, 2, 9));
        assertTrue(state.isDigitExcluded(2, 2, 9));
        assertTrue(state.isDigitExcluded(5, 2, 9));
        assertTrue(state.isDigitExcluded(8, 2, 9));
        assertFalse(state.isDigitExcluded(4, 0, 9));
        assertFalse(state.isDigitExcluded(7, 8, 9));
    }

    @Test
    public void testBlockExclusion() {
        assertTrue(state.setDigit(1, 2, 9));
        assertTrue(state.isDigitExcluded(0, 0, 9));
        assertTrue(state.isDigitExcluded(1, 0, 9));
        assertTrue(state.isDigitExcluded(2, 0, 9));
        assertTrue(state.isDigitExcluded(0, 1, 9));
        assertTrue(state.isDigitExcluded(2, 2, 9));
        assertFalse(state.isDigitExcluded(4, 3, 9));
        assertFalse(state.isDigitExcluded(2, 8, 9));
    }

    @Test
    public void testInvalidDigits() {
        assertTrue(state.isDigitValid(1));
        assertTrue(state.isDigitValid(9));
        assertFalse(state.isDigitValid(0));
        assertFalse(state.isDigitValid(10));
        assertFalse(state.isDigitValid(-1));
    }

    @Test
    public void testInvalidMoveSamePosition() {
        assertTrue(state.setDigit(1, 2, 9));
        assertFalse(state.setDigit(1, 2, 9));
    }

    @Test
    public void testInvalidMoveSamePosition2() {
        assertTrue(state.setDigit(1, 2, 9));
        assertFalse(state.setDigit(1, 2, 6));
    }

    @Test
    public void testInvalidMoveSameRow() {
        assertTrue(state.setDigit(1, 2, 9));
        assertFalse(state.setDigit(1, 7, 9));
    }

    @Test
    public void testInvalidMoveSameColumn() {
        assertTrue(state.setDigit(1, 2, 9));
        assertFalse(state.setDigit(7, 2, 9));
    }

    @Test
    public void testInvalidMoveSameBlock() {
        assertTrue(state.setDigit(1, 2, 9));
        assertFalse(state.setDigit(0, 0, 9));
    }

}