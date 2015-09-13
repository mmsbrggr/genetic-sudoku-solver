/*
 * This file is part of the genetic-sudoku-solver.
 *
 * (c) Marcel Moosbrugger
 *
 * This source file is subject to the MIT license that is bundled
 * with this source code in the file LICENSE.
 */

package io;

import problem.habitat.Individual;
import sudoku.SudokuGrid;

/**
 * Class writes to the standard-output
 */
public class GridWriter {

    public static void printHeadline() {
        String headline = "   _____                 _   _         _____           _       _          \n" +
                "  / ____|               | | (_)       / ____|         | |     | |         \n" +
                " | |  __  ___ _ __   ___| |_ _  ___  | (___  _   _  __| | ___ | | ___   _ \n" +
                " | | |_ |/ _ \\ '_ \\ / _ \\ __| |/ __|  \\___ \\| | | |/ _` |/ _ \\| |/ / | | |\n" +
                " | |__| |  __/ | | |  __/ |_| | (__   ____) | |_| | (_| | (_) |   <| |_| |\n" +
                "  \\_____|\\___|_| |_|\\___|\\__|_|\\___| |_____/ \\__,_|\\__,_|\\___/|_|\\_\\\\__,_|\n";
        System.out.println(headline);
        System.out.println("  solver by Marcel Moosbrugger");
        System.out.println();
    }

    public static void printLabel(String label) {
        System.out.print(label + ": ");
    }

    public static void printSpace() {
        System.out.println();
        System.out.println();
    }

    public static void printBiglabel(String label) {
        GridWriter.printLabel(label);
        System.out.println();
    }

    public static void printIntput(SudokuGrid grid) {
        GridWriter.printHeadline("Starting to solve the following grid:");
        System.out.println(grid);
        GridWriter.printSpace();
    }

    public static void printPresolve(SudokuGrid grid, int emptyFields) {
        GridWriter.printHeadline("After presolving:");
        System.out.println(grid);
        System.out.println("Empty fields: " + emptyFields);
        GridWriter.printSpace();
    }

    public static void printElite(Individual elite, int epoch, int generation, double avgFitness, double avgConflicts) {
        GridWriter.printHeadline("Current best individual:");
        System.out.println(elite);
        System.out.println("Conflicts: " + elite.getConflicts());
        System.out.println("Avg conflicts in population: " + avgConflicts);
        System.out.println("Fitness: " + elite.getFitness());
        System.out.println("Avg fitness in population: " + avgFitness);
        System.out.println("Epoch: " + epoch);
        System.out.println("Generation: " + generation);
        GridWriter.printSpace();
    }

    public static void printSolution(SudokuGrid grid) {
        GridWriter.printHeadline("Solution to problem:");
        System.out.println(grid);
        System.out.println("Conflicts: " + grid.getConflicts());
        GridWriter.printSpace();
    }

    private static void printHeadline(String headline) {
        System.out.println(headline);
        System.out.println(new String(new char[headline.length()]).replace("\0", "-"));
    }
}
