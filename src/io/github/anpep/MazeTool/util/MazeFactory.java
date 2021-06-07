package io.github.anpep.MazeTool.util;

import io.github.anpep.MazeTool.model.BoxedCell;
import io.github.anpep.MazeTool.model.Cell;
import io.github.anpep.MazeTool.model.Maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeFactory {
    private Maze m_maze;
    private BoxedCell[][] m_generationCells;
    private ArrayList<BoxedCell> m_generationCellsRemaining;
    private BoxedCell m_lastCell;
    private Stack<BoxedCell> m_previousCells;

    private MazeFactory(int rows, int cols) {
        m_maze = new Maze(rows, cols);
    }

    private void generate() {
        m_previousCells = new Stack<BoxedCell>();
        m_generationCells = new BoxedCell[m_maze.getRows()][m_maze.getCols()];
        m_generationCellsRemaining = new ArrayList<BoxedCell>();

        for (int row = 0; row < m_maze.getRows(); row++) {
            for (int col = 0; col < m_maze.getCols(); col++) {
                final Cell cell = m_maze.getCell(row, col);
                if (row == 0)
                    cell.setNeighbor(0, false);
                if (row == m_maze.getRows() - 1)
                    cell.setNeighbor(2, false);
                if (col == 0)
                    cell.setNeighbor(3, false);
                if (col == m_maze.getCols() - 1)
                    cell.setNeighbor(1, false);

                m_generationCells[row][col] = new BoxedCell(cell);
                m_generationCellsRemaining.add(m_generationCells[row][col]);
            }
        }

        BoxedCell randomCell = m_generationCellsRemaining.remove(new Random().nextInt(m_generationCellsRemaining.size()));
        randomCell.visit();

        while (dig()) ;
    }

    private boolean dig() {
        BoxedCell c1;

        if (m_lastCell == null) {
            do {
                if (m_generationCellsRemaining.isEmpty())
                    return false;

                m_lastCell = m_generationCellsRemaining.remove(new Random().nextInt(m_generationCellsRemaining.size()));
            } while (m_lastCell.isVisited());
            m_previousCells.push(m_lastCell);
            return true;
        }

        final BoxedCell currentCell = m_previousCells.peek();
        final int[] move = pickRandomMove(currentCell.unwrap());

        final int row = currentCell.unwrap().getRow(), col = currentCell.unwrap().getCol();
        BoxedCell nextCell = m_generationCells[row + move[0]][col + move[1]];

        if (m_previousCells.search(nextCell) != -1) {
            eraseLoopingPath(m_previousCells, nextCell);
        } else {
            nextCell.setAntecessor(m_lastCell);
        }
        m_previousCells.push(nextCell);

        if (!nextCell.isVisited()) {
            m_lastCell = nextCell;
            return true;
        }

        nextCell = m_previousCells.pop();
        while (nextCell.getAntecessor() != null) {
            m_lastCell = nextCell.getAntecessor();
            if (m_lastCell != null)
                mergeCells(m_lastCell.unwrap(), nextCell.unwrap());

            nextCell.visit();
            nextCell.setAntecessor(null);
            nextCell = m_previousCells.pop();
        }

        nextCell.visit();
        m_lastCell = null;

        return true;
    }

    private void eraseLoopingPath(Stack<BoxedCell> tail, BoxedCell head) {
        BoxedCell nextCell = tail.pop();
        do nextCell.setAntecessor(null); while (!(nextCell = tail.pop()).equals(head));
    }

    private void mergeCells(Cell a, Cell b) {
        final int rowDelta = b.getRow() - a.getRow(), colDelta = b.getCol() - a.getCol();
        if (rowDelta == -1 && colDelta == 0) {
            a.setNeighbor(0, false);
            b.setNeighbor(2, false);
        } else if (rowDelta == 0 && colDelta == 1) {
            a.setNeighbor(1, false);
            b.setNeighbor(3, false);
        } else if (rowDelta == 1 && colDelta == 0) {
            a.setNeighbor(2, false);
            b.setNeighbor(0, false);
        } else if (rowDelta == 0 && colDelta == -1) {
            a.setNeighbor(3, false);
            b.setNeighbor(1, false);
        }
    }

    private int[] pickRandomMove(Cell cell) {
        final ArrayList<int[]> legalMoves = new ArrayList<int[]>();
        final ArrayList<int[]> allowedMoves = new ArrayList<int[]>();

        for (int row = 0; row < Maze.MAX_NEIGHBORS; row++) {
            int[] move = new int[]{Maze.LEGAL_MOVES[row][0], Maze.LEGAL_MOVES[row][1]};
            legalMoves.add(move);
            allowedMoves.add(move);
        }

        if (cell.getRow() - 1 < 0)
            allowedMoves.remove(allowedMoves.indexOf(legalMoves.get(0)));
        if (cell.getCol() + 1 >= m_maze.getCols())
            allowedMoves.remove(allowedMoves.indexOf(legalMoves.get(1)));
        if (cell.getRow() + 1 >= m_maze.getRows())
            allowedMoves.remove(allowedMoves.indexOf(legalMoves.get(2)));
        if (cell.getCol() - 1 < 0)
            allowedMoves.remove(allowedMoves.indexOf(legalMoves.get(3)));

        return allowedMoves.get(new Random().nextInt(allowedMoves.size()));
    }

    private Maze getMaze() {
        return m_maze;
    }

    public static Maze createMaze(int rows, int cols) {
        MazeFactory mazeFactory = new MazeFactory(rows, cols);
        mazeFactory.generate();
        return mazeFactory.getMaze();
    }
}
