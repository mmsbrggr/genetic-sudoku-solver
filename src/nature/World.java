/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package nature;

import java.util.Iterator;

/**
 * Represents the world. Holds a population of organisms and evolves them
 */
public final class World {

    private Population oldPopulation;
    private Population population;
    private Selection selection;
    private Crossover crossover;
    private double mutationRate;
    private Organism best;


    public World(Population population, Selection selection, Crossover crossover, double mutationRate) {
        this.population = population;
        this.oldPopulation = population.getEqual();
        this.best = this.population.getRandomOrganism();
        this.selection = selection;
        this.crossover = crossover;
        this.mutationRate = mutationRate;
    }

    public Organism getSolution() {
        for (int i = 0; i < 100000; i++) {
            this.evolve();
            if (i % 1000 == 0) {
                System.out.println(this.best.getFitness());
            }
        }
        return this.best;
    }

    private void evolve() {
        Iterator<Organism> iterator = this.oldPopulation.iterator();
        while(iterator.hasNext()) {
            Organism sculpture = iterator.next();
            if (sculpture != this.best) {
                Organism[] parents = {this.selection.select(this.population), this.selection.select(this.population)};
                this.crossover.cross(sculpture, parents);
                if (Math.random() < this.mutationRate) {
                    sculpture.mutate();
                }
                if (sculpture.compareTo(this.best) > 0) {
                    this.best = sculpture;
                }
            }
        }
        this.population = oldPopulation;
        this.oldPopulation = population;
    }

}
