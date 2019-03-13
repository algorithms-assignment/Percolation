import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] system;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF unionFind;
    private int n;

    public Percolation(int N)
    {
        if (N <= 0) {
            throw new IllegalArgumentException("Given N <= 0");
        }
        system = new boolean[N][N];
        unionFind     = new WeightedQuickUnionUF(N*N + 2);
        virtualTop    = N*N;
        virtualBottom = N*N + 1;
        n    = N;
    }

    public void open(int i, int j)
    {
        if (checkIndex(i, j)) {
            system[i-1][j-1] = true;
            // It opens and union with previously opened sites in the array
            // 6 options
            /**
             *  For the very top and bottom sites
                 *  1 - the very first row --- open it and connect it with virtual top
                 *  2 - the very last row ---- open it and connect it with virtual bottom
             *  For the middle sites
                 *  3 - connect it with its right if the the site was already opened
                 *  4 - connect it with its left if the the site was already opened
                 *  5 - connect it with its above if the the site was already opened
                 *  6 - connect it with its below if the the site was already opened
             *
             * */
            if (i == 1) { //Very first which is always open
                unionFind.union(j-1, virtualTop);
            }
            if (i == n) { //Very bottom which is always open
                unionFind.union((i-1)*n+j-1, virtualBottom);
            }
            if (i > 1   && isOpen(i-1, j)) { //The size left of it
                unionFind.union((i-1)*n+j-1, (i-2)*n+j-1);
            }
            if (i < n && isOpen(i+1, j)) { //The site right of it
                unionFind.union((i-1)*n+j-1, i*n+j-1);
            }
            if (j > 1   && isOpen(i, j-1)) { //The site above it
                unionFind.union((i-1)*n+j-1, (i-1)*n+j-2);
            }
            if (j < n && isOpen(i, j+1)) { //The site below it
                unionFind.union((i-1)*n+j-1, (i-1)*n+j);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isOpen(int i, int j)
    {
        if (checkIndex(i, j)) {
            return system[i-1][j-1];
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean isFull(int i, int j)
    {
        if (checkIndex(i, j)) {
            return unionFind.connected((i-1)*n+j-1, virtualTop);
        }
        throw new IndexOutOfBoundsException();
    }

    public boolean percolates()
    {
        return unionFind.connected(virtualTop, virtualBottom);
    }

    private boolean checkIndex(int i, int j)
    {
        if (i < 1 || i > n || j < 1 || j > n) return false;
        return true;
    }
}