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
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertTrue(state.isDigitSet(1, 2));
        assertEquals((byte) 9, state.getDigit(1, 2));
    }

    @Test
    public void testRowExclusion() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertTrue(state.isDigitExcluded(1, 1, (byte)9));
        assertTrue(state.isDigitExcluded(1, 2, (byte)9));
        assertTrue(state.isDigitExcluded(1, 3, (byte)9));
        assertTrue(state.isDigitExcluded(1, 5, (byte)9));
        assertTrue(state.isDigitExcluded(1, 8, (byte)9));
        assertFalse(state.isDigitExcluded(0, 3, (byte)9));
        assertFalse(state.isDigitExcluded(7, 0, (byte)9));
    }

    @Test
    public void testColumnExclusion() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertTrue(state.isDigitExcluded(0, 2, (byte)9));
        assertTrue(state.isDigitExcluded(1, 2, (byte)9));
        assertTrue(state.isDigitExcluded(2, 2, (byte)9));
        assertTrue(state.isDigitExcluded(5, 2, (byte)9));
        assertTrue(state.isDigitExcluded(8, 2, (byte)9));
        assertFalse(state.isDigitExcluded(4, 0, (byte)9));
        assertFalse(state.isDigitExcluded(7, 8, (byte)9));
    }

    @Test
    public void testBlockExclusion() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertTrue(state.isDigitExcluded(0, 0, (byte)9));
        assertTrue(state.isDigitExcluded(1, 0, (byte)9));
        assertTrue(state.isDigitExcluded(2, 0, (byte)9));
        assertTrue(state.isDigitExcluded(0, 1, (byte)9));
        assertTrue(state.isDigitExcluded(2, 2, (byte)9));
        assertFalse(state.isDigitExcluded(4, 3, (byte)9));
        assertFalse(state.isDigitExcluded(2, 8, (byte)9));
    }

    @Test
    public void testInvalidDigits() {
        assertTrue(state.isDigitValid((byte)1));
        assertTrue(state.isDigitValid((byte)9));
        assertFalse(state.isDigitValid((byte)0));
        assertFalse(state.isDigitValid((byte)10));
        assertFalse(state.isDigitValid((byte)-1));
    }

    @Test
    public void testInvalidMoveSamePosition() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertFalse(state.setDigit(1, 2, (byte)9));
    }

    @Test
    public void testInvalidMoveSamePosition2() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertFalse(state.setDigit(1, 2, (byte)6));
    }

    @Test
    public void testInvalidMoveSameRow() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertFalse(state.setDigit(1, 7, (byte)9));
    }

    @Test
    public void testInvalidMoveSameColumn() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertFalse(state.setDigit(7, 2, (byte)9));
    }

    @Test
    public void testInvalidMoveSameBlock() {
        assertTrue(state.setDigit(1, 2, (byte)9));
        assertFalse(state.setDigit(0, 0, (byte)9));
    }

}