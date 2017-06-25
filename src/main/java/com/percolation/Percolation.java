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
    private int numberOfOpenSites;

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
    private boolean grid[][];


    /**
     *
     * @param n of elements passed as argument
     * @throws IllegalArgumentException Illigal argument exception
     */
    public Percolation(final int n) throws IllegalArgumentException {
        this.n = n;
        this.grid = new boolean[n][n];

        int nsquare = (int) Math.pow(n, 2);
        time = nsquare;

        numberOfCalls = 0;
        virtualTopSite = nsquare;
        virtualBottomSite = nsquare + 1;

        weightedQuickUnionUF = new WeightedQuickUnionUF(nsquare + 2);

        setVirtualSites(n, virtualTopSite, 1);
        setVirtualSites(n, virtualBottomSite, n);

        if (n <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0");
        }
    }

    /**
     *
     * @param n number n
     * @param virtualSite virtual top/bottom site
     * @param coord n or 1 coordinate
     */
    private void setVirtualSites(final int n, final int virtualSite, final int coord) {
        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(virtualSite, xyTo1D(coord, i));
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

        if (!percolates()) {
            if (!isOpen(row, col)) {
                grid[j][i] = true;
                openSites(row, col, convertedCoordinates, n);
            }
        }
    }

    /**
     *
     * @param row row
     * @param col column
     * @param convCoordinates coordinates converted to 1D
     * @param n number n
     */
    private void openSites(final int row, final int col, final int convCoordinates, final int n) {
        validateRow(row - 1, col, convCoordinates, n);
        validateRow(row + 1, col, convCoordinates, n);
        validateColumn(row, col - 1, convCoordinates, n);
        validateColumn(row, col + 1, convCoordinates, n);
    }

    private void validateRow(final int row, final int col, final int convCoordinates, final int n) {
        if (positionIsInBounds(row) && row > 0 && row < n && isOpen(row, col)) {
            unionAction(row, col, convCoordinates);
        }
    }

    private void validateColumn(final int row, final int col, final int convCoordinates, final int n) {
        if (positionIsInBounds(col) && col > 0 && col < n && isOpen(row, col)) {
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
            throw new IndexOutOfBoundsException("Arguments should be > 0 and < n");
        }

        return pos - 1;
    }
}
