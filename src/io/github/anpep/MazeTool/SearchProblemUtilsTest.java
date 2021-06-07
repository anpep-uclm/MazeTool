package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.SearchProblem;
import io.github.anpep.MazeTool.util.SearchProblemReader;
import io.github.anpep.MazeTool.util.SearchProblemWriter;

import java.io.File;
import java.util.Objects;

public class SearchProblemUtilsTest {
    public static void main(String[] args) {
        /* Parse sample search problems */
        final File folder = new File("data/tree_search");
        for (final File file : Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".json") && !name.endsWith("_maze.json")))) {
            try {
                SearchProblem problem = SearchProblemReader.readFromFile(file.getAbsolutePath());
                SearchProblemWriter.writeToFile(problem, "data/out/" + file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
