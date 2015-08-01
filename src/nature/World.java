/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package nature;

/**
 * Represents the world. Holds a population of organisms and evolves them
 * @param <T> the organism type
 */
public final class World<T extends Organism> {

    private Population<T> oldPopulation;
    private Population<T> population;
    private Selection selection;
    private Crossover crossover;
    private double mutationRate;
    private T best;


    public World(Population<T> population, Selection selection, Crossover crossover, double mutationRate) {
        this.population = population;
        this.oldPopulation = population.getEqual();
        this.best = this.population.getRandomOrganism();
        this.selection = selection;
        this.crossover = crossover;
        this.mutationRate = mutationRate;
    }

    public T getSolution() {
        for (int i = 0; i < 1000; i++) {
            this.evolve();
        }
        return this.best;
    }

    private void evolve() {
        for (T sculpture : this.oldPopulation) {
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
