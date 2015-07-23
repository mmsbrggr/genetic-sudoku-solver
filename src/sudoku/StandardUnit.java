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
        this.conflicts = this.representation.length;
    }

    @Override
    public void write(int position, int number) throws NotAllowedValueException {
        if (number < this.rangeMin || this.rangeMax < number) {
            throw new NotAllowedValueException(this.rangeMin, this.rangeMax);
        }
        this.representation[position] = number;
        // TODO: update conflicts and usages
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
}
