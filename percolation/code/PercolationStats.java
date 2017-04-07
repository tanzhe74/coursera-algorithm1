import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.*;
/**
 * @author zhe tan
 * @start-date 04/06/2017
 * @version 1.0
 * 
 * This class creates a data type Percolation with the following API:
 * 
 * public class PercolationStats{
 *     public PercolationStats(int n, int trials)
 *     public double mean()
 *     public double stddev()
 *     public double confidenceLo()
 *     public double confidenceHi()
 *    
 *     public static void main(String[] args)
 * }
 */

public class PercolationStats{

    private int n;
    private int trials;
    private double[] opennumber;
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        opennumber = new double[trials];
        for(int i = 0; i<trials; i++) {
            int number = 0;
            Percolation p = new Percolation(n);
            while(!p.percolates() && number < n* n) {
                int random = StdRandom.uniform(1, n * n + 1);
                int row = 0, col = 0;
                if(random % n == 0) {
                    row = random / n;
                    col = n;
                }
                else {
                    row = random / n + 1;
                    col = random % n;
                }
                while (p.isOpen(row, col)) {
                    random = StdRandom.uniform(1, n * n + 1);
                    if(random % n == 0) {
                        row = random / n;
                        col = n;
                    }
                    else {
                        row = random / n + 1;
                        col = random % n;
                    }
                }
                number ++;
                p.open(row, col);
            }
            opennumber[i] = (double)number/(n *n);
           
        }
    }
    
    public double mean(){
        return StdStats.mean(opennumber);
    }
    
    public double stddev() {
        return StdStats.stddev(opennumber);
    }
    
    public double confidenceLo(){
        return StdStats.mean(opennumber) - 1.96 * StdStats.stddev(opennumber) / Math.sqrt(trials);
    }
    
    public double confidenceHi() {
        return StdStats.mean(opennumber) + 1.96 * StdStats.stddev(opennumber) / Math.sqrt(trials);
    }
    
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(2, 10000);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLo());
        System.out.println(ps.confidenceHi());
    }
}