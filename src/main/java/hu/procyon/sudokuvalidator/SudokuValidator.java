package hu.procyon.sudokuvalidator;

import java.io.File;

public class SudokuValidator {
    public static void main(String[] args) {
        if (args.length == 1) {
            File stateFile = new File(args[0]);
            if (stateFile.exists()) {
                SudokuStateParser parser = new SudokuStateParser();
                try {
                    SudokuState state = parser.parseFile(stateFile);
                    System.out.println(state.toString());
                } catch (SudokuParseException e) {
                    error("Sudoku state file is invalid.", 4);
                } catch (SudokuStateException e) {
                    error("Sudoku state is invalid.", 1);
                }
            }
            else {
                error("Sudoku state file " + stateFile.getPath() + " does NOT exist.", 2);
            }
        }
        else {
            error("SudokuValidator requires exactly 1 argument - state file.", 3);
        }
    }

    private static void error(final String message, final int errorCode) {
        System.err.println(message);
        System.exit(errorCode);
    }

}
