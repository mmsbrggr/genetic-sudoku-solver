/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

import sudoku.*;

public class App {

    public static void main(String[] args) {
        int size = 3;
        Grid grid = new SudokuGrid(size);

        for (int x = 0; x < size * size; x++) {
            for (int y = 0; y < size * size; y++) {
                try {
                    grid.write(x, y, (int) (Math.random() * size * size + 1));
                } catch (NotAllowedValueException exc) {
                    System.out.println(exc);
                }
            }
        }
        System.out.println(grid);
        System.out.println(grid.getConflicts() + " conflicts");
    }
}
