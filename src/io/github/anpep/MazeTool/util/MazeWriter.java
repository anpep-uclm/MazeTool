package io.github.anpep.MazeTool.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;
import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;

import java.io.FileWriter;
import java.io.IOException;

public class MazeWriter {
    public static void writeToFile(Maze maze, String path) throws IOException {
        final JsonWriter writer = new JsonWriter(new FileWriter(path));
        writer.setIndent("    ");

        writer.beginObject();

        writer.name("rows").value(maze.getRows());
        writer.name("cols").value(maze.getCols());
        writer.name("max_n").value(Maze.MAX_NEIGHBORS);

        writer.name("mov");
        writer.beginArray();
        for (int[] move : Maze.LEGAL_MOVES) {
            assert move.length == 2;
            writer.beginArray();
            writer.value(move[0]);
            writer.value(move[1]);
            writer.endArray();
        }
        writer.endArray();

        writer.name("id_mov");
        writer.beginArray();
        for (char moveName : Maze.MOVE_NAMES)
            writer.value(String.valueOf(moveName));
        writer.endArray();

        writer.name("cells");
        writer.beginObject();

        for (int row = 0; row < maze.getRows(); row++) {
            for (int col = 0; col < maze.getCols(); col++) {
                final Cell cell = maze.getCell(row, col);
                writer.name(String.format("(%d, %d)", row, col));
                writer.beginObject();
                writer.name("value").value(cell.getValue());

                writer.name("neighbors");
                writer.beginArray();
                for (boolean neighbor : cell.getNeighbors())
                    writer.value(neighbor);
                writer.endArray();

                writer.endObject();
            }
        }

        writer.endObject();
        writer.endObject();
        writer.close();
    }
}
