/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package sudoku;

import java.util.Arrays;

/**
 * Represents a sudoku unit block (column, row or block)
 */
final class SudokuUnit implements Unit {

    private final int rangeMin;
    private final int rangeMax;
    private final int occurrences;
    private int conflicts;
    private int[] representation;
    private int[] usages;

    /**
     * The main constructor
     * @param rangeMin the minimum allowed value within the range (incl.)
     * @param rangeMax the maximum allowed value within the range (incl.)
     * @param occurrences the number of occurrences each value within the range must have
     */
    public SudokuUnit(int rangeMin, int rangeMax, int occurrences) {
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.occurrences = occurrences;
        this.representation = new int[(rangeMax - rangeMin + 1) * this.occurrences];
        this.usages = new int[rangeMax - rangeMin + 1];
        this.conflicts = 0;
    }

    /**
     * Copy constructor
     * @param other the SudokuUnit to copy
     */
    public SudokuUnit(SudokuUnit other) {
        this.rangeMin = other.getRangeMin();
        this.rangeMax = other.getRangeMax();
        this.occurrences = other.getOccurrences();
        this.conflicts = other.getConflicts();
        this.representation = Arrays.copyOf(other.representation, other.representation.length);
        this.usages = Arrays.copyOf(other.usages, other.usages.length);
    }

    @Override
    public int getConflicts() {
        return this.conflicts;
    }

    @Override
    public int getRangeMin() {
        return this.rangeMin;
    }

    @Override
    public int getRangeMax() {
        return this.rangeMax;
    }

    @Override
    public int getOccurrences() {
        return this.occurrences;
    }

    @Override
    public void write(int position, int number) {
        int oldNumber = this.representation[position];
        if (oldNumber >= this.getRangeMin()) {
            this.decreaseConflict(oldNumber);
            this.setUsages(oldNumber, this.getUsages(oldNumber) - 1);
        }
        this.representation[position] = number;
        if (number >= this.getRangeMin()) {
            this.setUsages(number, this.getUsages(number) + 1);
            this.increaseConflict(number);
        }
    }

    @Override
    public int read(int position) {
        return this.representation[position];
    }

    @Override
    public String print(int blockSize) {
        String value = "";
        for (int i = 0; i < this.representation.length; i++) {
            if (i % blockSize == 0) {
                value += "| ";
            }
            //value += this.representation[i] + " ";
            value += String.format("%" + (Math.log10(this.getRangeMax()) + 1) + "d", this.representation[i]) + " ";
        }
        return value + "|";
    }

    @Override
    public int[] toArray() {
        return this.representation;
    }

    /**
     * Sets the usages of a number
     * @param number the number to set the usages for
     * @param usages the new usages
     */
    private void setUsages(int number, int usages) {
        this.usages[number - 1] = usages;
    }

    /**
     * Returns the usages for a number
     * @param number the number to return the usages for
     * @return usages
     */
    private int getUsages(int number) {
        return this.usages[number - 1];
    }

    /**
     * If there is a conflict with the passed number decreases it by one
     * @param number The number for which there may be conflict
     */
    private void decreaseConflict(int number) {
        if (this.getUsages(number) > 1) {
            this.conflicts -= 1;
        }
    }

    /**
     * If there is a conflict with the passed number increases it by one
     * @param number The number for which there may be a conflict
     */
    private void increaseConflict(int number) {
        if (this.getUsages(number) > 1) {
            this.conflicts += 1;
        }
    }
}
