/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.crossover;

import problem.habitat.Individual;

/**
 * Interface for the crossover-process of the algorithm
 */
public interface Crossover {

    /**
     * Adds the sculpture which gets used to write the new genes on
     * @param sculpture the individual to use as the sculpture
     */
    public void setSculpture(Individual sculpture);

    /**
     * Adds an individual which acts as a parent in the crossover
     * @param individual a parent
     */
    public void addParent(Individual individual);

    /**
     * @return true iff the crossover needs more parents
     */
    public boolean needsParent();

    /**
     * Executes the crossover
     * @param mutationRate the likelihood that a chromosome mutates
     * @return the changed sculpture
     */
    public Individual cross(double mutationRate);
}
