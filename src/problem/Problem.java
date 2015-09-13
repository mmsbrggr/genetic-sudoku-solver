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

import java.util.*;

/**
 * Represents the problem. Takes an unfinished sudoku-grid
 * and preprocesses different things
 */
public class Problem {

    private SudokuGrid grid;
    private Set<Integer> variableFields;
    private Map<Integer, Integer[]> variableFieldsForRows;
    private Map<Integer, Integer[]> variableFieldsForColumns;
    private Map<Integer, Integer[]> variableFieldsForBlocks;
    private Map<Integer, Set<Integer>> validNumbers;
    private Map<Integer, Integer[]> validNumbersArray;

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
    public Integer[] getVariableFields() {
        return this.variableFields.toArray(new Integer[this.variableFields.size()]);
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
        return this.validNumbersArray.get(index);
    }

    /**
     * @param index the index of the field
     * @param number the number which gets tested on validity
     * @return true iff a given number is valid for a field with a given index
     */
    public boolean numberIsValidForIndex(int index, int number) {
        return this.validNumbers.get(index).contains(number);
    }

    /**
     * Preprocesses different things
     */
    private void preprocess() {
        this.preprocessVariableFields();
        this.presolveGrid();
        this.preprocessVariableFieldsUnits();
        this.validNumbersToArray();
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
        this.variableFieldsForRows = new HashMap<>();
        this.variableFieldsForColumns = new HashMap<>();
        this.variableFieldsForBlocks = new HashMap<>();

        for (int unit = 0; unit < this.grid.getSideLength(); unit++) {
            ArrayList<Integer> variableFieldsForRow = new ArrayList<>();
            ArrayList<Integer> variableFieldsForColumn = new ArrayList<>();
            ArrayList<Integer> variableFieldsForBlock = new ArrayList<>();

            for (int i = 0; i < this.grid.getSideLength(); i++) {

                int rowField = this.grid.getIndexByRow(unit, i);
                if (this.variableFields.contains(rowField)) {
                    variableFieldsForRow.add(rowField);
                }

                int columnField = this.grid.getIndexByColumn(unit, i);
                if (this.variableFields.contains(columnField)) {
                    variableFieldsForColumn.add(columnField);
                }

                int blockField = this.grid.getIndexByBlock(unit, i);
                if (this.variableFields.contains(blockField)) {
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
     * holds an array of numbers which don't interfere with the already fixed
     * number on the problems grid
     */
    private void preprocessValidNumbers() {
        this.validNumbers = new HashMap<>();
        for (int index : this.getVariableFields()) {
            Set<Integer> validNumbers = new HashSet<>();
            for (int i = this.grid.getValidMin(); i <= this.grid.getValidMax(); i++) {
                this.grid.write(index, i);
                if (this.grid.getConflicts() == 0) {
                    validNumbers.add(i);
                }
                this.grid.write(index, 0);
            }
            this.validNumbers.put(index, validNumbers);
        }
    }

    private void validNumbersToArray() {
        this.validNumbersArray = new HashMap<>();
        for (Map.Entry<Integer, Set<Integer>> validNumbers : this.validNumbers.entrySet()) {
            this.validNumbersArray.put(
                    validNumbers.getKey(),
                    validNumbers.getValue().toArray(new Integer[validNumbers.getValue().size()])
            );
        }
    }

    /**
     * Presolves the grid with the determined numbers
     */
    private void presolveGrid() {
        boolean gridChanged = true;
        while (gridChanged && this.variableFields.size() > -1) {
            this.preprocessValidNumbers();
            gridChanged = this.insertFixedFields();
        }
        this.preprocessValidNumbers();
    }

    /**
     * Takes each variable field and looks if one of it's valid numbers is unique among its block, row or column.
     * If so the valid number gets inserted into the grid
     * @return true iff a number for a field has been inserted
     */
    private boolean insertFixedFields() {
        Iterator<Integer> iterator = this.variableFields.iterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            boolean fieldInserted;

            fieldInserted = this.insertFixedFieldsForUnit(index, this.grid.getRowForIndex(index));
            if (fieldInserted) {
                iterator.remove();
                return true;
            }

            fieldInserted = this.insertFixedFieldsForUnit(index, this.grid.getColumnForIndex(index));
            if (fieldInserted) {
                iterator.remove();
                return true;
            }

            fieldInserted = this.insertFixedFieldsForUnit(index, this.grid.getBlockForIndex(index));
            if (fieldInserted) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    /**
     * Intersects the valid numbers of a field with a given index with all valid numbers from a given unit.
     * If a exactly one valid number is left over it gets inserted into the grid
     * @param index The index to look for a valid number for
     * @param unit the unit from which the valid numbers get taken for the intersection
     * @return true iff a valid number has been found and inserted
     */
    private boolean insertFixedFieldsForUnit(int index, int[] unit) {
        Set<Integer> validNumbersCopy = new HashSet<>(this.validNumbers.get(index));
        for (int unitIndex: unit) {
            if (unitIndex != index && this.variableFields.contains(unitIndex)) {
                Iterator<Integer> iterator = validNumbersCopy.iterator();
                while (iterator.hasNext()) {
                    int validNumber = iterator.next();
                    if (this.validNumbers.get(unitIndex).contains(validNumber)) {
                        iterator.remove();
                    }
                }
            }
        }
        if (validNumbersCopy.size() == 1) {
            this.grid.write(index, validNumbersCopy.iterator().next());
            return true;
        }

        return false;
    }
}
