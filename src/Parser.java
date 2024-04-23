
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    public char[][] parseMapFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        int numRows = 0;
        int numCols = 0;
        while (scanner.hasNextLine()) {
            numRows++;
            numCols = scanner.nextLine().trim().length();
        }

        char[][] gameBoard = new char[numRows][numCols];
        scanner = new Scanner(file); // reset scanner to beginning of file

        for (int i = 0; i < numRows; i++) {
            String line = scanner.nextLine().trim();
            for (int j = 0; j < numCols; j++) {
                gameBoard[i][j] = line.charAt(j);
            }
        }

        return gameBoard;
    }

    public void displayMap(char[][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public int[] findSpecificElement(char[][] gameBoard, char element) {
        for (int y = 0; y < gameBoard.length; y++) {
            for (int x = 0; x < gameBoard[y].length; x++) {
                if (gameBoard[y][x] == element) { // Index out of bounds Error handling
                    return new int[]{y, x}; // Corrected to return {y, x} instead of {x, y}
                }
            }
        }
        return null;
    }
}
