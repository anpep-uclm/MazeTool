package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchTreeNode;

public class GreedySearchAlgorithm implements ISearchAlgorithm {
    @Override
    public double getHeuristic(SearchTreeNode node) {;
        return node.getHeuristic();
    }

    @Override
    public String toString() {
        return "greedy";
    }
}
