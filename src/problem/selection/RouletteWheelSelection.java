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
 * Implements a standard roulette-wheel selection for a genetic-algorithm
 */
public class RouletteWheelSelection implements Selection {

    @Override
    public Individual select(Population population) {
        double ball = Math.random();
        for (Individual individual : population) {
            double value = individual.getFitness() / population.getTotalFitness();
            if (ball <= value) {
                return individual;
            }
            ball -= value;
        }
        throw new RuntimeException("Roulettewheel selection failed to select an individual");
    }
}
