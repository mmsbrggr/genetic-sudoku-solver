/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.habitat;

import problem.Problem;
import problem.registry.Registry;
import sudoku.SudokuGrid;

import java.util.Arrays;

/**
 * Represents an individual in the world of sudoku-grids
 */
public final class Individual extends SudokuGrid implements Comparable<Individual> {

    private Problem problem;
    private int[] gene;

    /**
     * Default constructor. Takes an unfinished grid
     * and fills it randomly.
     */
    public Individual() {
        super((SudokuGrid) Registry.getInstance().get("grid"));
        this.problem = (Problem) Registry.getInstance().get("problem");
        this.gene = new int[this.problem.getVariableFields().length];
        this.fillRandom();
    }

    /**
     * Copy constructor
     * @param other the individual to copy
     */
    public Individual(Individual other) {
        super(other);
        this.problem = other.problem;
        this.gene = Arrays.copyOf(other.getGene(), other.getGene().length);
    }

    /**
     * Fills the grid with random numbers
     */
    private void fillRandom() {
        for (int i = 0; i < this.gene.length; i++) {
            this.writeChromosomeRandom(i);
        }
    }

    /**
     * Overwrites the chromosome at a given index with a given number
     * @param index the index to overwrite at
     * @param number the number to insert
     */
    public void writeChromosome(int index, int number) {
        this.write(this.problem.getVariableFields()[index], number);
        this.gene[index] = number;
    }

    /**
     * overwrites the gene at a given position with random number
     * @param index the index to overwrite at
     */
    public void writeChromosomeRandom(int index) {
        int gridIndex = this.problem.getVariableFields()[index];
        Integer[] validNumbers = this.problem.getValidNumbersForIndex(gridIndex);
        int randomIndex = (int) (Math.random() * validNumbers.length);
        this.writeChromosome(index, validNumbers[randomIndex]);
    }

    /**
     * @param index the index in the gene to read from
     * @return the chromosome at a given gene-index
     */
    public int readChromosome(int index) {
        return this.getGene()[index];
    }

    /**
     * @return the individual's gene
     */
    public int[] getGene() {
        return this.gene;
    }

    /**
     * @return the fitness of the individual
     */
    public double getFitness() {
        return Math.pow((1.0 / (this.getConflicts() + 1)), 3);
    }

    @Override
    public int compareTo(Individual o) {
        return (this.getFitness() < o.getFitness()) ? 1 : -1;
    }
}
