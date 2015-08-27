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
import problem.registry.Registry;
import problem.selection.Selection;

/**
 * Represents the world. Evolves a population till a satisfied
 * solution has been found.
 */
public class World {

    Population population;
    Population backupPopulation;
    Crossover crossover;
    Selection selection;

    /**
     * Default constructor
     */
    public World() {
        this.crossover = (Crossover) Registry.getInstance().get("crossover");
        this.selection = (Selection) Registry.getInstance().get("selection");
        this.population = new Population();
        this.population.createRandom();
        this.backupPopulation = new Population();
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
        Population newPopulation = new Population();
        newPopulation.add(this.population.get(10)); //TODO: make cleaner
        int counter = 10;
        for (Individual sculpture : this.backupPopulation) {
            this.crossover.setSculpture(sculpture);
            while (this.crossover.needsParent()) this.crossover.addParent(this.selection.select(this.population));
            newPopulation.add(this.crossover.cross());
            if (++counter == (int) Registry.getInstance().get("population-size")) break;
        }
        this.backupPopulation = this.population;
        this.population = newPopulation;
    }
}
