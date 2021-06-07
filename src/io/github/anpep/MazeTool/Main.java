package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.SearchProblem;
import io.github.anpep.MazeTool.util.*;

import java.io.File;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        /* Parse sample search problems */
        final File folder = new File("data/tree_search");
        for (final File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".json") && !name.endsWith("_maze.json")))) {
            try {
                SearchProblem problem = SearchProblemReader.readFromFile(file.getAbsolutePath());
                System.out.println(problem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main2(String[] args) {
        /* Parse/render/export sample mazes */
        final File folder = new File("data/sample_mazes");
        for (final File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".json")))) {
            try {
                Maze maze = MazeReader.readFromFile(file.getAbsolutePath());
                MazeRenderer.render(maze, "data/out/" + file.getName().replace(".json", ".png"));
                MazeWriter.writeToFile(maze, "data/out/" + file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /* Create mazes using Wilson's algorithm */
        Maze maze = MazeFactory.createMaze(10, 10);
        try {
            MazeRenderer.render(maze, "data/out/wilson.png");
            MazeWriter.writeToFile(maze, "data/out/wilson.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
