/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package nature;

/**
 * Crossover process of a genetic algorithm
 */
public interface Crossover {

    /**
     * @return true iff enough parents were added
     */
    public boolean hasEnoughParents();

    /**
     * Adds a parent to the crossover
     * @param parent the parent which gets used to create a new organism
     */
    public void addParent(Gene parent);

    /**
     * Crosses the added parents to a new gene
     */
    public Gene cross();
}
