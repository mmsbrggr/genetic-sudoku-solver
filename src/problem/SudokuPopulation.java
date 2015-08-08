/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */


package problem;

import nature.Population;
import sudoku.SudokuGrid;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a population of sudoku-solutions
 */
public class SudokuPopulation implements Population<SudokuSolution> {

    private final int size;
    private ArrayList<SudokuSolution> population;
    private int avgFitness;

    /**
     * Main constructor creates a random population of a given size for a given problem
     *
     * @param size    the population size
     */
    public SudokuPopulation(int size) {
        this.size = size;
        this.population = new ArrayList<>();
    }

    /**
     * Fills the population with random solutions
     * @param problem the unsolved sudoku-grid
     */
    public void createRandom(SudokuGrid problem) {
        for (int i = 0; i < this.getSize(); i++) {
            SudokuSolution solution = new SudokuSolution(problem);
            this.add(solution);
        }
    }

    @Override
    public void add(SudokuSolution solution) {
        this.population.add(solution);
        this.avgFitness = solution.getFitness() / this.size;
    }

    @Override
    public Iterator<SudokuSolution> iterator() {
        return this.population.iterator();
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public int getAvgFitness() {
        return this.avgFitness;
    }
}