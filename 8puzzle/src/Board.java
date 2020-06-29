public class Board {
    private final int[][] tiles;
    private final int n;
    private final int hamming;
    private final int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;
        this.tiles = new int[n][n];
        int v = 0;
        int hamming = 0;
        int manhattan = 0;
        int dx = 0;
        int dy = 0;
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                v = tiles[i][j];
                this.tiles[i][j] = v;
                if (idx != v) {
                    hamming++;
                }
                dx = (v - 1) / n;
                dy = v - 1 - dx * n;
                if (dx > i) {
                    manhattan += dx - i;
                } else {
                    manhattan += i - dx;
                }
                if (dy > j) {
                    manhattan += dy - j;
                } else {
                    manhattan += j - dy;
                }
            }
        }
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    // string representation of this board
    public String toString() {
        String ret = "";
        ret += n;
        ret += "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ret += " ";
                ret += tiles[i][j];
            }
            ret += "\n";
        }
        return ret;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            throw new IllegalArgumentException();
        }
        if (this == y) {
            return true;
        }
        Board b = (Board) y;
        if (b.n != this.n || b.hamming != this.hamming || b.manhattan != this.manhattan) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
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