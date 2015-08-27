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

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Represents a population of individuals
 */
public class Population implements Iterable<Individual> {

    private double totalFitness;
    private TreeSet<Individual> representation;

    /**
     * Default constructor. Creates a population with random individuals
     */
    public Population() {
        this.totalFitness = 0.0;
        this.representation = new TreeSet<>();
    }

    /**
     * Fills the population with random individuals
     */
    public void createRandom() {
        for (int i = 0; i < (int) Registry.getInstance().get("population-size"); i++) {
            this.add(new Individual());
        }
    }

    /**
     * Adds an individual to the population
     * @param individual the individual to add
     */
    public void add(Individual individual) {
        this.totalFitness += individual.getFitness();
        this.representation.add(individual);
    }

    /**
     * Adds more individuals to the population
     * @param individuals the individuals to add
     */
    public void add(Individual[] individuals) {
        for (Individual individual : individuals) {
            this.add(individual);
        }
    }

    /**
     * @return the cumulated fitness of the population
     */
    public double getTotalFitness() {
        return this.totalFitness;
    }

    /**
     * @param number number of individuals to return
     * @return returns the the best individuals
     */
    public Individual[] get(int number) {
        //TODO refactor: make cleaner
        if (number > (int) Registry.getInstance().get("population-size")) {
            throw new IllegalArgumentException("Cannot return more individuals than in population.");
        }
        Individual[] elite = new Individual[number];
        int index = 0;
        for (Individual individual : this) {
            elite[index] = new Individual(individual);
            if (++index == elite.length) break;
        }
        return elite;
    }

    /**
     * @return the best individual in the population
     */
    public Individual getBest() {
        return this.representation.first();
    }

    @Override
    public Iterator<Individual> iterator() {
        return this.representation.iterator();
    }
}
