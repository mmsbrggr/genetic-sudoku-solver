/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.crossover;

import com.sun.javaws.exceptions.InvalidArgumentException;
import problem.habitat.Individual;

/**
 * Crosses multiple parents to one individual, where for each chromosome
 * each parent has the same probability to be chosen.
 */
public class UniformCrossover implements Crossover {

    private Individual sculpture;
    private Individual[] parents;
    private int parentIndex;

    /**
     * Default constructor
     * @param numParents the number of parents to use for the crossover
     */
    public UniformCrossover(int numParents) {
        this.parents = new Individual[numParents];
        this.parentIndex = 0;
    }

    @Override
    public void setSculpture(Individual sculpture) {
        this.sculpture = sculpture;
    }

    @Override
    public void addParent(Individual individual) {
        if (individual == null) {
            throw new IllegalArgumentException("Parent cannot be null in a uniform-crossover");
        }
        this.parents[this.parentIndex] = individual;
        this.parentIndex += 1;
    }

    @Override
    public boolean needsParent() {
        return this.parentIndex < this.parents.length;
    }

    @Override
    public Individual cross(double mutationRate) {
        if (this.needsParent()) {
            throw new RuntimeException("Uniformselection needs more parents before cross can be executed");
        }
        for (int i = 0; i < this.sculpture.getGene().length; i++) {
            if (Math.random() < mutationRate) {
                this.sculpture.writeChromosomeRandom(i);
            } else {
                int randomIndex = (int) (Math.random() * this.parents.length);
                this.sculpture.writeChromosome(i, this.parents[randomIndex].readChromosome(i));
            }
        }
        this.parentIndex = 0;
        return this.sculpture;
    }
}
