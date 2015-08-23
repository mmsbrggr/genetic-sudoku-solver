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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Represents the problem. Takes an unfinished sudoku-grid
 * and preprocesses different things
 */
public class Problem {

    private SudokuGrid problem;
    private int[] variableFields;
    private Map<Integer, Integer[]> validNumbers;

    /**
     * Default constructor. Takes an unfinished sudoku-grid and
     * preprocesses different things
     * @param problem The unfinished sudoku-grid which represents the problem
     */
    public Problem(SudokuGrid problem) {
        this.problem = problem;
        this.preprocess();
    }

    /**
     * @return variableFields
     */
    public int[] getVariableFields() {
        return this.variableFields;
    }

    /**
     * @return the unfinished sudoku-grid
     */
    public SudokuGrid getGrid() {
        return this.problem;
    }

    /**
     * @param index the index to return the valid numbers for
     * @return array of valid numbers for a given index
     */
    public Integer[] getValidNumbersForIndex(int index) {
        return this.validNumbers.get(index);
    }

    /**
     * Preprocesses different things
     */
    private void preprocess() {
        this.preprocessVariableFields();
        this.preprocessValidNumbers();
    }

    /**
     * Calculates an array of indexes of fields which have to be filled
     */
    private void preprocessVariableFields() {
        this.variableFields = this.problem.getEmptyFields();
    }

    /**
     * Calculates an array which for each index on the problem-grid
     * holds an array of numbers which don't interfere with the fixed
     * number on the problems grid
     */
    private void preprocessValidNumbers() {
        boolean gridChanged = true;
        while (gridChanged) {
            gridChanged = false;
            this.preprocessVariableFields();
            this.validNumbers = new Hashtable<>();
            for (int index : this.getVariableFields()) {
                ArrayList<Integer> validNumbers = new ArrayList<>();
                for (int i = this.problem.getValidMin(); i <= this.problem.getValidMax(); i++) {
                    this.problem.write(index, i);
                    if (this.problem.getConflicts() == 0) {
                        validNumbers.add(i);
                    }
                    this.problem.write(index, 0);
                }
                if (validNumbers.size() == 1) {
                    this.problem.write(index, validNumbers.get(0));
                    gridChanged = true;
                }
                this.validNumbers.put(index, validNumbers.toArray(new Integer[validNumbers.size()]));
            }
        }
    }
}
