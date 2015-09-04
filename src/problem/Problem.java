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
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

/**
 * Represents the problem. Takes an unfinished sudoku-grid
 * and preprocesses different things
 */
public class Problem {

    private SudokuGrid grid;
    private int[] variableFields;
    private Map<Integer, Integer[]> variableFieldsForRows;
    private Map<Integer, Integer[]> variableFieldsForColumns;
    private Map<Integer, Integer[]> variableFieldsForBlocks;
    private Map<Integer, Integer[]> validNumbers;

    /**
     * Default constructor. Takes an unfinished sudoku-grid and
     * preprocesses different things
     * @param grid The unfinished sudoku-grid which represents the problem
     */
    public Problem(SudokuGrid grid) {
        this.grid = grid;
        this.preprocess();
    }

    /**
     * @return variableFields
     */
    public int[] getVariableFields() {
        return this.variableFields;
    }

    /**
     * @param row the index of the row to get the variable fields for
     * @return an array of variable fields in a given row
     */
    public Integer[] getVariableFieldsForRow(int row) {
        return this.variableFieldsForRows.get(row);
    }

    /**
     * @param column the index of the column to get the variable fields for
     * @return an array of variable fields in a given column
     */
    public Integer[] getVariableFieldsForColumn(int column) {
        return this.variableFieldsForColumns.get(column);
    }

    /**
     * @param block the index of the block to get the variable fields for
     * @return an array of variable fields in a given block
     */
    public Integer[] getVariableFieldsForBlock(int block) {
        return this.variableFieldsForBlocks.get(block);
    }

    /**
     * @param index the index to return the valid numbers for
     * @return array of valid numbers for a given index
     */
    public Integer[] getValidNumbersForIndex(int index) {
        return this.validNumbers.get(index);
    }

    /**
     * @param index the index of the field
     * @param number the number which gets tested on validity
     * @return true iff a given number is valid for a field with a given index
     */
    public boolean numberIsValidForIndex(int index, int number) {
        return Arrays.binarySearch(this.validNumbers.get(index), number) > -1;
    }

    /**
     * Preprocesses different things
     */
    private void preprocess() {
        this.preprocessVariableFields();
        this.preprocessValidNumbers();
        this.preprocessVariableFieldsUnits();
    }

    /**
     * Calculates an array of indexes of fields which have to be filled
     */
    private void preprocessVariableFields() {
        this.variableFields = this.grid.getEmptyFields();
    }

    /**
     * Calculates for each row, column and block which fields need to be filled
     */
    private void preprocessVariableFieldsUnits() {
        this.variableFieldsForRows = new Hashtable<>();
        this.variableFieldsForColumns = new Hashtable<>();
        this.variableFieldsForBlocks = new Hashtable<>();

        for (int unit = 0; unit < this.grid.getSideLength(); unit++) {
            ArrayList<Integer> variableFieldsForRow = new ArrayList<>();
            ArrayList<Integer> variableFieldsForColumn = new ArrayList<>();
            ArrayList<Integer> variableFieldsForBlock = new ArrayList<>();

            for (int i = 0; i < this.grid.getSideLength(); i++) {

                int rowField = this.grid.getIndexByRow(unit, i);
                if (Arrays.binarySearch(this.variableFields, rowField) > -1) {
                    variableFieldsForRow.add(rowField);
                }

                int columnField = this.grid.getIndexByColumn(unit, i);
                if (Arrays.binarySearch(this.variableFields, columnField) > -1) {
                    variableFieldsForColumn.add(columnField);
                }

                int blockField = this.grid.getIndexByBlock(unit, i);
                if (Arrays.binarySearch(this.variableFields, blockField) > -1) {
                    variableFieldsForBlock.add(blockField);
                }
            }

            this.variableFieldsForRows.put(unit, variableFieldsForRow.toArray(new Integer[variableFieldsForRow.size()]));
            this.variableFieldsForColumns.put(unit, variableFieldsForColumn.toArray(new Integer[variableFieldsForColumn.size()]));
            this.variableFieldsForBlocks.put(unit, variableFieldsForBlock.toArray(new Integer[variableFieldsForBlock.size()]));
        }
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
                for (int i = this.grid.getValidMin(); i <= this.grid.getValidMax(); i++) {
                    this.grid.write(index, i);
                    if (this.grid.getConflicts() == 0) {
                        validNumbers.add(i);
                    }
                    this.grid.write(index, 0);
                }
                if (validNumbers.size() == 1) {
                    this.grid.write(index, validNumbers.get(0));
                    gridChanged = true;
                }
                this.validNumbers.put(index, validNumbers.toArray(new Integer[validNumbers.size()]));
            }
        }
    }
}
