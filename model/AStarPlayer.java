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

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(Node::getDist));

        open.add(new Node(game.getGameBoard(), game.getHeuristicValue(game.getGameBoard()), 0, null));

        HashSet<Node> closed = new HashSet<>();
        do {
            Node current = open.poll();
            if (game.isSolution(current.getBord())) {
                System.out.println("Find way");
                return result;
            }

            closed.add(current);
            action[] possibleActions = game.getPossibleActions(current.getBord());
            for (PuzzleGame.action ac : possibleActions) {
                Integer[][] nextBord = game.computeAction(ac, current.getBord());
                if (closed.stream().anyMatch(node -> Arrays.deepEquals(node.getBord(), nextBord))) {
                    continue;
                }
                int way = current.estimate + 1;
                if (open.stream().anyMatch(node -> Arrays.deepEquals(node.getBord(), nextBord) && way >= node.getDist())) {
                    continue;
                }

                if (open.stream().noneMatch(node -> {
                    if (Arrays.deepEquals(node.getBord(), nextBord)) {
                        node.setEstimate(way + game.getHeuristicValue(nextBord));
                        return true;
                    }
                    return false;
                })) {
                    Node next = new Node(nextBord, way + game.getHeuristicValue(nextBord), way, current);
                    open.add(next);
                }


            }

        } while (!open.isEmpty());

        return null;
    }

    private class Node {

        private Integer[][] bord;
        private int estimate;
        private int dist;
        private Node previous;

        public Node(Integer[][] bord, int estimate, int dist, Node previous) {
            this.bord = bord;
            this.estimate = estimate;
            this.dist = dist;
            this.previous = previous;
        }

        public int getEstimate() {
            return estimate;
        }

        public void setEstimate(int estimate) {
            this.estimate = estimate;
        }

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Integer[][] getBord() {
            return bord;
        }

        public void setBord(Integer[][] bord) {
            this.bord = bord;
        }

        public int getDist() {
            return estimate;
        }

        public void setDist(int dist) {
            this.estimate = dist;
        }
    }
}

