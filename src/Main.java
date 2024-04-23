import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/** *****************************************************************************
 *  Name:    Pramuditha Nadun
 *  UOW ID:  W1902262
 *  IIT ID:  20212171
 *
 *  Description:  Main method Implementation
 *
 **************************************************************************** */

public class Main {

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the file path : ");
            String fileName = input.nextLine();
            System.out.println();

            // Parse the puzzle from the input file
            Parser parser = new Parser();
            char[][] puzzle = parser.parseMapFromFile(fileName);

            // Display the mapped puzzle
            System.out.println("********* THE MAP **************");
            System.out.println();

            parser.displayMap(puzzle);

            // Find the start and end points
            int[] startPos = parser.findNodes(puzzle, 'S');
            int[] finishPos = parser.findNodes(puzzle, 'F');

            System.out.println();
            System.out.println("Start Position: " + (startPos[1] + 1) + ", " + (startPos[0] + 1));
            System.out.println("Finish Position: " + (finishPos[1] + 1) + ", " + (finishPos[0] + 1));
            System.out.println();
            System.out.println("********************************");

            // Record the start time
            long startTime = System.nanoTime();

            // Create AStar instance
            AStarPathfinder aStar = new AStarPathfinder(puzzle, startPos, finishPos);

            // Find the shortest path
            List<int[]> shortestPath = aStar.findShortestPath();

            // Record the end time
            long endTime = System.nanoTime();

            if (shortestPath != null) {

                System.out.println();
                System.out.println("Shortest Path Found:");
                System.out.println();

                printPathWithDirections(shortestPath, startPos);
            } else {
                System.out.println("No path found.");
            }

            // Calculate and print the elapsed time
            long elapsedTime = endTime - startTime;
            System.out.println();
            System.out.println("Time taken to find the shortest path: " + elapsedTime / 1_000_000 + " milliseconds");

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

        System.out.println("Start at " + getPathPosition(startPos)); // Print the starting position
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
            System.out.println(step + ". " + direction + " to " + getPathPosition(currPos));
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

    private static String getPathPosition(int[] position) {
        return "(" + (position[1]+1 ) + ", " + (position[0]+1 ) + ")"; // Adjusted to start from the 1 index
    }
}
