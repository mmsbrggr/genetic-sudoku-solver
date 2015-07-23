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
 * Thrown if having to do with an invalid value
 */
public class NotAllowedValueException extends Exception {

    private int min;
    private int max;

    /**
     * main constructor
     * @param min the minimum allowed value within the range (incl.)
     * @param max the maximum allowed value within the range (incl.)
     */
    public NotAllowedValueException(int min, int max) {
        super();
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return this.min;
    }

    public int getmax() {
        return this.max;
    }
}
