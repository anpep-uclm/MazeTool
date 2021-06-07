package io.github.anpep.MazeTool;

import io.github.anpep.MazeTool.model.Frontier;
import io.github.anpep.MazeTool.util.SearchTreeNodeFactory;

public class SearchTreeTest {
    public static void main(String[] args) {
        final Frontier frontier = new Frontier();

        /* Test push */
        for (int i = 0; i < 10000; i++)
            frontier.add(SearchTreeNodeFactory.createTestNode());

        /* Test pop with correct sorting order */
        for (int i = 0; i < 10000; i++)
            System.out.println(frontier.remove());
    }
}
