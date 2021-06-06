package io.github.anpep.MazeTool.util;

import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeRenderer {
    public final static int CELL_SIZE = 16;
    public final static int MAZE_PADDING = 8;

    public static void render(Maze maze, String path) throws IOException {
        final BufferedImage img = new BufferedImage(MAZE_PADDING + maze.getCols() * CELL_SIZE, MAZE_PADDING + maze.getRows() * CELL_SIZE, BufferedImage.TYPE_INT_RGB);
        final Graphics gfx = img.createGraphics();

        gfx.setColor(Color.WHITE);
        gfx.fillRect(0, 0, img.getWidth(), img.getHeight());
        gfx.setColor(Color.BLACK);

        for (int col = 0, x = MAZE_PADDING / 2; col < maze.getCols(); col++, x += CELL_SIZE) {
            for (int row = 0, y = MAZE_PADDING / 2; row < maze.getRows(); row++, y += CELL_SIZE) {
                final Cell cell = maze.getCell(row, col);
                final int value = cell.getValue();
                final boolean[] neighbors = cell.getNeighbors();

                if (!neighbors[0]) /* North */
                    gfx.drawLine(x, y, x + CELL_SIZE, y);
                if (!neighbors[1]) /* East */
                    gfx.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
                if (!neighbors[2]) /* West */
                    gfx.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);
                if (!neighbors[3]) /* South */
                    gfx.drawLine(x, y, x, y + CELL_SIZE);

                if (value != 0)
                    gfx.drawString(Integer.toString(value), x + 3, y + CELL_SIZE - 3);
            }
        }

        gfx.dispose();
        ImageIO.write(img, "png", new File(path));
    }
}
