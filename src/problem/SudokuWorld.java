/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem;

import nature.Crossover;
import nature.Selection;
import problem.SudokuSolution;
import sudoku.SudokuGrid;

/**
 * Represents the world. Holds a population of sudokusolutions and evolves them
 */
public final class SudokuWorld {

    private SudokuPopulation population;
    private SudokuGrid problem;
    private Selection selection;
    private Crossover crossover;
    private double mutationRate;

    /**
     * Main constructor. Injects all dependencies
     * @param problem The sudoku-grid to solve
     * @param populationSize The size of a population
     * @param selection the selection to use for the evolution-process
     * @param crossover The crossover to use for the evolution-process
     * @param mutationRate the possibility of an organism to mutate
     */
    public SudokuWorld(SudokuGrid problem, int populationSize, Selection selection, Crossover crossover, double mutationRate) {
        this.population = new SudokuPopulation(populationSize);
        this.population.createRandom(problem);
        this.problem = problem;
        this.selection = selection;
        this.crossover = crossover;
        this.mutationRate = mutationRate;
    }

    public SudokuGrid getSolution() {
        for (int i = 0; i < 10000; i++) {
            this.evolvePopulation();
        }
        return null;
    }

    private void evolvePopulation() {

    }
}
