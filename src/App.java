/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

import problem.Problem;
import problem.World;
import problem.crossover.UniformCrossover;
import problem.habitat.Individual;
import problem.mutation.SwapRowMutation;
import problem.registry.Registry;
import problem.selection.RouletteWheelSelection;
import sudoku.*;

public class App {

    public static void main(String[] args) {
        SudokuGrid grid = new SudokuGrid(3);
        grid.write(6, 0, 3);
        grid.write(7, 0, 8);
        grid.write(3, 1, 3);
        grid.write(4, 1, 4);
        grid.write(5, 1, 6);
        grid.write(1, 2, 5);
        grid.write(2, 2, 1);

        grid.write(0, 3, 2);
        grid.write(3, 3, 8);
        grid.write(5, 3, 3);
        grid.write(8, 3, 6);
        grid.write(1, 4, 6);
        grid.write(6, 4, 5);
        grid.write(8, 4, 9);
        grid.write(1, 5, 1);
        grid.write(2, 5, 5);
        grid.write(3, 5, 2);

        grid.write(4, 6, 3);
        grid.write(6, 6, 6);
        grid.write(7, 6, 7);
        grid.write(2, 7, 2);
        grid.write(7, 7, 5);
        grid.write(0, 8, 6);
        grid.write(2, 8, 4);
        grid.write(4, 8, 7);
        grid.write(5, 8, 2);

        Registry.getInstance().set("elitism-rate", 0.002);
        Registry.getInstance().set("mutation-rate", 0.3);
        Registry.getInstance().set("population-size", 800);
        Registry.getInstance().set("grid", grid);
        Registry.getInstance().set("problem", new Problem(grid));
        Registry.getInstance().set("crossover", new UniformCrossover(2));
        Registry.getInstance().set("selection", new RouletteWheelSelection());
        Registry.getInstance().set("mutation", new SwapRowMutation());

        World world = new World();

        Individual solution = world.findSolution();
        System.out.println(solution);
        System.out.println(solution.getConflicts());
        System.out.println(solution.getFitness());
    }
}
