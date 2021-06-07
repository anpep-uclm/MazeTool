package io.github.anpep.MazeTool.model;

import java.util.Objects;

public class BoxedCell {
    private Cell m_cell;
    private boolean m_is_visited;
    private BoxedCell m_antecessor;

    public BoxedCell(Cell cell) {
        m_cell = cell;
    }

    public Cell unwrap() {
        return m_cell;
    }

    public BoxedCell getAntecessor() {
        return m_antecessor;
    }

    public void setAntecessor(BoxedCell antecessor) {
        m_antecessor = antecessor;
    }

    public boolean isVisited() {
        return m_is_visited;
    }

    public void visit() {
        m_is_visited = true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        final BoxedCell boxedCell = (BoxedCell) object;
        return m_cell.equals(boxedCell.m_cell);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_cell);
    }

    @Override
    public String toString() {
        return "BoxedCell{" +
                "m_cell=" + m_cell +
                '}';
    }
}
