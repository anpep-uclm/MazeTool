package io.github.anpep.MazeTool.util;

import io.github.anpep.MazeTool.algorithm.ISearchAlgorithm;
import io.github.anpep.MazeTool.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SearchProblemSolver {
    public static final int MAX_DEPTH = 1000000;

    public static ArrayList<SearchTreeNode> solve(SearchProblem problem, ISearchAlgorithm algorithm) {
        SearchTreeNode.resetIDCounter();

        final Frontier frontier = new Frontier();
        final SearchTreeNode initialNode = new SearchTreeNode(problem.getInitialState(), problem.getInitialState().getValue(), 0, 0, algorithm, Move.NOT_SET, null);
        frontier.add(initialNode);

        final State goalState = problem.getGoalState();
        final HashMap<String, Double> visitedNodes = new HashMap<String, Double>();
        final LinkedList<SearchTreeNode> solution = new LinkedList<SearchTreeNode>();
        SearchTreeNode currentNode = null;
        boolean didFindSolution = false;

        while (!frontier.isEmpty()) {
            currentNode = frontier.remove();

            if (currentNode.getState().isGoal(goalState.getRow(), goalState.getCol())) {
                didFindSolution = true;
                break;
            }

            if (!visitedNodes.containsKey(currentNode.getState().toString()) && currentNode.getDepth() < MAX_DEPTH) {
                visitedNodes.put(currentNode.getState().toString(), currentNode.getHeuristic());

                final ArrayList<Successor> successors = currentNode.getState().getSuccessors(problem.getMaze());
                final ArrayList<SearchTreeNode> nodes = resolveSuccessorNodes(problem, algorithm, currentNode, successors);
                frontier.addAll(nodes);
            }
        }

        if (didFindSolution) {
            while (currentNode.getParentNode() != null) {
                solution.addFirst(currentNode);
                currentNode = currentNode.getParentNode();
            }

            solution.addFirst(initialNode);

            return new ArrayList<SearchTreeNode>(solution);
        }

        return null;
    }

    public static void writeToFile(ArrayList<SearchTreeNode> solution, String path) throws IOException {
        final PrintWriter writer = new PrintWriter(new FileWriter(path));

        writer.println("[id][cost,state,father_id,action,depth,h,value]");
        for (SearchTreeNode node : solution)
            writer.printf("[%d][%d,%s,%s,%s,%d,%f,%d]\n", node.getID(), node.getCost(), node.getState(), node.getParentNode() == null ? "None" : node.getParentNode().getID(), node.getAction(), node.getDepth(), node.getHeuristic(), node.getValue());

        writer.close();
    }

    private static ArrayList<SearchTreeNode> resolveSuccessorNodes(SearchProblem problem, ISearchAlgorithm algorithm, SearchTreeNode antecessorNode, ArrayList<Successor> successors) {
        final ArrayList<SearchTreeNode> nodes = new ArrayList<SearchTreeNode>();
        for (Successor successor : successors) {

            nodes.add(new SearchTreeNode(
                    successor.getState(),
                    successor.getState().getValue(),
                    antecessorNode.getDepth() + 1,
                    antecessorNode.getCost() + successor.getCost(),
                    algorithm,
                    successor.getMove(),
                    antecessorNode));
        }
        return nodes;
    }
}
