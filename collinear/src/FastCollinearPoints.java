import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] lines;

    // private class Line implements Comparable<Line> {
    // private final Point start;
    // private final Point end;
    // private final double slope;
    // private final double xCord;
    // private final double yCord;

    // public Line(Point p1, Point p2) {
    // if (p1.compareTo(p2) == 0) {
    // throw new IllegalArgumentException();
    // } else if (p1.compareTo(p2) > 0) {
    // start = p2;
    // end = p1;
    // } else {
    // start = p1;
    // end = p2;
    // }
    // slope = start.slopeTo(end);
    // }

    // private double xCord(){
    // if (slope == 0)
    // }

    // public int compareTo(Line that) {
    // if (slope > that.slope) {
    // return 1;
    // } else if (slope == that.slope) {
    // return 0;
    // } else {
    // return -1;
    // }
    // }
    // }

    private boolean checkInLine(Comparator<Point>[] origin, int index, Point start, Point end) {
        for (int i = 0; i < index; i++) {
            Comparator<Point> p = origin[i];
            if (p.compare(start, end) == 0) {
                return true;
            }
        }
        return false;
    }

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        int size = points.length;
        Point[] lst = new Point[size];
        for (int i = 0; i < size; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
            lst[i] = points[i];
        }

        // Arrays.sort(lst);

        int lineIdx = 0;
        int max = size * (size - 1) * (size - 2) / 6;
        if (max < 0) {
            max = Integer.MAX_VALUE;
        }
        LineSegment[] tmp = new LineSegment[max];
        Comparator<Point>[] pList = (Comparator<Point>[]) new Object[max];

        for (int i = 0; i < size - 3; i++) {
            int idx = 0;
            Comparator<Point> comp = lst[i].slopeOrder();
            Arrays.sort(lst, i + 1, size, comp);
            int j = i + 1;
            Point end = lst[i];
            Point start = lst[i];
            while (j < size - 1) {
                int ret = comp.compare(lst[j], lst[j + 1]);

                if (ret != 0) {
                    if (idx > 2) {
                        if (!checkInLine(pList, lineIdx, lst[i], lst[j])) {
                            if (end.compareTo(lst[j]) < 0) {
                                end = lst[j];
                            } else if (start.compareTo(lst[j]) > 0) {
                                start = lst[j];
                            }
                            pList[lineIdx] = comp;
                            tmp[lineIdx++] = new LineSegment(start, end);
                        }
                    }
                    idx = 1;
                    end = lst[i];
                    start = lst[i];
                } else {
                    if (end.compareTo(lst[j]) < 0) {
                        end = lst[j];
                    } else if (start.compareTo(lst[j]) > 0) {
                        start = lst[j];
                    }
                    idx++;
                }
                j++;
            }
            if (idx > 1) {
                if (!checkInLine(pList, lineIdx, lst[i], lst[j])) {
                    if (end.compareTo(lst[j]) < 0) {
                        end = lst[j];
                    } else if (start.compareTo(lst[j]) > 0) {
                        start = lst[j];
                    }
                    pList[lineIdx] = comp;
                    tmp[lineIdx++] = new LineSegment(start, end);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}