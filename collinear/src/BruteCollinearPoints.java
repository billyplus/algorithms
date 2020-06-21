import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final LineSegment[] lines;

    private boolean check4points(Point p1, Point p2, Point p3, Point p4) {
        double r1 = p1.slopeTo(p2);
        double r2 = p2.slopeTo(p3);
        double r3 = p3.slopeTo(p4);
        if (r1 == Double.NEGATIVE_INFINITY || r2 == Double.NEGATIVE_INFINITY || r3 == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException();
        return r1 == r2 && r1 == r3;
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        int size = points.length;
        Point[] lst = new Point[size];
        for (int i = 0; i < size; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            lst[i] = points[i];
        }
        Arrays.sort(lst);
        for (int i = 0; i < size - 1; i++) {
            if (lst[i].compareTo(lst[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        int lineIdx = 0;
        int max = size * (size - 1) * (size - 2) / 6;
        if (max < 0) {
            max = Integer.MAX_VALUE;
        }
        LineSegment[] tmp = new LineSegment[max];

        for (int i = 0; i < size - 3; i++) {
            for (int j = i + 1; j < size - 2; j++) {
                for (int k = j + 1; k < size - 1; k++) {
                    for (int m = k + 1; m < size; m++) {
                        if (check4points(lst[i], lst[j], lst[k], lst[m])) {
                            tmp[lineIdx] = new LineSegment(lst[i], lst[m]);
                            lineIdx++;
                        }
                    }
                }
            }
        }

        lines = new LineSegment[lineIdx];
        for (int i = 0; i < lineIdx; i++) {
            lines[i] = tmp[i];
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lines.clone();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}