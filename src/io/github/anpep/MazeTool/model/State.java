package io.github.anpep.MazeTool.model;

import java.sql.Array;
import java.util.ArrayList;

public class State extends Cell implements Cloneable {
    public State(Cell cell) {
        super(cell.getRow(), cell.getCol());
        System.arraycopy(cell.getNeighbors(), 0, getNeighbors(), 0, Maze.MAX_NEIGHBORS);
        setValue(cell.getValue());
    }

    public State(Maze maze, int row, int col) {
        this(maze.getCell(row, col));
    }

    public ArrayList<Successor> getSuccessors(Maze stateSpace) {
        final ArrayList<Successor> successors = new ArrayList<Successor>();

        for (Move move : Move.values()) {
            try {
                final int[] moveDeltas = Maze.LEGAL_MOVES[move.toOrientationIndex()];
                final State newState = new State(stateSpace.getCell(getRow() + moveDeltas[0], getCol() + moveDeltas[1]));
                successors.add(new Successor(move, newState, stateSpace.getCell(newState.getRow(), newState.getCol()).getValue()));
            } catch (ArrayIndexOutOfBoundsException ex) {}
        }

        return successors;
    }

    public boolean isGoal(int row, int col) {
        return getRow() == row && getCol() == col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", getRow(), getCol());
    }

    @Override
    protected Object clone() {
        return new State((Cell) super.clone());
    }
}
