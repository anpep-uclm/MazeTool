# Intelligent Systems
> **Student:** Ángel Pérez Porras `<angel.perez7@alu.uclm.es>`  
> **Group:** A1.1

## 1. Problem Introduction
The problem tackled by this solution consists in parsing, generating, rendering, and solving mazes using a command-line
application written in the Java programming language.

Mazes are represented as a grid and supplied in the JSON format:
```json5
{
  "rows": 10, // Number of rows in the grid.
  "cols": 10, // Number of columns in the grid.
  "max_n": 4, // Maximum number of neighbors (constant fixed to 4)
  "mov": [
    // Legal cell movements, represented as a set of (dx, dy) tuples
    // such that adding up (dx, dy) to the (x, y) coordinates of the
    // cell results in the destination cell coordinates.
    [-1, 0 ], // North (N)
    [ 0, 1 ], // East (E)
    [ 1, 0 ], // South (S)
    [ 0,-1 ]  // West (O)
  ],
  
  // Order of the legal movements (N: north, E: east, S: south, O: west)
  "id_mov": ["N","E","S","O"],
  
  // A mapping of every cell in the maze.
  "cells": {
    // Every cell has an associated string ID in the form of "(x, y)",
    // with `x` and `y` both starting in 0.
    "(0, 0)": {
      // The cell value represents the algorithmic cost of being traversed
      // by a search algorithm. In graphical renderings, it is depicted as
      // a different shade for the cell background:
      //   - 0: Asphalt (clear background)
      //   - 1: Earth (brownish background)
      //   - 2: Grass (greenish background)
      //   - 3: Water (blueish background)
      "value": 0,
      
      // Neighbors represent the ability to cross the cell by tracing a path
      // towards its orientation, following the movement border defined above.
      // In this case, neighbors (N,E,S,O) := (F,T,T,F) imply that this cell
      // has walls in the east and south sides.
      "neighbors": [
        false,
        true,
        true,
        false
      ]
    }
  }
}
```

The `MazeReader` and `MazeWriter` class are responsible for loading and saving mazes as JSON files. Their basic usage is
as follows:
#### Reading a maze
```java
final Maze maze = MazeReader.readFromFile("puzzle_10x10.json");
```

#### Writing a maze
```java
final Maze maze = /* ... */;
MazeWriter.writeToFile(maze, "puzzle_10x10.json");
```

#### Rendering a maze
```java
final Maze maze = /* ... */;
MazeRenderer.render(maze, "puzzle_10x10.png");
```

The `MazeRenderer` class is responsible for rendering mazes, as seen in the snippet above. The class will iterate over
every individual cell and draw its background and cell borders taking into account the neighbors, and save the resulting
image in the PNG format at the supplied path.

<center>
<img src="https://tenshi.industries/~angel/mazetool/puzzle_10x10.png" />
<img src="https://tenshi.industries/~angel/mazetool/sol_5x5_a_star.png" />
</center>

#### Creating a maze
```java
final Maze maze = MazeFactory.createMaze(10, 10);
```

The `MazeFactory` class is responsible for creating and generating mazes, using the [Wilson's Algorithm](https://people.cs.ksu.edu//~ashley78/wiki.ashleycoleman.me/index.php/Wilson's_Algorithm.html).

### 1.1. State Definition
A state is defined by every basic property present in a maze cell. In fact, the `State` class that represents this object
directly inherits the `Cell` class that is used to represent a maze cell. In addition to this, every state is uniquely
identified by a tuple `(row, col)` that contains the zero-based indices of the row and column where this cell lies within
the maze.

## 2. Search Problems
A search problem, like every maze, is given as a JSON file consisting of three properties, with the following format:
```json5
{
  "INITIAL": "(0, 0)",      // Initial state identifier.
  "OBJETIVE": "(4, 4)",     // "Objetive" (sic, i.e. "Goal") state identifier.
  "MAZE": "puzzle_5x5.json" // File path to the maze associated to this problem.
}
```
The `SearchProblemReader` and `SearchProblemWriter` classes are responsible for parsing and exporting these JSON files
that define search problems represented by the `SearchProblem` class.

##### Loading Search Problems
```java
final SearchProblem problem = SearchProblemReader.readFromFile("problem_5x5.json");
final Maze maze = problem.getMaze(); // puzzle_5x5.json, as per the JSON definition above
```

##### Exporting Search Problems
```java
final SearchProblem problem = /* ... */;
SearchProblemWriter.writeToFile(problem, "problem_5x5.json");
```

### 2.2. _Node_ Software Artifact
The `SearchTreeNode` class represents a _node_ in the search tree. It has the following properties:
- **ID:** A unique, numeric identifier for this tree node.
- **State:** Access to the state space for that node.
- **Value:** Value of the node according to the applied search algorithm (more on this later on in this document).
- **Depth:** Depth of the node in the search tree.
- **Cost:** An accumulative quantity that refers to the algorithmic cost incurred in traversing the associated cell.
- **Heuristic:** A double-precision floating-point value that is automatically caculated upon node addition that is defined
by search algorithms.
- **Action/Move:** Action performed in this state. Possible values are:
    - `NORTH` (move to the north): `(-1, 0)`
    - `EAST` (move to the east): `(0, +1)`
    - `SOUTH` (move to the south): `(1, 0)`
    - `WEST` (move to the west): `(0, -1)`
    - `NOT_SET` (no action done, e.g. in the root node)
    
Tree nodes will be placed on a priority queue (the *frontier*), so they need to have some means of sorting them.
To this end, `SearchTreeNode` objects implement the `Comparable<SearchTreeNode>` interface, which means they can be compared
against another node. Following the criteria specified by the problem statement, the order of sorting is as follows:
1. Value
2. State Row
3. State Column
4. Node ID

This comparison is performed by calculating a _score_ that computes a _hash function_ that gives the weight stated above
to each of these properties in order to obtain a value that can be compared numerically (see `SearchTreeNode.getSortingScore()`).

Tree nodes can be created by using the `SearchTreeNodeFactory` facility. This class is intended to be used **only** for
performing the tests that are mandated by the problem statement, as discussed later on.

### 2.3. _Frontier_ Software Artifact
The _frontier_ has to be a collection of node artifacts following the sorting order stated in _2.1_.
It must implement the following operations while maintatining the original sorting order:
- **Push** (insert a new element): `Frontier.add(node: SearchTreeNode): void`
- **Pop** (remove the last element): `Frontier.remove(): SearchTreeNode`

With these considerations, we simply **inherit** the `PriorityQueue<SearchTreeNode>` type from the Java runtime, which
yields the exact desired outcome with the desired behavior.

### 2.4. Search Algorithms
The following search algorithms must be implemented, as per the original problem statement:
- **A*** (`a_star`) with a heuristic value defined as `node.heuristic + node.cost`.
- **Breadth-First (BFS)** (`breadth`) with a heuristic value defined as `node.depth`.
- **Depth-First (DFS)** (`depth`) with a heuristic value defined as `1 / (1 + node.depth)`.
- **Greedy** (`greedy`) with a heuristic value defined as `node.heuristic`.
- **Uniform Cost** (`ucost`) with a heuristic value defined as `node.cost`.

Search algorithms are represented by classes implementing the `ISearchAlgorithm` interface, which requires two methods
to be implemented:
1. `getHeuristic(node: SearchTreeNode): double` (defining heuristic)
2. `toString(): String` (path-friendly display name for the algorithm)

