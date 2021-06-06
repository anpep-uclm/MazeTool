package io.github.anpep.MazeTool.util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class MazeReader {
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
    }
}
