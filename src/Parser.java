
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/** *****************************************************************************
 *  Name:    Pramuditha Nadun
 *  UOW ID:  W1902262
 *  IIT ID:  20212171
 *
 *  Description:  Parser Implementation - Maps the puzzle
 *
 **************************************************************************** */

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

        // 2d array to store the puzzle
        char[][] puzzle = new char[numRows][numCols];
        scanner = new Scanner(file); // reset scanner to beginning of file

        for (int i = 0; i < numRows; i++) {
            String line = scanner.nextLine().trim(); // Read a line of a file and trim any leading or trailing whitespaces
            for (int j = 0; j < numCols; j++) {
                puzzle[i][j] = line.charAt(j);
            }
        }

        return puzzle;
    }

    public void displayMap(char[][] puzzle) {
        for (char[] row : puzzle) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    //Find positions of the nodes in the puzzle
    public int[] findNodes(char[][] puzzle, char element) {
        for (int y = 0; y < puzzle.length; y++) {
            for (int x = 0; x < puzzle[y].length; x++) {
                if (puzzle[y][x] == element) { // Index out of bounds Error handling
                    return new int[]{y, x}; // Corrected to return {y, x} instead of {x, y}
                }
            }
        }
        return null;
    }
}
