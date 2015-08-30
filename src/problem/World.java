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
import problem.mutation.Mutation;
import problem.registry.Registry;
import problem.selection.Selection;

/**
 * Represents the world. Evolves a population till a satisfied
 * solution has been found.
 */
public class World {

    private Population population;
    private Population backupPopulation;
    private Crossover crossover;
    private Mutation mutation;
    private Selection selection;
    private int numberElites;
    private int generation;

    /**
     * Default constructor
     */
    public World() {
        this.crossover = (Crossover) Registry.getInstance().get("crossover");
        this.selection = (Selection) Registry.getInstance().get("selection");
        this.mutation = (Mutation) Registry.getInstance().get("mutation");
        this.numberElites = (int) (((int) Registry.getInstance().get("population-size")) * ((double) Registry.getInstance().get("elitism-rate")));
        this.population = new Population();
        this.population.createRandom();
        this.backupPopulation = new Population();
        this.backupPopulation.createRandom();
        this.generation = 1;
    }

    public Individual findSolution() {
        while (this.population.getBest().getConflicts() > 0) {
            this.evolvePopulation();
            System.out.println(this.population.getBest().getConflicts());
        }
        return this.population.getBest();
    }

    private void evolvePopulation() {
        Population newPopulation = new Population();
        this.addElites(newPopulation);
        this.addDescendants(newPopulation);
        this.backupPopulation = this.population;
        this.population = newPopulation;
        this.generation += 1;
    }

    private void addElites(Population newPopulation) {
        newPopulation.add(this.population.get(this.numberElites));
    }

    private void addDescendants(Population newPopulation) {
        int counter = this.numberElites;
        for (Individual sculpture : this.backupPopulation) {
            this.crossover.setSculpture(sculpture);
            while (this.crossover.needsParent()) this.crossover.addParent(this.selection.select(this.population));
            newPopulation.add(this.mutation.mutate(this.crossover.cross()));
            if (++counter == (int) Registry.getInstance().get("population-size")) break;
        }
    }
}
