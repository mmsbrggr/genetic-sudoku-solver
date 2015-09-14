/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem;

import io.GridWriter;
import problem.crossover.Crossover;
import problem.habitat.Individual;
import problem.habitat.Population;
import problem.mutation.Mutation;
import problem.registry.Registry;
import problem.selection.Selection;

import java.util.ArrayList;

/**
 * Represents the world. Evolves a population till a satisfied
 * solution has been found.
 */
public final class World {

    private Population population;
    private Population backupPopulation;
    private Population restartPopulation;
    private Population generationElites;
    private ArrayList<Double> bestFitnessForGeneration;
    private int generation;
    private int epoch;

    private int numberElites;
    private Mutation mutation;
    private Crossover crossover;
    private Selection selection;
    private int populationSize;
    private int populationsBeforeRestart;

    /**
     * Default constructor
     */
    public World() {
        this.crossover = (Crossover) Registry.getInstance().get("crossover");
        this.selection = (Selection) Registry.getInstance().get("selection");
        this.mutation = (Mutation) Registry.getInstance().get("mutation");
        this.populationSize = (int) Registry.getInstance().get("population-size");
        this.populationsBeforeRestart = (int) Registry.getInstance().get("populations-before-restart");
        this.numberElites = (int) (this.populationSize * ((double) Registry.getInstance().get("elitism-rate")));
        this.population = new Population();
        this.population.createRandom();
        this.backupPopulation = new Population(this.population);
        this.restartPopulation = new Population(this.population);
        this.generationElites = new Population();
        this.bestFitnessForGeneration = new ArrayList<>();
        this.generation = 1;
        this.epoch = 1;
    }

    /**
     * Resets the population to the starting-population. If the generation-elites population is full
     * it is used as the new population. If this is not the case the best few individuals are added to the
     * generation-elites population
     */
    private void restart() {
        GridWriter.printElite(this.population.getBest(), this.epoch, this.generation, this.population.getAvgFitness(), this.population.getAvgConflicts());
        if (this.generationElites.isFull()) {
            this.restartPopulation = new Population(this.generationElites);
            this.generationElites = new Population();
        } else {
            this.generationElites.add(this.population.get(this.populationSize / this.populationsBeforeRestart));
        }
        this.population = new Population(this.restartPopulation);
        this.bestFitnessForGeneration = new ArrayList<>();
        this.epoch += 1;
        this.generation = 1;
    }

    /**
     * Finds a solution. Evolves a population and restart the evolution-process if necessary
     * @return a valid solution to the problem
     */
    public Individual findSolution() {
        while (this.population.getBest().getConflicts() > 0) {
            this.evolvePopulation();
            this.bestFitnessForGeneration.add(this.population.getBest().getFitness());
            if (this.bestFitnessForGeneration.size() - 20 >= 0 &&
                    this.population.getBest().getFitness() <= this.bestFitnessForGeneration.get(this.bestFitnessForGeneration.size() - 20)) {
                this.restart();
            }
        }
        return this.population.getBest();
    }

    /**
     * Evolves the population to the next generation
     */
    private void evolvePopulation() {
        Population newPopulation = new Population();
        this.addElites(newPopulation);
        this.addDescendants(newPopulation);
        this.backupPopulation = this.population;
        this.population = newPopulation;
        this.generation += 1;
    }

    /**
     * Adds the elites to the new population
     * @param newPopulation the population to add the elites to
     */
    private void addElites(Population newPopulation) {
        newPopulation.add(this.population.get(this.numberElites));
    }

    /**
     * Adds the descendants of the current population to a new population
     * @param newPopulation the population to add the descendants to
     */
    private void addDescendants(Population newPopulation) {
        int counter = this.numberElites;
        for (Individual sculpture : this.backupPopulation) {
            this.crossover.setSculpture(sculpture);
            while (this.crossover.needsParent()) this.crossover.addParent(this.selection.select(this.population));
            newPopulation.add(this.mutation.mutate(this.crossover.cross()));
            if (++counter == this.populationSize) break;
        }
    }
}
