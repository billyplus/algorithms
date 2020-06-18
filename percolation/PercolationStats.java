/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    final private double mean;
    private final double dev;
    private final double low;
    private final double hi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("invalid n or trials...");
        double[] result = new double[trials];
        for (int i = 0; i < trials; i++) {
            double r = percolate(n);
            result[i] = r;
        }
        mean = StdStats.mean(result);
        dev = StdStats.stddev(result);
        low = mean - CONFIDENCE_95 * dev / Math.sqrt(trials);
        hi = mean + CONFIDENCE_95 * dev / Math.sqrt(trials);
    }

    private double percolate(int n) {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            int i = StdRandom.uniform(n) + 1;
            int j = StdRandom.uniform(n) + 1;
            perc.open(i, j);
        }
        return (double) (perc.numberOfOpenSites()) / (double) (n * n);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = 20;
        int t = 300;
        if (args.length >= 1) {
            n = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            t = Integer.parseInt(args[1]);
        }

        PercolationStats percStat = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f\n", percStat.mean());
        StdOut.printf("stddev                  = %f\n", percStat.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]\n", percStat.confidenceLo(),
                      percStat.confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return dev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return low;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return hi;
    }
}
