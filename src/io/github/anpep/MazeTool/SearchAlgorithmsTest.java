package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.algorithm.*;
import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.SearchProblem;
import io.github.anpep.MazeTool.model.SearchTreeNode;
import io.github.anpep.MazeTool.util.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class SearchAlgorithmsTest {
    private final static ISearchAlgorithm[] ALGORITHMS = new ISearchAlgorithm[]{
            new AStarSearchAlgorithm(),
            new BreadthSearchAlgorithm(),
            new DepthSearchAlgorithm(),
            new GreedySearchAlgorithm(),
            new UniformCostSearchAlgorithm()
    };

    public static void main(String[] args) {
        /* Parse/render/export sample mazes */
        final File folder = new File("data/sample_problems");
        for (final File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".json") && !name.endsWith("_maze.json")))) {
            try {
                final SearchProblem problem = SearchProblemReader.readFromFile(file.getAbsolutePath());

                for (ISearchAlgorithm algorithm : ALGORITHMS) {
                    final ArrayList<SearchTreeNode> solution = SearchProblemSolver.solve(problem, algorithm);
                    final String fileName = String.format("data/out/sol_%dx%d_%s.txt", problem.getMaze().getRows(), problem.getMaze().getCols(), algorithm);
                    SearchProblemSolver.writeToFile(solution, fileName);
                    MazeRenderer.render(problem.getMaze(), String.format("data/out/sol_%dx%d_%s.png", problem.getMaze().getRows(), problem.getMaze().getCols(), algorithm), solution);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
