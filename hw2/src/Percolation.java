import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int[] dx = {1, -1, 0, 0};
    private final int[] dy = {0, 0, 1, -1};

    private final int[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int N;

    private int openSites;


    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        grid = new int[N][N];
        uf = new WeightedQuickUnionUF(N * N);
        this.N = N;
        openSites = 0;
    }

    private int index(int row, int cow) {
        return row * N + cow;
    }

    private void checkValidIndex(int row, int col) {
        if (row < 0 || row >= N) {
            throw new IndexOutOfBoundsException();
        }
        if (col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        checkValidIndex(row, col);
        if (grid[row][col] == 0) {
            openSites++;
            grid[row][col] = 1;
            for (int i = 0; i < 4; i++) {
                if (row + dx[i] >= N || row + dx[i] < 0) {
                    continue;
                } else if (col + dy[i] >= N || col + dy[i] < 0) {
                    continue;
                }
                if (isOpen(row + dx[i], col + dy[i])) {
                    uf.union(index(row, col), index(row + dx[i], col + dy[i]));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        checkValidIndex(row, col);
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        checkValidIndex(row, col);
        for (int i = 0; i < N; i++) {
            if (isOpen(0, i)) {
                if (uf.connected(index(0, i), index(row, col))) {
                    return true;
                }
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        for (int i = 0; i < N; i++) {
            if (isFull(N - 1, i)) {
                return true;
            }
        }
        return false;
    }

}
