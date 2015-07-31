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
 * Randomly selects to organisms of a population
 * and returns the fitter one
 */
public final class TournamentSelection implements Selection {

    private int number;

    /**
     * Main construcor
     * @param number The number of organisms participating in the selection-process
     */
    public TournamentSelection(int number) {
        this.number = number;
    }

    @Override
    public Organism select(Population population) {
        Organism best = population.getRandomOrganism();
        Organism organism;
        for (int i = 1; i < this.number; i++) {
            organism = population.getRandomOrganism();
            if (organism.compareTo(best) > 0) {
                best = organism;
            }
        }
        return best;
    }
}
