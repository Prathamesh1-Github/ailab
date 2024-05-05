import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NQueensBFS {

    private int size;

    public NQueensBFS(int size) {
        this.size = size;
    }

    public List<List<int[]>> solve() {
        List<List<int[]>> solutions = new ArrayList<>();
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[0]);

        while (!queue.isEmpty()) {
            int[] solution = queue.poll();
            if (conflict(solution))
                continue;

            int row = solution.length;
            if (row == size) {
                solutions.add(getSolutionMatrix(solution));
                continue;
            }

            for (int col = 0; col < size; col++) {
                int[] candidate = new int[solution.length + 1];
                System.arraycopy(solution, 0, candidate, 0, solution.length);
                candidate[solution.length] = col;
                queue.offer(candidate);
            }
        }

        return solutions;
    }

    private List<int[]> getSolutionMatrix(int[] queens) {
        List<int[]> matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int[] row = new int[size];
            for (int j = 0; j < size; j++) {
                if (queens[i] == j) {
                    row[j] = 1;
                } else {
                    row[j] = 0;
                }
            }
            matrix.add(row);
        }
        return matrix;
    }

    private boolean conflict(int[] queens) {
        for (int i = 1; i < queens.length; i++) {
            for (int j = 0; j < i; j++) {
                if (queens[i] == queens[j] || Math.abs(queens[i] - queens[j]) == i - j)
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int size = 5;
        NQueensBFS nQueensBFS = new NQueensBFS(size);
        List<List<int[]>> solutions = nQueensBFS.solve();
        for (List<int[]> solution : solutions) {
            System.out.println("Chessboard Matrix:");
            for (int[] row : solution) {
                for (int cell : row) {
                    if (cell == 1) {
                        System.out.print("Q ");
                    }
                    else{
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}

