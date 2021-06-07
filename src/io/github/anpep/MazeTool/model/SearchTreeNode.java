package io.github.anpep.MazeTool.model;

public class SearchTreeNode implements Comparable<SearchTreeNode> {
    private final int m_id;
    private final State m_state;
    private final int m_value;
    private final int m_depth;
    private final int m_cost;
    private final int m_heuristic;
    private final Move m_action;
    private final SearchTreeNode m_parentNode;

    private static int s_idCounter = 0;

    public SearchTreeNode(State state, int value, int depth, int cost, int heuristic, Move action, SearchTreeNode parentNode) {
        m_id = s_idCounter++;
        m_state = state;
        m_value = value;
        m_depth = depth;
        m_cost = cost;
        m_heuristic = heuristic;
        m_action = action;
        m_parentNode = parentNode;
    }

    public int getID() {
        return m_id;
    }

    public State getState() {
        return m_state;
    }

    public int getValue() {
        return m_value;
    }

    public int getDepth() {
        return m_depth;
    }

    public int getCost() {
        return m_cost;
    }

    public int getHeuristic() {
        return m_heuristic;
    }

    public Move getAction() {
        return m_action;
    }

    public SearchTreeNode getParentNode() {
        return m_parentNode;
    }

    private int getSortScore() {
        return (int) (1e3 * getValue() + 1e2 * getState().getRow() + 1e1 * getState().getCol() + 1e0 * getID());
    }

    @Override
    public String toString() {
        return String.format("[%d][%d, %s, %d, '%s', %d, %d, %d]", m_id, m_cost, m_state, m_parentNode == null ? -1 : m_parentNode.getID(), m_action, m_depth, m_heuristic, m_value);
    }

    @Override
    public int compareTo(SearchTreeNode node) {
        return getSortScore() - node.getSortScore();
    }
}
