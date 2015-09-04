/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package problem.mutation;

import problem.Problem;
import problem.habitat.Individual;
import problem.registry.Registry;

/**
 * Mutates an individual by swapping to numbers from either rows
 */
public class SwapRowMutation implements Mutation {

    private Problem problem;

    public SwapRowMutation() {
        this.problem = (Problem) Registry.getInstance().get("problem");
    }

    @Override
    public Individual mutate(Individual individual) {
        if (Math.random() < (double) Registry.getInstance().get("mutation-rate")) {
            return individual;
        }
        for (int i = 0; i < (int) (Math.random() * individual.getSideLength()); i++) {
            int row = (int) (Math.random() * individual.getSideLength());
            Integer[] variableFields = this.problem.getVariableFieldsForRow(row);
            int field1 = variableFields[(int) (Math.random() * variableFields.length)];
            for (int field : variableFields) {
                if (individual.read(field1) != individual.read(field) &&
                        this.problem.numberIsValidForIndex(field1, individual.read(field)) &&
                        this.problem.numberIsValidForIndex(field, individual.read(field1))) {
                    int tmp = individual.read(field);
                    individual.write(field1, individual.read(field));
                    individual.write(field, tmp);
                }
            }
        }
        return individual;
    }
}
