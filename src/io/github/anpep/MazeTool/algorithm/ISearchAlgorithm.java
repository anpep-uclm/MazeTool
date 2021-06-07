package io.github.anpep.MazeTool.algorithm;

import io.github.anpep.MazeTool.model.SearchProblem;
import io.github.anpep.MazeTool.model.SearchTreeNode;

public interface ISearchAlgorithm {
    double getHeuristic(SearchTreeNode node);
    String toString();
}
