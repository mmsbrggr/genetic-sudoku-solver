/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package io;

import sudoku.SudokuGrid;
import java.util.Scanner;

/**
 * Class reads and returns a sudoku-grid
 */
public class GridReader {

    /**
     * Reads and returns a sudoku-grid form the standard-input
     * @return a sudoku-grid
     */
    public static SudokuGrid read() {
        try {
            Scanner scanner = new Scanner(System.in);
            GridWriter.printLabel("Size of a block ('3' for standard sudoku)");
            int blockSize = GridReader.readBlockSize(scanner);
            SudokuGrid grid = new SudokuGrid(blockSize);
            GridWriter.printBiglabel("Sudoku ('0' for empty fields)");
            GridReader.fillGrid(grid, scanner);
            return grid;
        } catch (InvalidInputException exception) {
            System.out.println("Error: " + exception.getMessage());
            return null;
        }
    }

    /**
     * @return the blocksize for the sudoku-grid
     * @param scanner The scanner to read from
     * @throws InvalidInputException
     */
    public static int readBlockSize(Scanner scanner) throws InvalidInputException {
        if (!scanner.hasNextLine() || !scanner.hasNextInt()) {
            throw new InvalidInputException("Please define a block-size (normal sudokus have '3').");
        }
        int blockSize = scanner.nextInt();
        scanner.nextLine();

        return blockSize;
    }

    /**
     * Fills a passed grid with numbers from the standard-input
     * @param grid the grid to fill with numbers
     * @param scanner The scanner to read from
     * @throws InvalidInputException
     */
    public static void fillGrid(SudokuGrid grid, Scanner scanner) throws InvalidInputException {
        int line = 0;
        while (scanner.hasNextLine()) {
            if (line >= grid.getSideLength()) {
                throw new InvalidInputException("Too many rows passed.");
            }
            GridReader.fillGridRow(grid, line, scanner.nextLine());
            line += 1;
        }
        if (line < grid.getSideLength()) {
            throw new InvalidInputException("Too less rows passed.");
        }
    }

    /**
     * Fills a given row with a given index of a given grid with numbers
     * @param grid the grid to fill with numbers
     * @param rowIndex the index of the row which should be filled in the grid
     * @param row the row of numbers encoded as a string to fill into the grid
     * @throws InvalidInputException
     */
    public static void fillGridRow(SudokuGrid grid, int rowIndex, String row) throws InvalidInputException {
        if (!row.contains(" ")) {
            row = row.replaceAll(".(?!$)", "$0 ").trim(); //space after each character
        }
        Scanner scanner = new Scanner(row);
        int numberIndex = 0;
        while (scanner.hasNextInt()) {
            if (numberIndex >= grid.getSideLength()) {
                throw new InvalidInputException("Too many numbers passed in row '" + rowIndex + "'.");
            }
            grid.write(numberIndex, rowIndex, scanner.nextInt());
            numberIndex += 1;
        }
        if (numberIndex < grid.getSideLength()) {
            throw new InvalidInputException("Too less numbers passed in row '" + rowIndex + "'.");
        }
    }
}
