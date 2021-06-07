package io.github.anpep.MazeTool.model;

public class Successor {
    private final Move m_move;
    private final State m_state;
    private final int m_cost;

    public Successor(Move move, State state, int cost) {
        m_move = move;
        m_state = state;
        m_cost = cost;
    }

    public Move getMove() {
        return m_move;
    }

    public State getState() {
        return m_state;
    }

    public int getCost() {
        return m_cost;
    }

    @Override
    public String toString() {
        return String.format("['%s', %s, %d]", m_move, m_state, m_cost);
    }
}
