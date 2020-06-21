import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final LineSegment[] lines;

    private boolean checkExist(double[] lst, int idx, Point p1, Point p2) {
        double s = p1.slopeTo(p2);
        if (s == Double.NEGATIVE_INFINITY) {
            throw new IllegalArgumentException();
        }
        int max = idx - 1;
        int min = 0;
        while (min <= max) {
            int mid = (max + min) / 2;
            if (s == lst[mid]) {
                return true;
            } else if (s < lst[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private LineSegment[] expandArray(LineSegment[] lst) {
        int size = lst.length;
        LineSegment[] arr = new LineSegment[size * 2];
        for (int i = 0; i < size; i++) {
            arr[i] = lst[i];
        }
        return arr;
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

        Arrays.sort(lst);
        for (int i = 0; i < size - 1; i++) {
            if (lst[i].compareTo(lst[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        int lineIdx = 0;
        LineSegment[] tmp = new LineSegment[4];
        Point[] last = new Point[size];
        int lastIdx = 0;
        double[] exist = new double[size];

        for (int i = 0; i < size - 3; i++) {
            Comparator<Point> comp = lst[i].slopeOrder();
            Arrays.sort(lst, i + 1, size, comp);

            int idx = 1;

            int j = i + 1;
            for (int m = 0; m < lastIdx; m++) {
                exist[m] = lst[i].slopeTo(last[m]);
            }
            Arrays.sort(exist, 0, lastIdx);
            last[lastIdx] = lst[i];
            lastIdx++;
            Point end = lst[i];
            Point start = lst[i];
            boolean skip = false;

            while (j < size - 1) {
                int ret = comp.compare(lst[j], lst[j + 1]);
                if (ret == 0 && !skip) {
                    if (checkExist(exist, lastIdx - 1, lst[i], lst[j])) {
                        skip = true;
                    }
                    if (!skip) {
                        if (end.compareTo(lst[j]) < 0) {
                            end = lst[j];
                        } else if (start.compareTo(lst[j]) > 0) {
                            start = lst[j];
                        }
                        idx++;
                    }
                } else {
                    skip = false;
                    if (idx > 2) {
                        if (end.compareTo(lst[j]) < 0) {
                            end = lst[j];
                        } else if (start.compareTo(lst[j]) > 0) {
                            start = lst[j];
                        }
                        tmp[lineIdx] = new LineSegment(start, end);
                        lineIdx++;
                        if (lineIdx >= tmp.length) {
                            tmp = expandArray(tmp);
                        }
                    }
                    idx = 1;
                    end = lst[i];
                    start = lst[i];
                }
                j++;
            }
            if (idx > 2) {
                if (end.compareTo(lst[j]) < 0) {
                    end = lst[j];
                } else if (start.compareTo(lst[j]) > 0) {
                    start = lst[j];
                }
                tmp[lineIdx] = new LineSegment(start, end);
                lineIdx++;
                if (lineIdx >= tmp.length) {
                    tmp = expandArray(tmp);
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