package io.github.anpep.MazeTool.model;

import java.util.Objects;

public class Cell {
    private int m_row;
    private int m_col;
    private int m_value;
    private boolean[] m_neighbors;

    public Cell(int row, int col) {
        m_row = row;
        m_col = col;
        m_value = 0;
        m_neighbors = new boolean[] { true, true, true, true };
    }

    public Cell(int row, int col, int neighbor_count, int value, boolean[] neighbors) {
        this(row, col);
        m_value = value;
        m_neighbors = neighbors;
    }

    public void setNeighbor(int neighbor_index, boolean present) {
        m_neighbors[neighbor_index] = present;
    }

    public void setValue(int value) {
        m_value = value;
    }

    public int getRow() { return m_row; }
    public int getCol() { return m_col; }
    public int getValue() { return m_value; }
    public boolean[] getNeighbors() { return m_neighbors; }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final Cell cell = (Cell) object;
        return m_row == cell.m_row && m_col == cell.m_col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_row, m_col);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "m_row=" + m_row +
                ", m_col=" + m_col +
                ", m_value=" + m_value +
                '}';
    }
}
