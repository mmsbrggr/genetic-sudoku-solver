/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.mutation;

import problem.habitat.Individual;

/**
 * Interface for the mutation-process of the algorithm<
 */
public interface Mutation {

    /**
     * Mutates a given individual
     * @param individual The individual to mutate
     * @return the mutated individual
     */
    Individual mutate(Individual individual);
}
