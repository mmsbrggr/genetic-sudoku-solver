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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a population of sudoku-solutions
 */
public class SudokuPopulation implements Population<SudokuSolution> {

    private final int size;
    private SudokuSolution[] population;
    private SudokuGrid problem;

    /**
     * Main constructor creates a random population of a given size for a given problem
     * @param size the population size
     * @param problem the problem for which a population should get created
     */
    public SudokuPopulation(int size, SudokuGrid problem) {
        this.size = size;
        this.problem = problem;
        this.population = new SudokuSolution[this.size];
        this.createRandom();
    }

    /**
     * Fills the population with random solutions
     */
    private void createRandom() {
        for (int i = 0; i < this.getSize(); i++) {
            this.population[i] = new SudokuSolution(this.problem);
        }
    }

    @Override
    public Iterator<SudokuSolution> iterator() {
        return new PopulationIterator(this.population);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public SudokuSolution getRandomOrganism() {
        return this.population[((int) (Math.random() * this.population.length))];
    }

    @Override
    public Population getEqual() {
        return new SudokuPopulation(this.getSize(), this.problem);
    }

    private static final class PopulationIterator implements Iterator<SudokuSolution> {
        int cursor = 0;
        SudokuSolution[] population;

        PopulationIterator(SudokuSolution[] population) {
            this.population = population;
        }

        @Override
        public SudokuSolution next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.cursor += 1;
            return this.population[this.cursor - 1];
        }

        @Override
        public boolean hasNext() {
            return this.cursor < this.population.length;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
