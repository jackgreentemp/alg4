import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF mWeightedQuickUnionUF;//带top虚拟节点的数据结构
    private WeightedQuickUnionUF mVirtualWeightedQuickUnionUF;//带top和bottom虚拟节点的数据结构
    private int arraySize;
    private int indexOfOpen = 0;
    private int indexOfClose;
    private int[] status; //n*n
    private int FLAGCLOSE = 0;
    private int FLAGOPEN = 1;
    private int size;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if(n<=0)
            throw new IllegalArgumentException();
        
        size = n;
        arraySize = n*n + 2;
        indexOfClose = arraySize - 1;
        
        status = new int[n*n];
        mWeightedQuickUnionUF = new WeightedQuickUnionUF(arraySize);
        mVirtualWeightedQuickUnionUF = new WeightedQuickUnionUF(arraySize);
        
        for(int i=0; i < n*n; i++) {
            status[i] = FLAGCLOSE;
        }
        
        for(int i=1; i <= n; i++) {
            //mVirtualWeightedQuickUnionUF.union(i, indexOfOpen);
        }
        
        for(int i=(arraySize-n-1); i <= (arraySize-2); i++){
            //mVirtualWeightedQuickUnionUF.union(i, indexOfClose);
        }
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        
        if(row <=0 || row>size)
            throw new IllegalArgumentException("row is not between 0 and " + (size-1));
        
        if(col <=0 || col>size)
            throw new IllegalArgumentException("col is not between 0 and " + (size-1));
        
        int index = (row-1)*size+col;
        status[index-1] = FLAGOPEN;
        // System.out.println("row = " + row + ", col = " + col + ", status = " + status[index-1]);
        // System.out.println("numberOfOpenSites = " + numberOfOpenSites());
        
        //union
        int upIndex = (row-2)*size + col;
        int downIndex = (row)*size + col;
        
        //最左边一列，leftIndex=-1
        int leftIndex = ((row-1)*size + col-1)%size == 0 ? -1 : (row-1)*size + col-1;
        
        //最右边一列，rigthIndex=-1
        int rigthIndex = ((row-1)*size + col+1)%size == 1 ? -1 : (row-1)*size + col+1;
        
        if(upIndex>=1 && upIndex <= size*size){
            if(isOpen(row-1, col)) {
                mWeightedQuickUnionUF.union(index, upIndex);
                mVirtualWeightedQuickUnionUF.union(index, upIndex);
            }
        } else {
            mWeightedQuickUnionUF.union(index, indexOfOpen);
            mVirtualWeightedQuickUnionUF.union(index, indexOfOpen);
        }
        
        if(leftIndex>=1 && leftIndex <= size*size){
            if(isOpen(row, col-1)) {
                mWeightedQuickUnionUF.union(index, leftIndex);
                mVirtualWeightedQuickUnionUF.union(index, leftIndex);
            }
        }
        
        if(rigthIndex>=1 && rigthIndex <= size*size){
            if(isOpen(row, col+1)) {
                mWeightedQuickUnionUF.union(index, rigthIndex);
                mVirtualWeightedQuickUnionUF.union(index, rigthIndex);
            }
        }
        
        if(downIndex>=1 && downIndex <= size*size){
            if(isOpen(row+1, col)) {
                mWeightedQuickUnionUF.union(index, downIndex);
                mVirtualWeightedQuickUnionUF.union(index, downIndex);
            }
        } else {
            mVirtualWeightedQuickUnionUF.union(index, indexOfClose);
        }
    }
    
    public boolean isOpen(int row, int col) {
        
        if(row <=0 || row>size)
            throw new IllegalArgumentException("row is not between 0 and " + size);
        
        if(col <=0 || col>size)
            throw new IllegalArgumentException("col="+ col+", col is not between 0 and " + size);
        
        int index = (row-1) * size + col;
        return status[index-1] == FLAGOPEN;
    }
    
    public boolean isFull(int row, int col) {
        
        if(row <=0 || row>size)
            throw new IllegalArgumentException("row is not between 0 and " + size);
        
        if(col <=0 || col>size)
            throw new IllegalArgumentException("col is not between 0 and " + size);
        
        
        int index = (row-1) * size + col;
        return mWeightedQuickUnionUF.connected(index, indexOfOpen);
    }
    
    public int numberOfOpenSites() {
        int sum = 0;
        for(int i=0; i<status.length; i++){
            sum += status[i];
        }
        return sum;
    }
    
    public boolean percolates() {
        // return mWeightedQuickUnionUF.connected(indexOfClose, indexOfOpen);
        return mVirtualWeightedQuickUnionUF.connected(indexOfClose, indexOfOpen);
    }
    
    public static void main(String[] args){
        int n = 5;
        Percolation mPercolation = new Percolation(n);
        
        for(int i=1; i<=5; i++){
            System.out.println("row = " + i + ", col = 1, isOpen = "+mPercolation.isOpen(i,1));
            System.out.println("row = " + i + ", col = 1, isFull = "+mPercolation.isFull(i,1));
            mPercolation.open(i,1);
            System.out.println("row = " + i + ", col = 1, isOpen = " + mPercolation.isOpen(i, 1) );
            System.out.println("row = " + i + ", col = 1, isFull = " + mPercolation.isFull(i,1) );
            System.out.println("is Percolation = " + mPercolation.percolates());
        }
        
        mPercolation = new Percolation(n);
        
        for(int i=1; i<=5; i++){
            System.out.println("row = " + 1 + ", col = " + i + ", isOpen = "+mPercolation.isOpen(1,i));
            System.out.println("row = " + 1 + ", col = " + i + ", isFull = "+mPercolation.isFull(1,i));
            mPercolation.open(1,i);
            System.out.println("row = " + 1 + ", col = " + i + ", isOpen = " + mPercolation.isOpen(1,i) );
            System.out.println("row = " + 1 + ", col = " + i + ", isFull = " + mPercolation.isFull(1,i) );
            System.out.println("is Percolation = " + mPercolation.percolates());
        }
        
        mPercolation = new Percolation(n);
        
        for(int i=1; i<=5; i++){
            System.out.println("row = " + i + ", col = " + i + ", isOpen = "+mPercolation.isOpen(i,i));
            System.out.println("row = " + i + ", col = " + i + ", isFull = "+mPercolation.isFull(i,i));
            mPercolation.open(i,i);
            System.out.println("row = " + i + ", col = " + i + ", isOpen = " + mPercolation.isOpen(i,i) );
            System.out.println("row = " + i + ", col = " + i + ", isFull = " + mPercolation.isFull(i,i) );
            System.out.println("is Percolation = " + mPercolation.percolates());
            System.out.println("===================================");
        }
        
        mPercolation = new Percolation(2);
        
        for(int i=1; i<=2; i++){
            System.out.println("row = " + i + ", col = 2, isOpen = "+mPercolation.isOpen(i,2));
            System.out.println("row = " + i + ", col = 2, isFull = "+mPercolation.isFull(i,2));
            mPercolation.open(i, 2);
            System.out.println("row = " + i + ", col = 2, isOpen = " + mPercolation.isOpen(i, 2) );
            System.out.println("row = " + i + ", col = 2, isFull = " + mPercolation.isFull(i,2) );
            System.out.println("is Percolation = " + mPercolation.percolates());
        }
        
        mPercolation = new Percolation(2);
        
        mPercolation.open(1, 1);
        System.out.println("row = " + 1 + ", col = 1, isOpen = " + mPercolation.isOpen(1, 1) );
        System.out.println("row = " + 1 + ", col = 1, isFull = " + mPercolation.isFull(1, 1) );
        System.out.println("is Percolation = " + mPercolation.percolates());
        mPercolation.open(2, 2);
        System.out.println("row = " + 2 + ", col = 2, isOpen = " + mPercolation.isOpen(2, 2) );
        System.out.println("row = " + 2 + ", col = 2, isFull = " + mPercolation.isFull(2, 2) );
        System.out.println("is Percolation = " + mPercolation.percolates());
        mPercolation.open(1, 2);
        System.out.println("row = " + 1 + ", col = 2, isOpen = " + mPercolation.isOpen(1, 2) );
        System.out.println("row = " + 1 + ", col = 2, isFull = " + mPercolation.isFull(1, 2) );
        System.out.println("is Percolation = " + mPercolation.percolates());
        
        System.out.println("row = " + 2 + ", col = 1, isOpen = " + mPercolation.isOpen(2, 1) );
        System.out.println("row = " + 2 + ", col = 1, isFull = " + mPercolation.isFull(2, 1) );
    }
    
}