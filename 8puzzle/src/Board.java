import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int[][] tiles;
    private final int size;
    private final int hamming;
    private final int manhattan;
    private int zeroX;
    private int zeroY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];
        int v = 0;
        int hamming = 0;
        int manhattan = 0;
        int dx = 0;
        int dy = 0;
        int idx = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                idx++;
                v = tiles[i][j];
                this.tiles[i][j] = v;
                if (v == 0) {
                    zeroX = i;
                    zeroY = j;
                }
                if (v > 0 && idx != v) {
                    hamming++;
                }
                if (v > 0) {
                    dx = (v - 1) / size;
                    dy = v - 1 - dx * size;
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
        }
        this.hamming = hamming;
        this.manhattan = manhattan;
    }

    // string representation of this board
    public String toString() {
        String ret = "";
        ret += size;
        ret += "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ret += " ";
                ret += tiles[i][j];
            }
            ret += "\n";
        }
        return ret;
    }

    // board dimension n
    public int dimension() {
        return size;
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
            return false;
        }
        if (this == y) {
            return true;
        }
        if (this.getClass() != y.getClass())
            return false;

        Board b = (Board) y;
        if (b.size != this.size || b.hamming != this.hamming || b.manhattan != this.manhattan) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addNeighbour(ArrayList<Board> list, int nzerox, int nzeroy) {
        int[][] n = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                n[i][j] = tiles[i][j];
            }
        }

        n[zeroX][zeroY] = tiles[nzerox][nzeroy];
        n[nzerox][nzeroy] = 0;
        list.add(new Board(n));
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> list = new ArrayList<Board>();
        if (zeroX > 0) {
            addNeighbour(list, zeroX - 1, zeroY);
        }
        if (zeroY > 0) {
            addNeighbour(list, zeroX, zeroY - 1);
        }
        if (zeroX < size - 1) {
            addNeighbour(list, zeroX + 1, zeroY);
        }
        if (zeroY < size - 1) {
            addNeighbour(list, zeroX, zeroY + 1);
        }
        return list;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] n = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                n[i][j] = tiles[i][j];
            }
        }
        if (tiles[0][1] == 0 || tiles[0][0] == 0) {
            n[1][0] = tiles[1][1];
            n[1][1] = tiles[1][0];
        } else {
            n[0][1] = tiles[0][0];
            n[0][0] = tiles[0][1];
        }
        return new Board(n);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // read in the board specified in the filename
        String filename = "puzzle04.txt";
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        // solve the slider puzzle
        Board initial = new Board(tiles);
        StdOut.println(filename + ": manhattan=" + initial.manhattan + " hamming=" + initial.hamming);
        StdOut.println(initial.toString());

        StdOut.println("twin");
        StdOut.println(initial.twin().toString());

        // for (Board b : initial.neighbors()) {
        // StdOut.println(b.toString());
        // }
    }

}