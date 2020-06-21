import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private final int minMoves;

    private class Node {
        public final Board node;
        public final Board parent;

        // private final int step;
        public Node(Board b, Board parent) {
            node = b;
            this.parent = parent;
            // this.step = step;
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        ArrayList<Node> steps = new ArrayList<Node>();
        ArrayList<Node> unreachable = new ArrayList<Node>();
        MinPQ<Node> solveTree = new MinPQ<Node>();
        solveTree.insert(new Node(initial, null));
        boolean skip = false;
        while (!solveTree.isEmpty()) {
            skip = false;
            Node current = solveTree.delMin();
            for (Node n : solveTree) {
                if (current.node.equals(n.node)) {
                    skip = true;
                }
            }
            if (skip) {
                continue;
            }
            steps.add(current);
            if (current.isGoal()) {

            }
            for (Board n : current.neighbors()) {
                solveTree.insert(n);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return minMoves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        throw new IllegalArgumentException();

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