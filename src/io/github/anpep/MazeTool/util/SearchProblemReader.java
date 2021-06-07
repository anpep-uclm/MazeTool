package io.github.anpep.MazeTool.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.SearchProblem;
import io.github.anpep.MazeTool.model.State;

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
/*
    public static Maze readFromFile(String path) throws FileNotFoundException {
        final JsonReader reader = new JsonReader(new FileReader(path));
        final JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

        final int rows = root.get("rows").getAsInt(), cols = root.get("cols").getAsInt();
        final Maze maze = new Maze(rows, cols);

        for (Map.Entry<String, JsonElement> cellEntry : root.get("cells").getAsJsonObject().entrySet()) {
            final String key = cellEntry.getKey();
            final JsonObject cellObject = cellEntry.getValue().getAsJsonObject();

            final String[] splitKey = key.replaceAll("[^\\d,]", "").split(",", 2);
            final int row = Integer.parseInt(splitKey[0]), col = Integer.parseInt(splitKey[1]);

            final Cell cell = maze.getCell(row, col);
            final JsonArray neighborArray = cellObject.get("neighbors").getAsJsonArray();

            cell.setValue(cellObject.get("value").getAsInt());
            for (int i = 0; i < 4; i++)
                cell.setNeighbor(i, neighborArray.get(i).getAsBoolean());
        }

        return maze;
    }*/
}
