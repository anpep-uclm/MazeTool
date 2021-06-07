package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchTreeNode;

public class UniformCostSearchAlgorithm implements ISearchAlgorithm {
    @Override
    public double getHeuristic(SearchTreeNode node) {;
        return node.getCost();
    }

    @Override
    public String toString() {
        return "ucost";
    }
}
