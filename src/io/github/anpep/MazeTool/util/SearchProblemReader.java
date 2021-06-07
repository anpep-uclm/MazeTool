package io.github.anpep.MazeTool.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.SearchProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Paths;

public class SearchProblemReader {
    public static SearchProblem readFromFile(String path) throws FileNotFoundException {
        final JsonReader reader = new JsonReader(new FileReader(path));
        final JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

        final String initialStateID = root.get("INITIAL").getAsString(),
                goalStateID = root.get("OBJETIVE").getAsString(),
                associatedMazeFileName = root.get("MAZE").getAsString();

        final String[] splitInitialStateID = initialStateID.replaceAll("[^\\d,]", "").split(",", 2),
                splitGoalStateID = goalStateID.replaceAll("[^\\d,]", "").split(",", 2);
        final int initialStateRow = Integer.parseInt(splitInitialStateID[0]),
                initialStateCol = Integer.parseInt(splitInitialStateID[1]);
        final int goalStateRow = Integer.parseInt(splitGoalStateID[0]),
                goalStateCol = Integer.parseInt(splitGoalStateID[1]);

        final String absoluteMazePath = Paths.get(new File(path).getParentFile().getAbsolutePath(), associatedMazeFileName).toFile().getAbsolutePath();

        final Maze associatedMaze = MazeReader.readFromFile(absoluteMazePath);
        return new SearchProblem(associatedMaze, initialStateRow, initialStateCol, goalStateRow, goalStateCol);
    }
}
