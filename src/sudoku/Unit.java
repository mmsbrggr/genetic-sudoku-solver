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
     * Inserts a number into a block
     * @param position The position at which the number gets inserted - must exist
     * @param number The number to insert
     */
    public void write(int position, int number) throws NotAllowedValueException;

    /**
     * @return The number of conflicts within the unit
     */
    public int getConflicts();

    /**
     * @return the minimum allowed number
     */
    public int getRangeMin();

    /**
     * @return the maximum allowed number
     */
    public int getRangeMax();

    /**
     * @return the number of occurrences each number between the minimum and the maximum must have
     */
    public int getOccurrences();
}
