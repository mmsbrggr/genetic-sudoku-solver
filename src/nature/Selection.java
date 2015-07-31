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
 * Selection process of a genetic algorithm
 */
public interface Selection {

    /**
     * Takes a population and returns an organism
     * @param population the population the select the organism from
     * @return the selected organism
     */
    public Organism select(Population population);
}
