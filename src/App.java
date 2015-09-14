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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class parses the input parameters and starts the algorithm
 */
public final class App {

    /* default configurations */
    private static double elitismRate = 0.002;
    private static double mutationRate = 0.1;
    private static int populationSize = 1000;
    private static int populationsBeforeRestart = 20;
    private static int numberParents = 2;
    private static int numberLeftEmptyFields = 0;

    /**
     * Main entry point
     * @param args array of the program's arguments (for details see README.md)
     */
    public static void main(String[] args) {
        GridWriter.printTitle();
        App.parseArguments(new ArrayList<>(Arrays.asList(args)));
        GridWriter.printSpace();
        SudokuGrid grid = GridReader.read();
        GridWriter.printSpace();

        if (grid != null) {
            GridWriter.printIntput(grid);

            Registry.getInstance().set("elitism-rate", App.elitismRate);
            Registry.getInstance().set("mutation-rate", App.mutationRate);
            Registry.getInstance().set("population-size", App.populationSize);
            Registry.getInstance().set("presolver-leave-empty", App.numberLeftEmptyFields);
            Registry.getInstance().set("populations-before-restart", App.populationsBeforeRestart);
            Registry.getInstance().set("grid", grid);
            Registry.getInstance().set("problem", new Problem(grid));
            Registry.getInstance().set("crossover", new UniformCrossover(App.numberParents));
            Registry.getInstance().set("selection", new RouletteWheelSelection());
            Registry.getInstance().set("mutation", new SwapRowMutation());

            GridWriter.printPresolve(grid, ((Problem) Registry.getInstance().get("problem")).getVariableFields().length);

            World world = new World();
            Individual solution = world.findSolution();

            GridWriter.printSolution(solution);
        }
    }

    /**
     * Parses an array-list of arguments and sets the variables for the algorithm
     * @param args the array-list of arguments to parse
     */
    private static void parseArguments(ArrayList<String> args) {
        if (args.indexOf("-e") > -1) {
            App.elitismRate = Double.parseDouble(args.get(args.indexOf("-e") + 1));
            GridWriter.printParameterChange("Elitism-rate (-e)", App.elitismRate);
        }
        if (args.indexOf("-m") > -1) {
            App.mutationRate = Double.parseDouble(args.get(args.indexOf("-m") + 1));
            GridWriter.printParameterChange("Mutation-rate (-m)", App.mutationRate);
        }
        if (args.indexOf("-p") > -1) {
            App.populationSize = Integer.parseInt(args.get(args.indexOf("-p") + 1));
            GridWriter.printParameterChange("Population size (-p)", App.populationSize);
        }
        if (args.indexOf("-b") > -1) {
            App.populationsBeforeRestart = Integer.parseInt(args.get(args.indexOf("-b") + 1));
            GridWriter.printParameterChange("Populations before restart (-b)", App.populationsBeforeRestart);
        }
        if (args.indexOf("-n") > -1) {
            App.numberParents = Integer.parseInt(args.get(args.indexOf("-n") + 1));
            GridWriter.printParameterChange("Number of parents (-n)", App.numberParents);
        }
        if (args.indexOf("-l") > -1) {
            App.numberLeftEmptyFields = Integer.parseInt(args.get(args.indexOf("-l") + 1));
            GridWriter.printParameterChange("Number of fields left empty by the presolver (-l)", App.numberLeftEmptyFields);
        }
    }
}
