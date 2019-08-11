package hu.procyon.sudokuvalidator;

import java.io.File;

/**
 * Main class for the sudokuvalidator application.
 */
public class SudokuValidator {
    public static void main(String[] args) {
        if (args.length == 1) {
            File stateFile = new File(args[0]);
            if (stateFile.exists()) {
                processStateFile(stateFile);
            }
            else {
                error("Sudoku puzzle file " + stateFile.getPath() + " does NOT exist.", 2);
            }
        }
        else {
            error("SudokuValidator requires exactly 1 argument: puzzle file path", 3);
        }
    }

    private static void processStateFile(File stateFile) {
        SudokuStateParser parser = new SudokuStateParser();
        try {
            SudokuState state = parser.parseFile(stateFile);
            SudokuState solved = new SudokuBacktracker().findSolution(state);
            if (solved != null) {
                System.out.println("Valid Sudoku puzzle.");
                //System.out.println("Solution:");
                //System.out.println(solved);
            }
            else {
                error("Invalid puzzle.", 1);
            }
        } catch (SudokuParseException e) {
            error("Sudoku puzzle file is invalid.", 4);
        } catch (SudokuStateException e) {
            error("Invalid puzzle.", 1);
        }
    }

    private static void error(final String message, final int errorCode) {
        System.err.println(message);
        System.exit(errorCode);
    }
}
