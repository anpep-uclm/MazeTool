package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.util.MazeFactory;
import io.github.anpep.MazeTool.util.MazeRenderer;
import io.github.anpep.MazeTool.util.MazeWriter;

public class MazeFactoryTest {
    public static void main(String[] args) {
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
