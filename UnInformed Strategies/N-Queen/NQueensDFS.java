import java.util.ArrayList;
import java.util.List;

public class NQueensDFS {

    private int size;

    public NQueensDFS(int size) {
        this.size = size;
    }

    public List<List<int[]>> solve() {
        List<List<int[]>> solutions = new ArrayList<>();
        solveRecursive(new int[0], solutions);
        return solutions;
    }

    private void solveRecursive(int[] queens, List<List<int[]>> solutions) {
        if (queens.length == size) {
            List<int[]> solution = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                solution.add(new int[]{i, queens[i]});
            }
            solutions.add(solution);
            return;
        }

        for (int col = 0; col < size; col++) {
            if (!conflict(queens, col)) {
                int[] newQueens = new int[queens.length + 1];
                System.arraycopy(queens, 0, newQueens, 0, queens.length);
                newQueens[queens.length] = col;
                solveRecursive(newQueens, solutions);
            }
        }
    }

    private boolean conflict(int[] queens, int newCol) {
        for (int i = 0; i < queens.length; i++) {
            int rowDiff = Math.abs(queens[i] - newCol);
            if (queens[i] == newCol || rowDiff == queens.length - i)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        int size = 5;
        NQueensDFS nQueensDFS = new NQueensDFS(size);
        List<List<int[]>> solutions = nQueensDFS.solve();
        for (List<int[]> solution : solutions) {
            for (int[] queen : solution) {
                System.out.print("| ");
                for (int j = 0; j < size; j++) {
                    if (queen[1] == j) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println("|");
            }
            System.out.println();
        }
    }
}
