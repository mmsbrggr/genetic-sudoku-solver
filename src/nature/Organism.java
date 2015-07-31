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
 * Represents an organism. Can be used in a population of
 * a genetic algorithm,
 * @param <T>
 */
public interface Organism<T> extends Comparable<Organism> {

    /**
     * Returnes the gene of the organism
     * @return the organism's gene
     */
    public Gene<T> getGene();

    /**
     * Resets a a dna at a given position in the gene
     * @param pos The position at which the dna gets overwritten
     * @param dna The dna to override with
     */
    public void setDna(int pos, T dna);

    /**
     * @param pos the position to return the dna from
     * @return the dna at a given position
     */
    public T getDna(int pos);

    /**
     * Mutates the organism
     */
    public void mutate();

    /**
     * @return The organism's fitness. The higher the fitness, the fitter the organism
     */
    public int getFitness();
}
