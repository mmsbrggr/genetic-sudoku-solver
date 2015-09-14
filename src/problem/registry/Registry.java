/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class to store globally relevant data
 */
public final class Registry {

    private static Registry instance;

    private Map<String, Object> store;

    /**
     * Singleton
     * @returns the singleton instance
     */
    public static Registry getInstance() {
        if (Registry.instance == null) {
            Registry.instance = new Registry();
        }

        return Registry.instance;
    }

    /**
     * Default constructor
     */
    public Registry() {
        this.store = new HashMap<>();
    }

    /**
     * Sets an id-subject pair into the registry
     * @param id The id to identify the subject
     * @param subject The subject to put in
     */
    public void set(String id, Object subject) {
        if (this.store.containsKey(id)) {
            throw new IllegalArgumentException("Registry object with id \"" + id + "\" is already set");
        }
        this.store.put(id, subject);
    }

    /**
     * @param id The id of the subject to get
     * @return the subject with a given id
     */
    public Object get(String id) {
        if (!this.store.containsKey(id)) {
            throw new IllegalArgumentException("Registry doesn't contain subject with id " + id);
        }

        return this.store.get(id);
    }
}
