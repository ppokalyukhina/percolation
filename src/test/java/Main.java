import com.percolation.Percolation;
import com.percolation.PercolationStats;

public class Main {

    public static void main(String[] args) {
        Percolation percolation = new Percolation(6);

        percolation.open(1,6);
        percolation.open(2,6);
        percolation.open(3,6);
        percolation.open(4,6);
        percolation.open(5,6);
        percolation.open(5,5);

        System.out.println(percolation.isFull(5,6));
        System.out.println(percolation.percolates() + " perc");


    };
}
