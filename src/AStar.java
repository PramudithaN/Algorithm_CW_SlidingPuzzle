import java.util.*;

public class AStar {
    private char[][] gameBoard;
    private int[] startPos;
    private int[] finishPos;
    private int numRows;
    private int numCols;

    // Constructor
    public AStar(char[][] gameBoard, int[] startPos, int[] finishPos) {
        this.gameBoard = gameBoard;
        this.startPos = startPos;
        this.finishPos = finishPos;
        this.numRows = gameBoard.length;
        this.numCols = gameBoard[0].length;
    }

    // Method to find the shortest path using A* algorithm
    public List<String> findShortestPath() {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        Set<Node> closedSet = new HashSet<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Integer> gScore = new HashMap<>();
        Map<Node, Integer> fScore = new HashMap<>();

        Node startNode = new Node(startPos[0], startPos[1], 0, heuristic(startPos[0], startPos[1]));
        openSet.add(startNode);
        gScore.put(startNode, 0);
        fScore.put(startNode, startNode.hScore);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.row == finishPos[0] && current.col == finishPos[1]) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            List<Node> neighbors = getNeighbors(current);
            for (Node neighbor : neighbors) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    fScore.put(neighbor, tentativeGScore + neighbor.hScore);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return null; // No path found
    }

    // Heuristic function (Manhattan distance)
    private int heuristic(int row, int col) {
        return Math.abs(row - finishPos[0]) + Math.abs(col - finishPos[1]);
    }

    // Method to get neighboring nodes
    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] dir : directions) {
            int newRow = node.row + dir[0];
            int newCol = node.col + dir[1];

            if (isValid(newRow, newCol)) {
                neighbors.add(new Node(newRow, newCol, 0, heuristic(newRow, newCol)));
            }
        }

        return neighbors;
    }

    // Method to check if a position is valid on the game board
    private boolean isValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && gameBoard[row][col] != '0';
    }

    // Method to reconstruct the path
    private List<String> reconstructPath(Map<Node, Node> cameFrom, Node current) {
        List<String> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            Node prev = cameFrom.get(current);
            if (prev.row < current.row) {
                path.add("down");
            } else if (prev.row > current.row) {
                path.add("up");
            } else if (prev.col < current.col) {
                path.add("right");
            } else {
                path.add("left");
            }
            current = prev;
        }
        Collections.reverse(path);
        return path;
    }

    // Node class to represent positions on the game board
    private static class Node {
        int row;
        int col;
        int gScore;
        int hScore;
        int fScore;

        Node(int row, int col, int gScore, int hScore) {
            this.row = row;
            this.col = col;
            this.gScore = gScore;
            this.hScore = hScore;
            this.fScore = gScore + hScore;
        }
    }
}