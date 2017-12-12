import edu.princeton.cs.algs4.*;

public class PercolationStats {

    private Percolation mPercolation;
    //private int size;
    private int trials;
    private double[] rate;

    public PercolationStats(int n, int t) {
        if(n <= 0 || t <= 0)
            throw new IllegalArgumentException("n or trials should > 0");

        //size = n;
        trials = t;
        rate = new double[trials];

        for(int i=0; i<trials; i++) {

            int count = 0;
            mPercolation = new Percolation(n);

            while (!mPercolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if(!mPercolation.isOpen(row, col)) {
                    mPercolation.open(row, col);
                    count++;
                }
            }
//            StdOut.println(i + " turn, count is " + count);
//            StdOut.println("n = " + n);
            rate[i] = (double) count/(n*n);
//            StdOut.println(i + " turn, rate is " + rate[i]);
        }

    }

    public double mean() {
        return StdStats.mean(rate);
    }

    public double stddev() {
        return StdStats.stddev(rate);
    }

    public double confidenceLo() {
        // StdOut.println("trials = " + trials);
        // StdOut.println("Math.sqrt(trials) = " + Math.sqrt((double)trials));
        return (mean() - 1.96*stddev()/Math.sqrt(trials));
    }

    public double confidenceHi() {
        return (mean() + 1.96*stddev()/Math.sqrt(trials));
    }

    public static void main(String[] args){
        // int n = StdIn.readInt();
        // int t = StdIn.readInt();

        int n = 200;
        int t = 100;

        StdOut.println("n = " + n + ", t = " + t);

        PercolationStats mPercolationStats = new PercolationStats(n, t);

        StdOut.println("mean = " + mPercolationStats.mean());
        StdOut.println("stddev = " + mPercolationStats.stddev());
        StdOut.println("95% confidence interval  = [" + mPercolationStats.confidenceLo() + ", " + mPercolationStats.confidenceHi() + "]");
    }
}
