import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the file path without the extension (e.g., src/FolderName/fileName): ");
            String fileName = input.nextLine();

            // Parse the game board from the input file
            Parser parser = new Parser();
            char[][] gameBoard = parser.parseMapFromFile(fileName + ".txt");

            // Display the parsed map
            System.out.println("********* THE MAP **************");
            parser.displayMap(gameBoard);

            // Find the start and end points
            int[] startPos = parser.findSpecificElement(gameBoard, 'S');
            int[] finishPos = parser.findSpecificElement(gameBoard, 'F');
            System.out.println("Start Position: " + startPos[0] + ", " + startPos[1]);
            System.out.println("Finish Position: " + finishPos[0] + ", " + finishPos[1]);
            System.out.println("********************************");

            // Create AStar instance
            AStar aStar = new AStar(gameBoard, startPos, finishPos);

            // Find the complete path
            List<int[]> completePath = aStar.findCompletePath();

            if (completePath != null) {
                System.out.println("Complete Path Found:");
                printPathWithDirections(completePath,startPos);
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
        int dx = to[0] - from[0];
        int dy = to[1] - from[1];

        if (dx > 0) return "Move down";
        if (dx < 0) return "Move up";
        if (dy > 0) return "Move right";
        if (dy < 0) return "Move left";
        return "Invalid direction"; // This should not happen
    }


    private static String getPathPositionString(int[] position) {
        return "(" + position[0] + ", " + position[1] + ")";
    }
}