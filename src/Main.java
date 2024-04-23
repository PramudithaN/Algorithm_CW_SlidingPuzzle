
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
            List<int[]> completePath = aStar.findShortestPath();

            if (completePath != null) {
                System.out.println("Complete Path Found:");
                for (int[] position : completePath) {
                    System.out.println("(" + position[0] + ", " + position[1] + ")");
                }
            } else {
                System.out.println("No path found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
