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
    // Method to find the complete path using A* algorithm
    public List<int[]> findCompletePath() {
        Queue<Node> openSet = new LinkedList<>();
        Map<Node, Node> cameFrom = new HashMap<>();
        Map<Node, Integer> gScore = new HashMap<>();

        Node startNode = new Node(startPos[0], startPos[1], heuristic(startPos[0], startPos[1]));
        gScore.put(startNode, 0);

        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            visited.add(current);

            if (current.row == finishPos[0] && current.col == finishPos[1]) {
                return reconstructCompletePath(cameFrom, current);
            }

            // Explore neighbors
            for (int[] dir : new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}}) {
                int newRow = current.row;
                int newCol = current.col;

                // Move in the specified direction until hitting an obstacle or the edge of the board
                while (isValid(newRow + dir[0], newCol + dir[1])) {
                    newRow += dir[0];
                    newCol += dir[1];

                    // Check if the new position is the finish position
                    if (newRow == finishPos[0] && newCol == finishPos[1]) {
                        cameFrom.put(new Node(newRow, newCol, 0), current);
                        return reconstructCompletePath(cameFrom, new Node(newRow, newCol, 0));
                    }
                }

                Node neighbor = new Node(newRow, newCol, heuristic(newRow, newCol));
                int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
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