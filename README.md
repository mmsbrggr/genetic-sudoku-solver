# Genetic sudoku solver

GSS is an approach of solving sudokus with a genetic algorithm. The project is more of educational purpose, as Java doesn't allow the most performant implementation, but greatly illustrates the architecture of such an algorithm. Nevertheless GSS solves most sudokus in a decent time. Additionally to standard 9x9-sudokus, the program is able to solve bigger sudokus like e.g 16x16 or 25x25.

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

## Parameters
You can tweak the behaviour of the GSS with a few parameters. The parameters get passed to the program on startup.
```
java -jar genetic-sudoku-solver.jar -e 0.001
```
This would result in the elitism-rate changed to 0.1 %.

Parameter | Default value | Name | Description
---|---|---|---
-e | 0.002 | Rate of elitism | Determines how many of the fittest individuals get automatically added to the next generation.
-m | 0.1 | Rate of mutation | The probability of an individual to mutate.
-p | 1000 | Population size | The number of individuals in a single population.
-b | 20 | Idle generations before restart | If after this many generation no progress was made, the algorithm gets restarted.
-n | 2 | Number of parents | The number of individuals (parents) from which a new individual (child) is derived.
-l | 0 | Number of fields left empty by the presolver | This many fields get left empty by the presolving algorithm, albeit it could now the answer.
