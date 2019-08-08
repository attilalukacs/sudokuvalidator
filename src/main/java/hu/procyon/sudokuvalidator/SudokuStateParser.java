package hu.procyon.sudokuvalidator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * SudokuStateParser
 */
public class SudokuStateParser {

    public List<Integer> parseLine(final int lineNo, final String line) throws SudokuParseException {
        String[] elements = line.split(",");
        List<Integer> result = new ArrayList<>(elements.length);
        for (int i = 0; i < elements.length; i++) {
            String element = elements[i].trim();
            if (!element.isEmpty()) {
                try {
                    int digit = Integer.parseInt(element.trim());
                    result.add(digit);
                } catch (NumberFormatException e) {
                    throw new SudokuParseException("Invalid Sudoku digit: " + element, e, lineNo, i);
                }
            }
            else {
                result.add(null);
            }
        }
        return result;
    }

    public SudokuState parseFile(final File stateFile) throws SudokuParseException, SudokuStateException {
        SudokuState state = new SudokuState();
        try (LineNumberReader reader = new LineNumberReader(new FileReader(stateFile))) {
            for (int lineNo = 0; lineNo < state.getSize(); lineNo++) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                List<Integer> digits = parseLine(lineNo, line);
                for (int i = 0 ; i < digits.size(); i++) {
                    Integer digit = digits.get(i);
                    if (digit != null) {
                        setDigit(state, lineNo, i, digit);
                    }
                }
            }
        } catch (IOException e) {
            throw new SudokuParseException("Error while parsing Sudoku state file", e);
        }
        return state;
    }

    private static void setDigit(SudokuState state, int row, int column, int digit) throws SudokuStateException {
        if (state.isDigitValid(digit)) {
            if (state.canSetDigit(row, column, digit)) {
                state.setDigit(row, column, digit);
            }
            else {
                throw new SudokuStateException("It is not possible to set " + digit + " in position (" + row + "," + column + ")", row, column, digit);
            }
        }
        else {
            throw new SudokuStateException("Digit " + digit + " is not valid", row, column, digit);
        }
    }
}