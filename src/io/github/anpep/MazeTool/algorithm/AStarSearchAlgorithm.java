package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchTreeNode;

public class AStarSearchAlgorithm implements ISearchAlgorithm {
    @Override
    public double getHeuristic(SearchTreeNode node) {;
        return node.getHeuristic() + node.getCost();
    }

    @Override
    public String toString() {
        return "a_star";
    }
}
