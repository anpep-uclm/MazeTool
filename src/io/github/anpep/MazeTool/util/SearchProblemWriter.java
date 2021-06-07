package io.github.anpep.MazeTool.util;

import com.google.gson.stream.JsonWriter;
import io.github.anpep.MazeTool.model.SearchProblem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SearchProblemWriter {
    public static void writeToFile(SearchProblem searchProblem, String path) throws IOException {
        assert path.endsWith(".json");

        MazeWriter.writeToFile(searchProblem.getMaze(), path.replace(".json", "_maze.json"));

        final JsonWriter writer = new JsonWriter(new FileWriter(path));

        writer.beginObject();

        writer.name("INITIAL").value(searchProblem.getInitialState().toString());
        writer.name("OBJETIVE").value(searchProblem.getGoalState().toString());
        writer.name("MAZE").value(new File(path).getName().replace(".json", "_maze.json"));

        writer.endObject();
        writer.close();
    }
}
