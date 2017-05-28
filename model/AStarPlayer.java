package model;

import java.util.*;
import model.PuzzleGame.action;

/**
 * Created on 28.05.17.
 *
 * @author Kristof Dinkgräve
 */
public class AStarPlayer extends Player {

    @Override
    public List<PuzzleGame.action> solve(PuzzleGame game) {
        List<PuzzleGame.action> result = new ArrayList<>();

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::getEstimate));

        open.add(new Node(game.getGameBoard(), game.getHeuristicValue(game.getGameBoard()), 0, null, null));

        HashSet<Node> closed = new HashSet<>();
        do {
            Node current = open.poll();
            if (game.isSolution(current.getBord())) {
                do {
                    result.add(0, current.getAction());
                    current = current.getPrevious();
                } while (current.getPrevious() != null);

                return result;
            }

            closed.add(current);
            action[] possibleActions = game.getPossibleActions(current.getBord());
            for (PuzzleGame.action ac : possibleActions) {
                Integer[][] nextBord = game.computeAction(ac, current.getBord());
                if (closed.stream().anyMatch(node -> Arrays.deepEquals(node.getBord(), nextBord))) {
                    continue;
                }
                int way = current.getDist() + 1;
                if (open.stream().anyMatch(node -> Arrays.deepEquals(node.getBord(), nextBord) && way >= node.getDist())) {
                    continue;
                }

                Node finalCurrent = current;
                if (open.stream().noneMatch(node -> {
                    if (Arrays.deepEquals(node.getBord(), nextBord)) {
                        node.setEstimate(way + game.getHeuristicValue(nextBord));
                        node.setDist(way);
                        node.setPrevious(finalCurrent);
                        node.setAction(ac);
                        return true;
                    }
                    return false;
                })) {
                    Node next = new Node(nextBord, way + game.getHeuristicValue(nextBord), way, current, ac);
                    open.add(next);
                }

            }

        } while (!open.isEmpty());

        return null;
    }

    private class Node {

        private final Integer[][] bord;
        private int estimate;
        private int dist;
        private Node previous;
        private action action;

        Node(Integer[][] bord, int estimate, int dist, Node previous, action action) {
            this.bord = bord;
            this.estimate = estimate;
            this.dist = dist;
            this.previous = previous;
            this.action = action;
        }

        action getAction() {
            return action;
        }

        void setAction(action action) {
            this.action = action;
        }

        int getEstimate() {
            return estimate;
        }

        void setEstimate(int estimate) {
            this.estimate = estimate;
        }

        Node getPrevious() {
            return previous;
        }

        void setPrevious(Node previous) {
            this.previous = previous;
        }

        Integer[][] getBord() {
            return bord;
        }

        int getDist() {
            return dist;
        }

        void setDist(int dist) {
            this.dist = dist;
        }
    }
}

