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
    void write(int x, int y, int number);

    /**
     * Writes a number into the grid
     * @param index the index starting from left to right and top-down
     * @param number the number to write
     */
    void write(int index, int number);

    /**
     * @param index the index to read from
     * @return returns the number in the grid for a given index
     */
    int read(int index);

    /**
     * @return The number of conflicts within the whole grid (row, column and blocks get added together)
     */
    int getConflicts();

    /**
     * @return an array with the indexes of the empty fields. The indexes go from left to right and top-down
     */
    int[] getEmptyFields();

    /**
     * @return the size of a block in a grid
     */
    int getBlockSize();

    /**
     * @return the length of one side of the grid
     */
    int getSideLength();

    /**
     * @return the minimum valid value
     */
    int getValidMin();

    /**
     * @return the maximum valid value
     */
    int getValidMax();

    /**
     * @param row the index of the row
     * @param position the position in the row
     * @return the grid index by a given row and the position in that row
     */
    int getIndexByRow(int row, int position);

    /**
     * @param column the index of the column
     * @param position the position in the column
     * @return the grid index by a given column and the position in that column
     */
    int getIndexByColumn(int column, int position);

    /**
     * @param block the index of the block
     * @param position the position in the block
     * @return the grid index by a given block and the position in that block
     */
    int getIndexByBlock(int block, int position);
}
