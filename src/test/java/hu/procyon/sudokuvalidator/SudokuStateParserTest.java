package hu.procyon.sudokuvalidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * SudokuStateParserTest
 */
public class SudokuStateParserTest {

    private SudokuStateParser parser;

    @Before
    public void setup() {
        parser = new SudokuStateParser();
    }

    @Test
    public void testParseLineNormal() throws SudokuParseException {
        List<Integer> digits = parser.parseLine(0, "1,2,3,4,5,6,7,8,9");
        assertEquals(9, digits.size());
        assertEquals(new Integer(1), digits.get(0));
        assertEquals(new Integer(5), digits.get(4));
        assertEquals(new Integer(9), digits.get(8));
    }

    @Test
    public void testParseLineEmpty() throws SudokuParseException {
        List<Integer> digits = parser.parseLine(0, "");
        assertEquals(1, digits.size());
        assertNull(digits.get(0));
    }

    @Test
    public void testParseLinePartial() throws SudokuParseException {
        List<Integer> digits = parser.parseLine(0, ",,,,1,2");
        assertEquals(6, digits.size());
        assertNull(digits.get(0));
        assertNull(digits.get(1));
        assertNull(digits.get(3));
        assertEquals(new Integer(1), digits.get(4));
        assertEquals(new Integer(2), digits.get(5));
    }

    @Test(expected = SudokuParseException.class)
    public void testParseLineInvalid() throws SudokuParseException {
        parser.parseLine(0, "x");
    }

}