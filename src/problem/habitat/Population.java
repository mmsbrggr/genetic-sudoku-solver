/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.habitat;

import problem.registry.Registry;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Represents a population of individuals
 */
public final class Population implements Iterable<Individual> {

    private double totalFitness;
    private int totalConflicts;
    private TreeSet<Individual> representation;
    private int populationSize;

    /**
     * Default constructor. Creates a population with random individuals
     */
    public Population() {
        this.totalFitness = 0.0;
        this.totalConflicts = 0;
        this.representation = new TreeSet<>();
        this.populationSize = (int) Registry.getInstance().get("population-size");
    }

    /**
     * Copy constructor
     * @param other the population to copy
     */
    public Population(Population other) {
        this();
        this.totalFitness = other.totalFitness;
        this.totalConflicts = other.totalConflicts;
        for (Individual individual : other) {
            this.representation.add(new Individual(individual));
        }
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
        if (this.representation.size() < this.populationSize) {
            this.totalFitness += individual.getFitness();
            this.totalConflicts += individual.getConflicts();
            this.representation.add(individual);
        }
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
     * @return the average number of conflicts of grids in the population
     */
    public double getAvgConflicts() {
        return this.totalConflicts / (double) this.representation.size();
    }

    /**
     * @return the average fitness of grids in the population
     */
    public double getAvgFitness() {
        return this.totalFitness / (double) this.representation.size();
    }

    /**
     * @return true iff the population is completely filled with individuals
     */
    public boolean isFull() {
        return this.representation.size() == this.populationSize;
    }

    /**
     * @param number number of individuals to return
     * @return returns the the best individuals
     */
    public Individual[] get(int number) {
        if (number > this.populationSize) {
            throw new IllegalArgumentException("Cannot return more individuals than in population.");
        }
        Individual[] elite = new Individual[number];
        int index = 0;
        if (elite.length > 0) {
            for (Individual individual : this) {
                elite[index] = new Individual(individual);
                if (++index == elite.length) break;
            }
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
