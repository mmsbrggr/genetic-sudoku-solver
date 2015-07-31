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
 * Represents a population of organisms
 */
public interface Population extends Iterable<Organism> {

    /**
     * @return the size of the population
     */
    public int getSize();

    /**
     * @return a random organism from the population
     */
    public Organism getRandomOrganism();

    /**
     * @return a population of the same size an similar organisms
     */
    public Population getEqual();
}
