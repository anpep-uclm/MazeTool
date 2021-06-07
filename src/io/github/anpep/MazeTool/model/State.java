package io.github.anpep.MazeTool.model;

public class State extends Cell {
    public State(Cell cell) {
        super(cell.getRow(), cell.getCol());
        System.arraycopy(cell.getNeighbors(), 0, m_neighbors, 0, Maze.MAX_NEIGHBORS);
        m_value = cell.m_value;
    }

    public State(Maze maze, int row, int col) {
        this(maze.getCell(row, col));
    }

    public boolean isGoal(int row, int col) {
        return m_row == row && m_col == col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", m_row, m_col);
    }
}
