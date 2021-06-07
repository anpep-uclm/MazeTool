package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchTreeNode;

public class DepthSearchAlgorithm implements ISearchAlgorithm {
    @Override
    public double getHeuristic(SearchTreeNode node) {;
        return 1.0 / (1 + node.getDepth());
    }

    @Override
    public String toString() {
        return "depth";
    }
}
