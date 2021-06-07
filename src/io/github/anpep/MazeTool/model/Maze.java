package io.github.anpep.MazeTool.model;

import java.util.ArrayList;
import java.util.Stack;

public class Maze {
    public static final int MAX_NEIGHBORS = 4;
    public static final char[] MOVE_NAMES = new char[]{'N', 'E', 'S', 'O'};
    public static final int[][] LEGAL_MOVES = new int[][]{
            new int[]{-1, 0}, new int[]{0, 1},
            new int[]{1, 0}, new int[]{0, -1}
    };

    private int m_rows;
    private int m_cols;
    private Cell[][] m_cells;

    public Maze(int rows, int cols) {
        m_rows = rows;
        m_cols = cols;
        m_cells = new Cell[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                m_cells[row][col] = new Cell(row, col);
    }

    public int getRows() {
        return m_rows;
    }

    public int getCols() {
        return m_cols;
    }

    public Cell[][] getCells() {
        return m_cells;
    }

    public Cell getCell(int row, int col) {
        return m_cells[row][col];
    }
}
