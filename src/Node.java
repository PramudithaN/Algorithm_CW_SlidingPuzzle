import java.util.Objects;

/** *****************************************************************************
 *  Name:    Pramuditha Nadun
 *  UOW ID:  W1902262
 *  IIT ID:  20212171
 *
 *  Description:  Node Implementation- Position of a single node is represented in the puzzle
 *
 **************************************************************************** */

public class Node {
        int row;
        int col;
        int hScore;

        Node(int row, int col, int hScore) {
            this.row = row;
            this.col = col;
            this.hScore = hScore;
        }

        // Map the position of a node in the puzzle
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
