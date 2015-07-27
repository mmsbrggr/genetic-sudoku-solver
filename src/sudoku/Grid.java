/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package sudoku;

/**
 * Represents a value grid
 */
public interface Grid {

    /**
     * Writes a number into the grid
     * @param x the x value to write in
     * @param y the y value to write in
     * @param number the number to write into the (x,y)-position
     */
    public void write(int x, int y, int number) throws NotAllowedValueException;

    /**
     * @return The number of conflicts within the whole grid (row, column and blocks get added together)
     */
    public int getConflicts();

    /**
     * @return the size of a block in a grid
     */
    public int getBlockSize();
}
