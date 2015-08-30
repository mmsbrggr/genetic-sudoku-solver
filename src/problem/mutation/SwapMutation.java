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
 * Mutates an individual by swapping to numbers from either rows, columns or blocks
 */
public class SwapMutation implements Mutation {

    @Override
    public Individual mutate(Individual individual) {
        return individual;
    }
}
