public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        throw new IllegalArgumentException();
    }

    // string representation of this board
    public String toString() {
        throw new IllegalArgumentException();
    }

    // board dimension n
    public int dimension() {
        throw new IllegalArgumentException();
    }

    // number of tiles out of place
    public int hamming() {
        throw new IllegalArgumentException();
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        throw new IllegalArgumentException();
    }

    // is this board the goal board?
    public boolean isGoal() {
        throw new IllegalArgumentException();
    }

    // does this board equal y?
    public boolean equals(Object y) {
        throw new IllegalArgumentException();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        throw new IllegalArgumentException();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        throw new IllegalArgumentException();
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        throw new IllegalArgumentException();
    }

}