import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the file path : ");
            String fileName = input.nextLine();

            // Parse the game board from the input file
            Parser parser = new Parser();
            char[][] puzzle = parser.parseMapFromFile(fileName);

            // Display the parsed map
            System.out.println("********* THE MAP **************");
            parser.displayMap(puzzle);

            // Find the start and end points
            int[] startPos = parser.findNodes(puzzle, 'S');
            int[] finishPos = parser.findNodes(puzzle, 'F');
            System.out.println("Start Position: " + (startPos[1] + 1) + ", " + (startPos[0] + 1));
            System.out.println("Finish Position: " + (finishPos[1] + 1) + ", " + (finishPos[0] + 1));
            System.out.println("********************************");

            // Create AStar instance
            AStarPathfinder aStar = new AStarPathfinder(puzzle, startPos, finishPos);

            // Find the complete path
            List<int[]> shortestPath = aStar.findShortestPath();

            if (shortestPath != null) {
                System.out.println("Shortest Path Found:");
                printPathWithDirections(shortestPath, startPos);
            } else {
                System.out.println("No path found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void printPathWithDirections(List<int[]> path, int[] startPos) {
        if (path.isEmpty()) {
            System.out.println("No path found");
            return;
        }

        System.out.println("Start at " + getPathPositionString(startPos)); // Print the starting position
        int step = 1;
        for (int i = 0; i < path.size(); i++) {
            int[] currPos = path.get(i);
            String direction;
            if (i == 0) {
                direction = getDirection(startPos, currPos);
            } else {
                int[] prevPos = path.get(i - 1);
                direction = getDirection(prevPos, currPos);
            }
            System.out.println(step + ". " + direction + " to " + getPathPositionString(currPos));
            step++;
        }
        System.out.println("Done!");
    }

    private static String getDirection(int[] from, int[] to) {
        int dx = to[1] - from[1];
        int dy = to[0] - from[0];

        if (dx > 0) return "Move right";
        if (dx < 0) return "Move left";
        if (dy > 0) return "Move down";
        if (dy < 0) return "Move up";
        return "Invalid direction";
    }

    private static String getPathPositionString(int[] position) {
        return "(" + (position[1]+1 ) + ", " + (position[0]+1 ) + ")"; // Adjusted to start from the 1 index
    }
}
