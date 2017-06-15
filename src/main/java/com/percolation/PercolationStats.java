package com.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Percolation stats.
 */
public class PercolationStats {
    private double[] meanArgs;

    /**
     *
     * @param n number n
     * @param trials amount of trials
     */
    public PercolationStats(final int n, final int trials) {
        int numberOfCells = (int) Math.pow(n, 2);
        meanArgs = new double[trials];

        for (int j=0; j < trials; j++) {
            Percolation percolation = new Percolation(n);

            for (int i = 0; i < numberOfCells; i++) {
                int r = StdRandom.uniform(1, n+1);
                int c = StdRandom.uniform(1, n+1);

            if (!percolation.percolates() && !percolation.isOpen(r, c)) {
                    percolation.open(r,c);
                }
            }
            meanArgs[j] = (double) percolation.numberOfOpenSites()/ numberOfCells;
        }

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments " + n + " or " + trials + " cannot be smaller than 0");
        }
    }

    /**
     *
     * @return mean
     */
    public double mean() {
        return StdStats.mean(meanArgs);
    }
    /**
     *
     * @return standard deviation
     */
    public double stddev() {
        return StdStats.stddev(meanArgs);
    }

    /**
     *
     * @return low level of confidence that the system percolates
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev())/Math.sqrt(meanArgs.length);
    }

    /**
     *
     * @return high level of confidence that the system percolates
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev())/Math.sqrt(meanArgs.length);
    }


    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.printf("mean                     " + percolationStats.mean());
        StdOut.printf("stddev                   " + percolationStats.stddev());
        StdOut.printf("95% confidence interval  " + "[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }

}
