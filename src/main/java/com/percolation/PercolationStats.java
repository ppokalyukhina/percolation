import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Percolation stats.
 */
public class PercolationStats {

    /**
     * arguments for mean and stddev methods
     */
    private double[] meanArgs;

    /**
     * stored mean value
     */
    private double meanValue;

    /**
     * standard deviation value
     */
    private double stdDev;

    /**
     *
     * @param n number n
     * @param trials amount of trials
     */
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments " + n + " or " + trials + " cannot be smaller than 0");
        }

        int numberOfCells = (int) Math.pow(n, 2);
        meanArgs = new double[trials];

        for (int j = 0; j < trials; j++) {
            Percolation percolation = new Percolation(n);

            for (int i = 0; i < numberOfCells; i++) {
                int r = StdRandom.uniform(1, n + 1);
                int c = StdRandom.uniform(1, n + 1);

            if (!percolation.percolates() && !percolation.isOpen(r, c)) {
                    percolation.open(r, c);
                }
            }
            meanArgs[j] = (double) percolation.numberOfOpenSites()/ numberOfCells;
        }

       this.meanValue = StdStats.mean(meanArgs);
       this.stdDev = StdStats.stddev(meanArgs);
    }

    /**
     *
     * @return double mean
     */
    public double mean() {
        return this.meanValue;
    }
    /**
     *
     * @return double standard deviation
     */
    public double stddev() {
        return this.stdDev;
    }

    /**
     *
     * @return double low level of confidence that the system percolates
     */
    public double confidenceLo() {
        return this.meanValue - (1.96 * this.stdDev) / Math.sqrt(this.meanArgs.length);
    }

    /**
     *
     * @return double high level of confidence that the system percolates
     */
    public double confidenceHi() {
        return this.meanValue + (1.96 * this.stdDev) / Math.sqrt(this.meanArgs.length);
    }

    public static void main(final String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.print("mean                     " + percolationStats.mean() + "\n");
        StdOut.print("stddev                   " + percolationStats.stddev() + "\n");
        StdOut.print("95% confidence interval  [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}
