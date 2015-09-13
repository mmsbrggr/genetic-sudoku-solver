/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package sudoku;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a sudoku-grid
 */
public class SudokuGrid implements Grid {

    private final int blockSize;
    private int conflicts;
    private int countEmptyFields;
    private int sideLength;
    private SudokuUnit[] rows;
    private SudokuUnit[] columns;
    private SudokuUnit[] blocks;

    /**
     * The main constructor
     * @param blockSize the size of a single block (the whole grid has a side length of blockSize squared)
     */
    public SudokuGrid(int blockSize) {
        this.blockSize = blockSize;
        this.rows = new SudokuUnit[blockSize * blockSize];
        this.columns = new SudokuUnit[blockSize * blockSize];
        this.blocks = new SudokuUnit[blockSize * blockSize];
        this.sideLength = this.blockSize * this.blockSize;
        this.countEmptyFields = this.sideLength * this.sideLength;
        this.fillBlank(this.rows);
        this.fillBlank(this.columns);
        this.fillBlank(this.blocks);
    }

    /**
     * Copy constructor
     * @param other the SudokuGrid to copy
     */
    public SudokuGrid(SudokuGrid other) {
        this.blockSize = other.getBlockSize();
        this.sideLength = other.getSideLength();
        this.conflicts = other.getConflicts();
        this.countEmptyFields = other.countEmptyFields;
        this.rows = this.copyUnits(other.rows);
        this.columns = this.copyUnits(other.columns);
        this.blocks = this.copyUnits(other.blocks);
    }

    /**
     * Makes a deep copy of a SudokuUnit-array
     * @param other the unit-array to copy
     * @return a copy of the passed SudokuUnit-array
     */
    private SudokuUnit[] copyUnits(SudokuUnit[] other) {
        SudokuUnit[] copy = new SudokuUnit[other.length];
        for (int i = 0; i < other.length; i++) {
            copy[i] = new SudokuUnit(other[i]);
        }
        return copy;
    }

    /**
     * Fills a unit array with blank sudoku-units
     * @param arr the array to fill
     */
    private void fillBlank(Unit[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new SudokuUnit(1, this.getSideLength(), 1);
        }
    }

    @Override
    public int getValidMin() {
        return 1;
    }

    @Override
    public int getValidMax() {
        return this.getBlockSize() * this.getBlockSize();
    }

    @Override
    public void write(int x, int y, int number) {
        int blockNum = ((y / this.blockSize) * this.blockSize) + ((x / this.blockSize));
        int blockIndex = ((y % this.blockSize) * this.blockSize) + (x % this.blockSize);

        if (this.rows[y].read(x) == 0) countEmptyFields -= 1;
        if (number == 0) countEmptyFields += 1;

        this.conflicts -= this.rows[y].getConflicts();
        this.conflicts -= this.columns[x].getConflicts();
        this.conflicts -= this.blocks[blockNum].getConflicts();

        this.rows[y].write(x, number);
        this.rows[y].setGridIndex(x, this.getIndexByRow(y, x)); //TODO: do not implement here
        this.columns[x].write(y, number);
        this.columns[x].setGridIndex(y, this.getIndexByColumn(x, y)); //TODO: do not implement here
        this.blocks[blockNum].write(blockIndex, number);
        this.blocks[blockNum].setGridIndex(blockIndex, this.getIndexByBlock(blockNum, blockIndex)); //TODO: do not implement here

        this.conflicts += this.rows[y].getConflicts();
        this.conflicts += this.columns[x].getConflicts();
        this.conflicts += this.blocks[blockNum].getConflicts();
    }

    @Override
    public int read(int index) {
        return this.rows[index / (this.getSideLength())].read(index % this.getSideLength());
    }

    @Override
    public void write(int index, int number) {
        this.write(index % this.getSideLength(), index / this.getSideLength(), number);
    }

    @Override
    public int getConflicts() {
        return this.conflicts;
    }

    @Override
    public int getBlockSize() {
        return this.blockSize;
    }

    @Override
    public int getSideLength() {
        return this.sideLength;
    }

    @Override
    public String toString() {
        String line = new String(
                new char[this.rows[0].print(this.blockSize).length()]
        ).replace('\0', '-') + '\n';
        String value = "";
        for(int i = 0; i < this.rows.length; i++) {
            if (i % this.blockSize == 0) {
                value += line;
            }
            value += this.rows[i].print(this.blockSize) + '\n';
        }
        value += line;
        return value.trim();
    }

    @Override
    public Set<Integer> getEmptyFields() {
        Set<Integer> emptyFields = new HashSet<>();
        for (int rowIndex = 0; rowIndex < this.rows.length; rowIndex++) {
            int[] row = this.rows[rowIndex].toArray();
            for (int colIndex = 0; colIndex < row.length; colIndex++) {
                if (row[colIndex] == 0) {
                    emptyFields.add((rowIndex * this.getSideLength()) + colIndex);
                }
            }
        }
        return emptyFields;
    }

    @Override
    public int getIndexByRow(int row, int position) {
        return row * this.getSideLength() + position;
    }

    @Override
    public int getIndexByColumn(int column, int position) {
        return position * this.getSideLength() + column;
    }

    @Override
    public int getIndexByBlock(int block, int position) {
        return ((block / this.getBlockSize()) * this.getSideLength() * this.getBlockSize()) +
                (position / this.getBlockSize()) * this.getSideLength() +
                ((block % this.getBlockSize()) * this.getBlockSize()) +
                (position % this.getBlockSize());
    }

    @Override
    public int getRowByIndex(int index) {
        return index / this.getSideLength();
    }

    @Override
    public int getColumnByIndex(int index) {
        return index % this.getSideLength();
    }

    @Override
    public int getBlockByIndex(int index) {
        return (this.getRowByIndex(index) / this.getBlockSize()) * this.getBlockSize() +
                (this.getColumnByIndex(index) / this.getBlockSize());
    }

    @Override
    public int[] getRowForIndex(int index) {
        return this.rows[this.getRowByIndex(index)].getGridIndices();
    }

    @Override
    public int[] getColumnForIndex(int index) {
        return this.columns[this.getColumnByIndex(index)].getGridIndices();
    }

    @Override
    public int[] getBlockForIndex(int index) {
        return this.blocks[this.getBlockByIndex(index)].getGridIndices();
    }
}
