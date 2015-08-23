/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem;

import problem.crossover.Crossover;
import problem.habitat.Individual;
import problem.habitat.Population;
import problem.selection.Selection;

/**
 * Represents the world. Evolves a population till a satisfied
 * solution has been found.
 */
public class World {

    Problem problem;
    Selection selection;
    Crossover crossover;
    Population population;
    Population backupPopulation;
    double mutationRate;

    /**
     * Default constructor
     * @param problem The problem to solve
     * @param selection The selection process to use
     * @param crossover The crossover process to use
     * @param size The population size
     * @param mutationRate rate of mutation
     */
    public World(Problem problem, Selection selection, Crossover crossover, int size, double mutationRate) {
        this.problem = problem;
        this.selection = selection;
        this.crossover = crossover;
        this.mutationRate = mutationRate;
        this.population = new Population(size, this.problem);
        this.population.createRandom();
        this.backupPopulation = new Population(size, this.problem);
        this.backupPopulation.createRandom();
    }

    public Individual findSolution() {
        for (int i = 0; i < 20000000; i++) {
            this.evolvePopulation();
            System.out.println(this.population.getBest().getConflicts());
        }
        return this.population.getBest();
    }

    private void evolvePopulation() {
        Population newPopulation = new Population(this.population.getSize(), this.problem);
        newPopulation.add(this.population.get(10)); //TODO: make cleaner
        int counter = 10;
        for (Individual sculpture : this.backupPopulation) {
            this.crossover.setSculpture(sculpture);
            while (this.crossover.needsParent()) this.crossover.addParent(this.selection.select(this.population));
            newPopulation.add(this.crossover.cross(this.mutationRate));
            if (++counter == this.population.getSize()) break;
        }
        this.backupPopulation = this.population;
        this.population = newPopulation;
    }
}
