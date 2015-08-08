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
public interface Population<T extends Organism> extends Iterable<T> {

    /**
     * @return the size of the population
     */
    public int getSize();

    /**
     * @return the avg fitness of the population
     */
    public int getAvgFitness();

    /**
     * Adds an organism to the population
     * @param organism the organism to add
     */
    public void add(T organism);
}
