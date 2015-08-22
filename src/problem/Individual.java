/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem;
import sudoku.SudokuGrid;

import java.util.Arrays;

/**
 * Represents a sudoku-grid
 */
public class Individual extends SudokuGrid {

    private int[] variableFields;
    private SudokuGrid problem;

    /**
     * Default constructor. Takes an unfinished grid
     * and fills it randomly.
     * @param grid an unfinished grid
     */
    public Individual(SudokuGrid grid) {
        super(grid.getBlockSize());
        this.problem = grid;
        int[] emptyFields = grid.getEmptyFields();
        this.variableFields = Arrays.copyOf(emptyFields, emptyFields.length);
    }

    /**
     * @param index the index to at wich the value is changed. The The index
     * @param value the value to write at the index
     * @return true iff a given value inserted at at a given index would
     * produce a conflict with the original (non variable) problem
     */
    private boolean createsProblemConflict(int index, int value) {
        boolean returnValue = false;
        this.problem.write(index, value);
        if (this.problem.getConflicts() > 0) {
            returnValue = true;
        }
        this.problem.write(index, 0);
        return returnValue;
    }

    private void fillRandom() {

    }
}
