import java.util.ArrayList;
import java.util.Deque;
// import java.util.HashSet;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Node dest;

    private class Node implements Comparable<Node> {
        public final Board b;
        public final Node parent;
        public final int moves;
        private final int p;

        public Node(Board b, Node parent) {
            this.b = b;
            this.parent = parent;
            if (parent != null) {
                this.moves = parent.moves + 1;
            } else {
                this.moves = 0;
            }
            this.p = b.manhattan() + moves;
        }

        public int compareTo(Node other) {
            return this.p - other.p;
        }
    }

    private void addNeighbor(MinPQ<Node> lst, Node n) {
        for (Board b : n.b.neighbors()) {
            if (n.parent != null && b.equals(n.parent.b)) {
                continue;
            }
            lst.insert(new Node(b, n));
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<Node> lst = new MinPQ<Node>();
        MinPQ<Node> twinlst = new MinPQ<Node>();
        // ArrayList<Board> visited = new ArrayList<Board>();
        // HashSet<String> visited = new HashSet<String>();
        Node n = new Node(initial, null);
        lst.insert(n);
        Node twinn = new Node(initial.twin(), null);
        twinlst.insert(twinn);
        // visited.add(initial);
        // visited.add(twinn.b);
        // visited.add(initial.toString());
        // visited.add(twinn.b.toString());
        while (true) {
            n = lst.delMin();

            if (n.b.isGoal()) {
                // found
                dest = n;
                break;
            }

            twinn = twinlst.delMin();
            if (twinn.b.isGoal()) {
                // invalid
                dest = null;
                break;
            }

            addNeighbor(lst, n);

            addNeighbor(twinlst, twinn);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return dest != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return dest.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (dest == null) {
            return null;
        }
        Deque<Board> que = new LinkedList<Board>();
        // ArrayList<Board> que = new ArrayList<Board>();
        Node n = dest;
        while (n != null) {
            que.addFirst(n.b);
            n = n.parent;
        }
        return que;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}