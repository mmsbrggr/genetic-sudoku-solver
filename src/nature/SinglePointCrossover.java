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
 * Crosses two organisms at a single random point
 */
public class SinglePointCrossover implements Crossover {

    @Override
    public void cross(Organism sculpture, Organism[] organisms) {
        int point = (int) (Math.random() * sculpture.getGene().size());
        Organism from = organisms[0];
        for (int i = 0; i < sculpture.getGene().size(); i++) {
            sculpture.setDna(i, from.getDna(i));
            if (i == point) {
                from = organisms[1];
            }
        }
    }
}
