package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchTreeNode;

public class BreadthSearchAlgorithm implements ISearchAlgorithm {
    @Override
    public double getHeuristic(SearchTreeNode node) {;
        return node.getDepth();
    }

    @Override
    public String toString() {
        return "breadth";
    }
}
