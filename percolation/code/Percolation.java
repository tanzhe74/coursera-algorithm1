import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @author zhe tan
 * @start-date 04/06/2017
 * @version 1.0
 * 
 * This class creates a data type Percolation with the following API:
 * 
 * public class Percolation {
 *     public Percolation(int n)
 *     public    void open(int row, int col)
 *     public boolean isOpen(int row, int col)
 *     public boolean isFull(int row, int col)
 *     public     int numberOfOpenSites()
 *     public boolean percolates()
 * 
 *     public static void main(String[] args)
 * }
 */

public class Percolation{
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean isOpensite[][];
    private int grid = 0;
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        grid = n;
        uf = new WeightedQuickUnionUF(n*n + 2);
        uf2 = new WeightedQuickUnionUF(n*n + 1);
        isOpensite = new boolean[n][n];   
    }
    
    public void open(int row, int col) {
        boolean isValid = isValidIndices(row, col);         
        if(isValid == true) {
            isOpensite[row - 1][col - 1] = true;
            int index = xyTo1D(row, col);
            if(row == 1) {
                uf.union(0, index);
                uf2.union(0, index);
            }
            if(row == grid) {
                uf.union(index, grid*grid+1);
                //uf2.union(index, grid*grid+1);
            }
            if(row > 1 && isOpensite[row - 2][col - 1] == true) {
                uf.union(index, index - grid);
                uf2.union(index, index - grid);
            }
            if(row < grid && isOpensite[row][col-1] == true) {
                uf.union(index, index + grid);
                uf2.union(index, index + grid);
            }
            if(col > 1 && isOpensite[row - 1][col - 2] == true) {
                uf.union(index, index - 1);
                uf2.union(index, index - 1);
            }
            if(col < grid && isOpensite[row - 1][col] == true){
                uf.union(index, index + 1);
                uf2.union(index, index + 1);
            }
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }
    
    public boolean isOpen(int row, int col) {
        boolean isValid = isValidIndices(row, col);
        if(isValid == false) {
            throw new IndexOutOfBoundsException();
        }
        if(isValid == true) {
            return isOpensite[row - 1][col - 1];
        }
        return false;
    }
    
    public boolean isFull(int row, int col) {
        if(isValidIndices(row, col)) {        
            int index = xyTo1D(row, col);
            return uf2.connected(0, index);
        }
        else {
            throw new IndexOutOfBoundsException();
        }

    }
    
    public int numberOfOpenSites(){
        int count = 0;
        for(int i = 0; i < isOpensite.length; i++) {
            for(int j = 0; j < isOpensite[0].length; j++) {
                if(isOpensite[i][j] == true) {
                    count ++;
                }
            }
        }
        return count;
    }
    
    public boolean percolates() {
        return uf.connected(0, grid * grid + 1);
    }
    
    private boolean isValidIndices(int row, int col) {
        if(row <= 0 || row > grid || col <= 0 || col > grid) {
            return false;
        }
        return true;
    }
    
    private int xyTo1D(int row, int col) {
        return (row - 1) * grid + col;
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        System.out.println(p.isFull(1,3));
        p.open(1,3);
        System.out.println(p.isFull(2,3));
        p.open(2,3);
        System.out.println(p.isFull(3, 3));
        p.open(3,3);
        System.out.println(p.isFull(3, 1));
        p.open(3,1);
        System.out.println(p.isFull(2, 1));
        p.open(2,1);
        System.out.println(p.isFull(1, 1));
        p.open(1,1);
    }
}

    
