package com.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    /**
     * Weighted quick union UF - reusing of base methods.
     */
    private WeightedQuickUnionUF weightedQuickUnionUF;

    /**
     * number n.
     */
    private int n;

    /**
     * int time how long does it take for system to percolate.
     */
    private int time;

    /**
     * total number of open sites at the moment when it is called.
     */
    private int numberOfOpenSites = 0;

    /**
     * how many times was any method from WeightedQuickUnionUF called.
     */
    private int numberOfCalls;

    /**
     * int virtual top site. Used for reducing amount of percolates() calls.
     */
    private final int virtualTopSite;

    /**
     * int virtual bottom site. Used for reducing amount of percolates() calls.
     */
    private final int virtualBottomSite;

    /**
     * n by n grid.
     */
    private boolean[][] grid;


    /**
     *
     * @param n of elements passed as argument
     */
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0");
        }
        int nsquare = (int) Math.pow(n, 2);
        weightedQuickUnionUF = new WeightedQuickUnionUF(nsquare + 2);

        this.n = n;
        this.grid = new boolean[n][n];

        time = nsquare;

        numberOfCalls = 0;
        virtualTopSite = nsquare;
        virtualBottomSite = nsquare + 1;

        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(virtualTopSite, xyTo1D(1, i));
        }

        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(xyTo1D(n, i), virtualBottomSite);
        }
    }

    /**
     *
     * @param row row
     * @param col column
     * @return converted to 1d coordinates
     */
    private int xyTo1D(final int row, final int col) {
        return checkBound(row) * n + checkBound(col);
    }

    /**
     * increases total time and number of calls.
     */
    private void increaseTimeAndNumberOfCalls() {
        numberOfCalls += 1;
        time += numberOfCalls;
    }

    /**
     *
     * @param row row
     * @param col col
     */
    public void open(final int row, final int col) {
        int j = checkBound(row);
        int i = checkBound(col);
        final int convertedCoordinates = xyTo1D(row, col);
        numberOfOpenSites++;
       // System.out.println(isFull(row, col) +  "   ....   is full ?");

            if (!isOpen(row, col)) {
                grid[j][i] = true;
                //openSites(row, col, convertedCoordinates, n);

                if (row-1 > 0 && isOpen(row, col)) {
                    System.out.println(" TTT 1");
                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row-1, col));
                    increaseTimeAndNumberOfCalls();
                }

                if (row+1 < n && isOpen(row+2, col)) {
                    System.out.println(" TTT 2");
                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row+1, col));
                    increaseTimeAndNumberOfCalls();
                }

                if (col-1 > 0 && isOpen(row, col)) {
                    System.out.println(" TTT 3");
                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row, col-1));
                    increaseTimeAndNumberOfCalls();
                }

                if (col+1 < n && isOpen(row, col+2)) {
                    System.out.println(" TTT 4");
                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row, col+1));
                    increaseTimeAndNumberOfCalls();
                }

//                if (positionIsInBounds(row-1) && row-1 > 0 && isOpen(row-1, col)) {
//                    System.out.println(" TTT 1");
//                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row-1, col));
//                    increaseTimeAndNumberOfCalls();
//                }
//
//                if (positionIsInBounds(row+1) && row+1 < n && isOpen(row+1, col)) {
//                    System.out.println(" TTT 2");
//                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row+1, col));
//                    increaseTimeAndNumberOfCalls();
//                }
//
//                if (positionIsInBounds(col-1) && col-1 > 0 && isOpen(row, col-1)) {
//                    System.out.println(" TTT 3");
//                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row, col-1));
//                    increaseTimeAndNumberOfCalls();
//                }
//
//                if (positionIsInBounds(col+1) && col+1 < n && isOpen(row, col+1)) {
//                    System.out.println(" TTT 4");
//                    weightedQuickUnionUF.union(convertedCoordinates, xyTo1D(row, col+1));
//                    increaseTimeAndNumberOfCalls();
//                }
            }
    }

    /**
     *
     * @param row row
     * @param col column
     * @param convCoordinates coordinates converted to 1D
     * @param numberN number n
     */
    private void openSites(final int row, final int col, final int convCoordinates, final int numberN) {
        validateRow(row - 1, col, convCoordinates, numberN);
        validateRow(row + 1, col, convCoordinates, numberN);
        validateColumn(row, col - 1, convCoordinates, numberN);
        validateColumn(row, col + 1, convCoordinates, numberN);
    }

    private void validateRow(final int row, final int col, final int convCoordinates, final int numberN) {
        if (positionIsInBounds(row) && row > 0 && row < numberN && isOpen(row, col)) {
            unionAction(row, col, convCoordinates);
        }
    }

    private void validateColumn(final int row, final int col, final int convCoordinates, final int numberN) {
        if (positionIsInBounds(col) && col > 0 && col < numberN && isOpen(row, col)) {
           unionAction(row, col, convCoordinates);
        }
    }

    private void unionAction(final int row, final int col, final int convCoordinates) {
        weightedQuickUnionUF.union(convCoordinates, xyTo1D(row, col));
        increaseTimeAndNumberOfCalls();
    }

    /**
    *
     * @param row row
     * @param col col
     * @return boolean true if open
     */
    public boolean isOpen(final int row, final int col) {
        return grid[checkBound(row)][checkBound(col)];
    };

    /**
     *
     * @param row row.
     * @param col col
     * @return boolean true if full
     */
    public boolean isFull(final int row, final int col) {
        return isOpen(row, col) && weightedQuickUnionUF.connected(xyTo1D(row, col), virtualTopSite);

    }

    /**
     *
     * @return int number of white spaces
     */
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    };

    /**
     *
     * @return boolean
     */
    public boolean percolates() {
        increaseTimeAndNumberOfCalls();
        return weightedQuickUnionUF.connected(virtualTopSite, virtualBottomSite);
    }

    /**
     *
     * @param pos row / column
     * @return boolean if a value is valid
     */
    private boolean positionIsInBounds(final int pos) {
        return pos >= 0 || pos < n;
    }

    /**
     *
     * @param pos row/column
     * @return int validated row/column - 1
     */
    private int checkBound(final int pos) {
        if (!positionIsInBounds(pos)) {
            throw new IllegalArgumentException("Arguments should be > 0 and < n");
        }

        return pos - 1;
    }
}
