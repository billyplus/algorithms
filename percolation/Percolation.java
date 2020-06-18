/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    final private WeightedQuickUnionUF unionUF;
    private int topIndex;
    // private final int bottomIndex;
    private int countOfOpen;
    private boolean[] opened;
    private boolean[] full;
    private int[] bottom;
    private boolean isPercolate;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("invalid n...");
        this.n = n;
        unionUF = new WeightedQuickUnionUF(n * n + 1);
        opened = new boolean[n * n + 1];
        full = new boolean[n * n + 1];
        bottom = new int[n];
        topIndex = 0;
        // bottomIndex = n * n + 1;
        opened[topIndex] = true; // visual grid as top
        full[topIndex] = true;
        // opened[bottomIndex] = true; // visual grid as bottom
        countOfOpen = 0;
        isPercolate = false;
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In("input20.txt");      // input file
        int n = in.readInt();         // n-by-n percolation system

        // repeatedly read in sites to open and draw resulting system
        Percolation perc = new Percolation(n);
        // int num = 0;
        while (!in.isEmpty()) {
            // num++;
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
            // StdOut.printf("%d %d %d %b %b\n", num, i, j, perc.isFull(i, j), perc.isOpen(i, j));
            if (perc.isFull(i, j) && !perc.isOpen(i, j)) {
                throw new RuntimeException(
                        String.format("row=%d col=%d is full but not open...", i, j));
            }
        }
        if (!perc.percolates()) {
            throw new RuntimeException("percolates should be true");
        }
        perc = new Percolation(3);
        perc.open(2, 1);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 3);
        perc.open(3, 1);
        if (!perc.percolates()) {
            throw new RuntimeException("percolates 3*3 should be true");
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = getIndex(row, col);
        if (opened[index]) return;
        countOfOpen++;
        opened[index] = true;
        if (row == 1) {
            // connect to first row
            unionUF.union(topIndex, index);
            full[index] = true;
        }


        if (row < n) {
            tryUnion(index, row + 1, col);
        }
        if (row > 1) {
            tryUnion(index, row - 1, col);
        }
        if (col > 1) {
            tryUnion(index, row, col - 1);
        }
        if (col < n) {
            tryUnion(index, row, col + 1);
        }
        if (row == n) {
            for (int i = 1; i <= n; i++) {
                if (bottom[i] != i) continue;

            }
        }
        if (!isPercolate && isFull(row, col)) {
            int p = unionUF.find(topIndex);
            boolean lastOpen = false;
            for (int i = 1; i <= n; i++) {
                if (isOpen(n, i)) {
                    if (lastOpen) continue;
                    lastOpen = true;
                    int idx2 = getIndex(n, i);
                    if (full[idx2]) {
                        isPercolate = true;
                        break;
                    }
                    else {
                        int q = unionUF.find(idx2);
                        if (full[q]) {
                            isPercolate = true;
                            break;
                        }
                        else if (q == p) {
                            full[idx2] = true;
                            isPercolate = true;
                            break;
                        }
                    }
                }
                else {
                    lastOpen = false;
                }
            }
        }

    }

    private int getIndex(int row, int col) {
        if (row <= 0 || row > n
                || col <= 0 || col > n) throw new IllegalArgumentException(
                String.format("invalid row=%d col=%d ...", row, col));
        return (row - 1) * n + col;
    }

    private void tryUnion(int idx, int row, int col) {
        int idx2 = getIndex(row, col);
        if (opened[idx2]) {
            unionUF.union(idx, idx2);
            if (full[idx2]) {
                full[idx] = true;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int index = getIndex(row, col);
        return opened[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        if (full[index]) {
            return true;
        }
        boolean conn = connected(topIndex, index);
        full[index] = conn;
        return conn;
    }

    private boolean connected(int p, int q) {
        return unionUF.find(p) == unionUF.find(q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return isPercolate;
    }
}
