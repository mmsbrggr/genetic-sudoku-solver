/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package nature;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a gene
 * @param <T> the type of the dna
 */
public final class Gene<T> implements Iterable<T> {

    private ArrayList<T> representation;

    /**
     * Main constructor
     */
    public Gene() {
        this.representation = new ArrayList<>();
    }

    /**
     * copy constructor
     * @param other the gene to copy
     */
    public Gene(Gene<T> other) {
        this.representation = new ArrayList<>(other.representation);
    }

    /**
     * Adds a dna to the back of the gene
     * @param dna the dna to add
     */
    public void add(T dna) {
        this.representation.add(dna);
    }

    /**
     * Resets a dna at a given position with a given dna
     * @param pos The position at which the dna gets overwritten
     * @param dna The dna to override with
     */
    public void set(int pos, T dna) {
        this.representation.set(pos, dna);
    }

    /**
     * @param pos the position to return the dna from
     * @return the dna at a given position
     */
    public T get(int pos) {
        return this.representation.get(pos);
    }

    /**
     * @return the genes size
     */
    public int size() {
        return this.representation.size();
    }

    @Override
    public Iterator<T> iterator() {
        return this.representation.iterator();
    }
}
