/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package io;

/**
 * Class reads and returns a sudoku-grid
 */
public final class InvalidInputException extends Exception {

    private String message;

    public InvalidInputException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
