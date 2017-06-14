import com.percolation.Percolation;
import com.percolation.PercolationStats;

public class Main {

    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);

//        percolation.open(1,2);
//
//        percolation.open(2,3);
//        percolation.open(1,3);
//        percolation.open(3,4);
//        percolation.open(3,3);
//
//        percolation.open(4,4);
//
//        percolation.open(1,1);
//
//        percolation.open(1,4);
//        percolation.open(4,3);
//        percolation.open(3,3);
//
//        percolation.open(4,4);
//
        percolation.open(1,1);
        PercolationStats percolationStats = new PercolationStats(4, 10);

//        System.out.println(percolation.percolates() + " perc 3");
//        System.out.println(percolation.numberOfOpenSites() + " percolation open sites");

//        percolationStats.open();
//        percolationStats.open();

        System.out.println(percolationStats.getOpenSites() + " open sites...");

    };
}
