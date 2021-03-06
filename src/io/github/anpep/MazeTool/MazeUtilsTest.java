package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.util.MazeReader;
import io.github.anpep.MazeTool.util.MazeRenderer;
import io.github.anpep.MazeTool.util.MazeWriter;

import java.io.File;
import java.util.Objects;

public class MazeUtilsTest {
    public static void main(String[] args) {
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
    }
}
