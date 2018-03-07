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
    }

    /**
     *
     * @param row row
     * @param col column
     * @return converted to 1d coordinates
     */
    private int xyTo1D(final int row, final int col) {
        return checkBounds(row) * n + checkBounds(col);
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
        int j = checkBounds(row);
        int i = checkBounds(col);
        final int convertedCoordinates = xyTo1D(row, col);

            if (!isOpen(row, col)) {
                grid[j][i] = true;
                numberOfOpenSites++;

                if (row-1 > 0 && isOpen(row-1, col)) {
                    union(convertedCoordinates, row-1, col);
                }

                if (row < n && isOpen(row+1, col)) {
                    union(convertedCoordinates, row+1, col);
                }

                if (col-1 > 0 && isOpen(row, col-1)) {
                    union(convertedCoordinates, row, col-1);
                }

                if (col < n && isOpen(row, col+1)) {
                    union(convertedCoordinates, row, col+1);
                }
            }

        for (int r = 1; r <= n; r++) {
            if (isFull(n, r)) {
                increaseTimeAndNumberOfCalls();
                weightedQuickUnionUF.union(xyTo1D(n, r), virtualBottomSite);
            }
        }
    }

    /**
     *
     * @param row row
     * @param col column
     * @param convCoordinates coordinates converted to 1D
     */
    private void union(final int convCoordinates, final int row, final int col) {
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
        return grid[checkBounds(row)][checkBounds(col)];
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
     * @param pos row/column
     * @return int validated row/column - 1
     */
    private int checkBounds(final int pos) {
        if (pos <= 0 || pos > n) {
            throw new IllegalArgumentException("Arguments should be > 0 and < n");
        }

        return pos - 1;
    }
}
