/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem;

import nature.Gene;
import nature.Organism;
import sudoku.SudokuGrid;

/**
 * Represents a solution (not necessary valid) for a sudoku problem
 */
public class SudokuSolution extends SudokuGrid implements Organism<Integer> {

    // fields which are empty in the original problem
    private int[] variableFields;
    // maps the position of a dna in the gene to the position on the grid
    private int[] mapDnaToGrid;
    private Gene<Integer> gene;

    /**
     * Main constructor - Takes an unfinished sudoku-grid and guesses the solution
     * @param sudokuProblem the original solution
     */
    public SudokuSolution(SudokuGrid sudokuProblem) {
        super(sudokuProblem);
        this.gene = new Gene<>();
        this.variableFields = sudokuProblem.getEmptyFields();
        this.mapDnaToGrid = new int[this.variableFields.length];
        this.fillRandom();
    }

    /**
     * Fills the variable fields in the grid with random valid numbers and
     * initializes the array which maps the dna-positions to the positions on the grid
     */
    private void fillRandom() {
        int dnaPos = 0;
        for (int index : this.variableFields) {
            int value = this.getRandomValidNumber();
            this.write(index, value);
            this.gene.add(value);
            this.mapDnaToGrid[dnaPos] = index;
            dnaPos += 1;
        }
    }

    /**
     * Returns the position in the grid for a given position of a dna
     * @param dnaPosition the position of the dna
     * @return the index of the corresponding field in the grid
     */
    private int getGridPosition(int dnaPosition) {
        return this.mapDnaToGrid[dnaPosition];
    }

    private int getRandomValidNumber() {
        return (int) (Math.random() * this.getSideLength() + 1);
    }

    @Override
    public Gene<Integer> getGene() {
        return this.gene;
    }

    @Override
    public void setDna(int pos, Integer dna) {
        this.gene.set(pos, dna);
        this.write(this.getGridPosition(pos), dna);
    }

    @Override
    public Integer getDna(int pos) {
        return this.gene.get(pos);
    }

    @Override
    public void mutate() {
        int pos = (int) (Math.random() * this.gene.size());
        this.setDna(pos, this.getRandomValidNumber());
    }

    @Override
    public int getFitness() {
        return (-1) * this.getConflicts() + (this.getSideLength() * this.getSideLength());
    }

    @Override
    public int compareTo(Organism o) {
        return Integer.compare(this.getFitness(), o.getFitness());
    }
}
