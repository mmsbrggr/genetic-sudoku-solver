/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

import io.GridReader;
import io.GridWriter;
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
        GridWriter.printHeadline();
        SudokuGrid grid = GridReader.read();
        GridWriter.printSpace();
        if (grid != null) {
            GridWriter.printIntput(grid);

            Registry.getInstance().set("elitism-rate", 0.002);
            Registry.getInstance().set("mutation-rate", 0.1);
            Registry.getInstance().set("population-size", 1000);
            Registry.getInstance().set("populations-before-restart", 20);
            Registry.getInstance().set("grid", grid);
            Registry.getInstance().set("problem", new Problem(grid));
            Registry.getInstance().set("crossover", new UniformCrossover(2));
            Registry.getInstance().set("selection", new RouletteWheelSelection());
            Registry.getInstance().set("mutation", new SwapRowMutation());

            GridWriter.printPresolve(grid, ((Problem) Registry.getInstance().get("problem")).getVariableFields().length);

            World world = new World();
            Individual solution = world.findSolution();
            GridWriter.printSolution(solution);
        }
    }
}
