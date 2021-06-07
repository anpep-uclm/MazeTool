package io.github.anpep.MazeTool.util;

import io.github.anpep.MazeTool.model.Maze;
import io.github.anpep.MazeTool.model.Move;
import io.github.anpep.MazeTool.model.SearchTreeNode;
import io.github.anpep.MazeTool.model.State;

public class SearchTreeNodeFactory {
    private static Maze s_mockMaze = new Maze(100, 100);
    private static int s_row = 0;
    private static int s_col = 0;

    public static SearchTreeNode createTestNode() {
        final State state = new State(s_mockMaze, s_row, s_col);
        final SearchTreeNode node = new SearchTreeNode(state, 0, 0, 1, 42, Move.EAST, null);

        if (++s_col % s_mockMaze.getCols() == 0) {
            s_col = 0;
            s_row++;
        }

        return node;
    }
}
