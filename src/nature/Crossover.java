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
     * Crosses multiple organisms into one organsim
     * @param sculpture the organism to change the dna from
     * @param organisms organisms which get combined into one
     */
    public void cross(Organism sculpture, Organism[] organisms);
}
