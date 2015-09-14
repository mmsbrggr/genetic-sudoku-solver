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
 * Represents a value unit
 */
interface Unit {

    /**
     * Inserts a number into a unit
     * @param position The position at which the number gets inserted - must exist
     * @param number The number to insert
     */
    void write(int position, int number);

    /**
     * Reads a number from the unit
     * @param position The position to read the number from - must exist
     */
    int read(int position);

    /**
     * @return The number of conflicts within the unit
     */
    int getConflicts();

    /**
     * @return the minimum allowed number
     */
    int getRangeMin();

    /**
     * @return the maximum allowed number
     */
    int getRangeMax();

    /**
     * @return the number of occurrences each number between the minimum and the maximum must have
     */
    int getOccurrences();

    /**
     * Sets the index a cell with a given position has on the grid
     */
    void setGridIndex(int position, int index);

    /**
     * @return the indices the cells have on the grid
     */
    int[] getGridIndices();

    /**
     * @return the unit in a array representation
     */
    int[] toArray();

    /**
     * Returns a readable string representation
     * @param blockSize The number of values after which a separator gets placed
     * @return a String representation of the block
     */
    String print(int blockSize);
}
