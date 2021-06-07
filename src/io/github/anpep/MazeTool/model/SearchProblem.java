package io.github.anpep.MazeTool.model;

public class SearchProblem {
    private final Maze m_maze;
    private final State m_initialState;
    private final State m_goalState;

    public SearchProblem(Maze maze, int initialRow, int initialCol, int goalRow, int goalCol) {
        m_maze = maze;
        m_initialState = new State(maze, initialRow, initialCol);
        m_goalState = new State(maze, goalRow, goalCol);
    }

    public Maze getMaze() {
        return m_maze;
    }

    public State getInitialState() {
        return m_initialState;
    }

    public State getGoalState() {
        return m_goalState;
    }
}
