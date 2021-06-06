package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.util.MazeReader;
import io.github.anpep.MazeTool.util.MazeRenderer;
import io.github.anpep.MazeTool.util.MazeWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        File folder = new File("data/sample_mazes");

        for (File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".json")))) {
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
