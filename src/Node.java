import java.util.Objects;

public class Node { // Node class to represent positions on the puzzle
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
