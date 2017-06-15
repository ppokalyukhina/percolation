package com.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class.
 */
public class Percolation {

    /**
     * Weighted quick union UF - reusing of base methods
     */
    private WeightedQuickUnionUF weightedQuickUnionUF;

    /**
     * number n.
     */
    private int n;

    private int time;
    private int numberOfOpenSites;
    private int numberOfCalls;

    private final int virtualTopSite;
    private final int virtualBottomSite;

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

//        setVirtualSites(n, virtualTopSite, 1);
//        setVirtualSites(n, virtualBottomSite, n);
        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(virtualTopSite, xyTo1D(1, i));
        }

        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(xyTo1D(n, i), virtualBottomSite);
        }

        if (n <= 0) {
            throw new IllegalArgumentException("n should be bigger than 0");
        }
    }

    private void setVirtualSites(int n, int virtualSite, int firstCoordinate) {
        for (int i = 1; i <= n; i++) {
            increaseTimeAndNumberOfCalls();
            weightedQuickUnionUF.union(virtualSite, xyTo1D(firstCoordinate, i));
        }
    }

    private int xyTo1D(int row, int col) {
        return checkBound(row) * n + checkBound(col);
    }

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
        final int convertedCoordinates = xyTo1D(row,col);
        numberOfOpenSites++;

        if (!percolates()) {
            if (!isOpen(row,col)) {
                grid[j][i] = true;
                openSites(row, col, convertedCoordinates, n);
            }
        }
    }

    private void openSites(int row, int col, int convCoordinates, int n) {
        validateRow(row-1, col, convCoordinates, n);
        validateRow(row+1, col, convCoordinates, n);
        validateColumn(row, col-1, convCoordinates, n);
        validateColumn(row, col+1, convCoordinates, n);
    }

    private void validateRow(int row, int col, int convCoordinates, int n) {
        if (positionIsInBounds(row) && row > 0 && row < n && isOpen(row, col)) {
            unionAction(row, col, convCoordinates);
        }
    }

    private void validateColumn(int row, int col, int convCoordinates, int n) {
        if (positionIsInBounds(col) && col > 0 && col < n && isOpen(row, col)) {
           unionAction(row, col, convCoordinates);
        }
    }

    private void unionAction(int row, int col, int convCoordinates) {
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

    private boolean positionIsInBounds(final int pos) {
        return pos >= 0 || pos < n;
    }

    private int checkBound(final int pos) {
        if (!positionIsInBounds(pos)) {
            throw new IndexOutOfBoundsException("Arguments should be > 0 and < n");
        }

        return pos - 1;
    }
}
