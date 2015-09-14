# Genetic sudoku solver

GSS is an approach of solving sudokus with a genetic algorithm. The project is more of educational purpose, as Java doesn't allow the most performant implementation of such an algorithm, but greatly illustrates the architecture of such an algorithm. Nevertheless GSS solves most sudokus in a decent time. Additionally to standard 9x9-sudokus, the program is able to solve bigger sudokus like e.g 16x16 or 25x25.

## Download
You can either clone this repository or download the jar-file by clicking [here](https://github.com/marcelmoosbrugger/genetic-sudoku-solver/raw/master/genetic-sudoku-solver.jar).

## Usage
Call the following command in your terminal to start the program.
```
java -jar genetic-sudoku-solver.jar
```
After that GSS asks you the side length of a block of the sudoku you want be solved. Here you type '3' for a standard sudoku. For bigger sudokus you put e.g. '4' for a 16x16 puzzle or '5' for a 25x25 puzzle.
```
>> Size of a block ('3' for standard sudoku): 3
```
Succeeding GSS requests the sudoku-grid to solve
```
>> Sudoku ('0' for empty fields):
0 0 0 0 0 0 3 8 0
0 0 0 3 4 6 0 0 0
0 5 1 0 0 0 0 0 0
2 0 0 8 0 3 0 0 6
0 6 0 0 0 0 5 0 9
0 1 5 2 0 0 0 0 0
0 0 0 0 3 0 6 7 0
0 0 2 0 0 0 0 5 0
6 0 4 0 7 2 0 0 0
```
For a 9x9-sudoku you could alternitavely omit the spaces between the numbers.
```
>> Sudoku ('0' for empty fields):
000000380
000346000
051000000
200803006
060000509
015200000
000030670
002000050
604072000
```
As soon as you end your input with the EOF-character the algorithm starts solving your sudoku.
