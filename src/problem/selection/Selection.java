/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.selection;

import problem.habitat.Individual;
import problem.habitat.Population;

/**
 * Interface for the selection-process of the algorithm
 */
public interface Selection {

    /**
     * Selects an individual from a population
     * @param population the population to select from
     * @return the selected individual
     */
    public Individual select(Population population);
}
