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
    private int m_neighbor_count;
    private Cell[][] m_cells;

    private BoxedCell m_initial_cell;
    private BoxedCell[][] m_generation_cells;
    private ArrayList<BoxedCell> m_generation_cells_remaining;
    private Stack<BoxedCell> m_previous_cells;

    public Maze(int rows, int cols) {
        m_rows = rows;
        m_cols = cols;
        m_cells = new Cell[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                m_cells[i][j] = new Cell(i, j);
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

    public Cell getCell(int i, int j) {
        return m_cells[i][j];
    }

    public void setCell(int i, int j, Cell cell) {
        m_cells[i][j] = cell;
    }
}
