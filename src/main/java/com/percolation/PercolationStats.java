package com.percolation;

import com.percolation.Percolation;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation stats.
 */
public class PercolationStats {
    private boolean[][] grid;
    private final int numberOfCells;
    private double threshold = 0.0;

    private int numberOfOpenSites;

    private int[] meanArgs;
    private Percolation percolation;
    private int trials;

    /**
     *
     * @param n n.
     * @param trials amount of trials
     */
    public PercolationStats(final int n, final int trials) {
        this.trials = trials;
        numberOfCells = (int) Math.pow(n, 2);
        this.percolation = percolation;
        percolation = new Percolation(n);
//        meanArgs = new int[trials];
//
//        for (int i = 0; i < trials; i++) {
//            meanArgs[i] = i;
//        }

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments " + n + " or " + trials + " cannot be smaller than 0");
        }

        grid = new boolean[n][n];
    }

    /**
     *
     * @return
     */
    public double mean() {
        meanArgs = new int[trials];

        for (int i = 0; i < trials; i++) {
            meanArgs[i] = i;
        }
        return StdStats.mean(meanArgs);
    }


    public int getOpenSites() {
        return percolation.numberOfOpenSites();
    }

    /**
     *
     * @return
     */
    public double stddev() {
        return StdStats.stddev(meanArgs);
    }

    /**
     *
     * @return
     */
    public double confidenceLo() {

        return 0;
    }

    /**
     *
     * @return
     */
    public double confidenceHi() {

        return 0;
    }

    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        PercolationStats percolationStats = new PercolationStats(200, 100);


        percolation.open(1,2);

        System.out.println(percolationStats.mean() + " mean");
//        System.out.println(percolationStats.stddev() + " std dev");
    }
}
