import java.util.*;

public class AStar {
    private char[][] gameBoard;
    private int[] startPos;
    private int[] finishPos;
    private int numRows;
    private int numCols;
    private Set<Node> visited;

    // Constructor
    public AStar(char[][] gameBoard, int[] startPos, int[] finishPos) {
        this.gameBoard = gameBoard;
        this.startPos = startPos;
        this.finishPos = finishPos;
        this.numRows = gameBoard.length;
        this.numCols = gameBoard[0].length;
        this.visited = new HashSet<>();
    }

    // Method to find the complete path using A* algorithm
    public List<int[]> findCompletePath() {
        Queue<Node> openSet = new LinkedList<>();
        Map<Node, Node> cameFrom = new HashMap<>();

        Node startNode = new Node(startPos[0], startPos[1], heuristic(startPos[0], startPos[1]));

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            visited.add(current);

            if (current.row == finishPos[0] && current.col == finishPos[1]) {
                return reconstructCompletePath(cameFrom, current);
            }

            Node prevNode = cameFrom.get(current); // Get the previous node

            List<Node> neighbors = getValidNeighbors(current, prevNode);
            for (Node neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    cameFrom.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }

        return null; // No complete path found
    }

    // Heuristic function (Manhattan distance)
    private int heuristic(int row, int col) {
        return Math.abs(row - finishPos[0]) + Math.abs(col - finishPos[1]);
    }

    // Method to get valid neighboring nodes
    // Method to get valid neighboring nodes
    // Method to get valid neighboring nodes
    private List<Node> getValidNeighbors(Node node, Node prevNode) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Up, Down, Left, Right

        for (int[] dir : directions) {
            int newRow = node.row;
            int newCol = node.col;

            // Skip checking the direction opposite to the one they came from initially
            if (prevNode != null && newRow - prevNode.row == dir[0] && newCol - prevNode.col == dir[1]) {
                continue;
            }

            // Simulate sliding until encountering a wall or a rock
            while (isValid(newRow + dir[0], newCol + dir[1])) {
                newRow += dir[0];
                newCol += dir[1];
            }

            neighbors.add(new Node(newRow, newCol, heuristic(newRow, newCol)));
        }

        return neighbors;
    }





    // Method to check if a position is valid on the game board
    private boolean isValid(int row, int col) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols && gameBoard[row][col] != '0';
    }

    // Method to reconstruct the complete path
    private List<int[]> reconstructCompletePath(Map<Node, Node> cameFrom, Node current) {
        List<int[]> path = new ArrayList<>();
        while (cameFrom.containsKey(current)) {
            path.add(new int[]{current.row, current.col});
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    // Node class to represent positions on the game board
    private static class Node {
        int row;
        int col;
        int hScore;

        Node(int row, int col, int hScore) {
            this.row = row;
            this.col = col;
            this.hScore = hScore;
        }

        // Overriding equals and hashCode methods to properly use Node objects in HashSet and HashMap
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return row == node.row && col == node.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }
}