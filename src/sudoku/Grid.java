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
    public void write(int x, int y, int number);

    /**
     * Writes a number into the grid
     * @param index the index starting from left to right and top-down
     * @param number the number to write
     */
    public void write(int index, int number);

    /**
     * @return The number of conflicts within the whole grid (row, column and blocks get added together)
     */
    public int getConflicts();

    /**
     * @return an array with the indexes of the empty fields. The indexes go from left to right and top-down
     */
    public int[] getEmptyFields();

    /**
     * @return the size of a block in a grid
     */
    public int getBlockSize();

    /**
     * @return the length of one side of the grid
     */
    public int getSideLength();

    /**
     * @return the minimum valid value
     */
    public int getValidMin();

    /**
     * @return the maximum valid value
     */
    public int getValidMax();
}
