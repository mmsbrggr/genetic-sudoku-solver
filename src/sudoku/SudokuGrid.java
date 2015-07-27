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
 * Represents a sudoku-grid
 */
public class SudokuGrid implements Grid {

    private final int blockSize;
    private int conflicts;
    private Unit[] rows;
    private Unit[] columns;
    private Unit[] blocks;

    /**
     * The main constructor
     * @param blockSize the size of a single block (the whole grid has a side length of blockSize squared)
     */
    public SudokuGrid(int blockSize) {
        this.blockSize = blockSize;
        this.rows = new Unit[blockSize * blockSize];
        this.columns = new Unit[blockSize * blockSize];
        this.blocks = new Unit[blockSize * blockSize];
        this.fillBlank(this.rows);
        this.fillBlank(this.columns);
        this.fillBlank(this.blocks);
    }

    @Override
    public void write(int x, int y, int number) throws NotAllowedValueException {
        int blockNum = ((y / this.blockSize) * this.blockSize) + ((x / this.blockSize));
        int blockIndex = ((y % this.blockSize) * this.blockSize) + (x % this.blockSize);

        this.conflicts -= this.rows[y].getConflicts();
        this.conflicts -= this.columns[x].getConflicts();
        this.conflicts -= this.blocks[blockNum].getConflicts();

        this.rows[y].write(x, number);
        this.columns[x].write(y, number);
        this.blocks[blockNum].write(blockIndex, number);

        this.conflicts += this.rows[y].getConflicts();
        this.conflicts += this.columns[x].getConflicts();
        this.conflicts += this.blocks[blockNum].getConflicts();
    }

    @Override
    public int getConflicts() {
        return this.conflicts;
    }

    @Override
    public int getBlockSize() {
        return this.blockSize;
    }

    /**
     * Fills a unit array with blank sudoku-units
     * @param arr the array to fill
     */
    private void fillBlank(Unit[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new SudokuUnit(1, this.blockSize * this.blockSize, 1);
        }
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
        return value + line;
    }
}
