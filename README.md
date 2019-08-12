# sudokuvalidator

Command line tool for Sudoku validation. The tool can tell if a Sudoku puzzle is valid, that is, it can be solved. If the puzzle can be solved, it returns with 0 and shows the `Valid Sudoku puzzle.` message. If the puzzle cannot be solved or there is some other error with the puzzle file, the program ends with non-zero return code and the error message on the standard error channel.

## Usage

The puzzle is given in a simple text file. Java Runtime must be installed on the machine and java executable has to be on the PATH.

On Windows:

```
sudokuvalidator.bat PUZZLEFILE
```

On Linux:

```
.\sudokuvalidator.sh PUZZLEFILE
```

### Sudoku puzzle format

The puzzle should be in a format similar to the following example:
```
9, ,4, ,6, ,7, ,1
 ,2, ,4, ,3, ,8,
8, , , , , , , ,4
 , ,1,8,4,9,6, ,
 , , , , , , , ,
 , ,3,2,5,7,9, ,
4, , , , , , , ,7
 ,8, ,6, ,4, ,5,
5, ,6, ,8, ,2, ,3
```

The validator allows a relaxed syntax: the white spaces can be omitted, and a row must be defined only to its last given digit.

## Development

The project is written in Java and Maven is used for building the main program.

To create a package, use `make` on Linux:

```
make package
```

To clean the development files, use the `clean` target:

```
make clean
```

### Javadoc

Javadoc can be created using Maven:

```
mvn javadoc:javadoc
```
