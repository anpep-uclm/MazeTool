package io.github.anpep.MazeTool.util;

import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.Move;
import io.github.anpep.MazeTool.model.SearchTreeNode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MazeRenderer {
    public final static int CELL_SIZE = 16;
    public final static int MAZE_PADDING = 8;

    public static final HashMap<Integer, Integer> CELL_SHADES = new HashMap<Integer, Integer>() {{
        put(Cell.EARTH, 0xE3D5C4);
        put(Cell.GRASS, 0xC2FFAD);
        put(Cell.WATER, 0xADEBFF);
        put(0xff, 0xFF0000);
    }};

    private static boolean inSolution(int row, int col, ArrayList<SearchTreeNode> solution) {
        for (SearchTreeNode node : solution) {
            if (node.getState().getRow() == row && node.getState().getCol() == col)
                return true;
        }
        return false;
    }

    public static void render(Maze maze, String path) throws IOException {
        render(maze, path, null);
    }

    public static void render(Maze maze, String path, ArrayList<SearchTreeNode> solution) throws IOException {
        final BufferedImage img = new BufferedImage(MAZE_PADDING + maze.getCols() * CELL_SIZE, MAZE_PADDING + maze.getRows() * CELL_SIZE, BufferedImage.TYPE_INT_RGB);
        final Graphics gfx = img.createGraphics();

        gfx.setColor(Color.WHITE);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        gfx.setColor(Color.BLACK);

        for (int row = 0, y = MAZE_PADDING / 2; row < maze.getRows(); row++, y += CELL_SIZE) {
            for (int col = 0, x = MAZE_PADDING / 2; col < maze.getCols(); col++, x += CELL_SIZE) {
                final Cell cell = maze.getCell(row, col);
                final int value = cell.getValue();
                final boolean[] neighbors = cell.getNeighbors();

                if (solution != null && inSolution(row, col, solution))
                    gfx.setColor(Color.RED);
                else
                    gfx.setColor(new Color(CELL_SHADES.getOrDefault(value, 0xFFFFFF)));
                gfx.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                gfx.setColor(Color.BLACK);

                if (!neighbors[Move.NORTH.toOrientationIndex()]) /* North */
                    gfx.drawLine(x, y, x + CELL_SIZE, y);
                if (!neighbors[Move.EAST.toOrientationIndex()]) /* East */
                    gfx.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
                if (!neighbors[Move.SOUTH.toOrientationIndex()]) /* South */
                    gfx.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);
                if (!neighbors[Move.WEST.toOrientationIndex()]) /* West */
                    gfx.drawLine(x, y, x, y + CELL_SIZE);

                /*if (value > 3)
                    gfx.drawString(Integer.toString(value), x + 3, y + CELL_SIZE - 3);*/
            }
        }

        gfx.dispose();
        ImageIO.write(img, "png", new File(path));
    }
}
