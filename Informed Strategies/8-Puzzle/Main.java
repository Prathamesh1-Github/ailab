import java.util.*;

public class Main {
    static final int[][] GOAL_STATE = {
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}
    };

    static class PuzzleState {
        int[][] board;
        int cost;
        int heuristic;
        PuzzleState parent;

        PuzzleState(int[][] b, int c, int h, PuzzleState p) {
            board = new int[3][3];
            for (int i = 0; i < 3; i++) {
                board[i] = Arrays.copyOf(b[i], 3);
            }
            cost = c;
            heuristic = h;
            parent = p;
        }

        int calculateHeuristic() {
            int h = 0;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (board[i][j] != GOAL_STATE[i][j]) {
                        h += 1;
                    }
                }
            }
            return h;
        }

        boolean isGoal() {
            return Arrays.deepEquals(board, GOAL_STATE);
        }

        List<PuzzleState> getNextStates() {
            List<PuzzleState> nextStates = new ArrayList<>();
            int[] dx = {1, -1, 0, 0};
            int[] dy = {0, 0, 1, -1};
            int x = 0, y = 0;

            // Find the position of the blank tile (0)
            outer:
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (board[i][j] == 0) {
                        x = i;
                        y = j;
                        break outer;
                    }
                }
            }

            // Generate next states by moving tiles into the blank space
            for (int i = 0; i < 4; ++i) {
                int newX = x + dx[i];
                int newY = y + dy[i];
                if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                    int[][] newBoard = new int[3][3];
                    for (int k = 0; k < 3; k++) {
                        newBoard[k] = Arrays.copyOf(board[k], 3);
                    }
                    int temp = newBoard[x][y];
                    newBoard[x][y] = newBoard[newX][newY];
                    newBoard[newX][newY] = temp;
                    PuzzleState nextState = new PuzzleState(newBoard, cost + 1, calculateHeuristic(), this);
                    nextStates.add(nextState);
                }
            }

            return nextStates;
        }

        boolean equals(PuzzleState other) {
            return Arrays.deepEquals(board, other.board);
        }

        static class Hash {
            int hashCode(PuzzleState state) {
                int hashVal = 0;
                for (int[] row : state.board) {
                    for (int val : row) {
                        hashVal ^= Integer.hashCode(val) + 0x9e3779b9 + (hashVal << 6) + (hashVal >>> 2);
                    }
                }
                return hashVal;
            }
        }
    }

    static List<int[][]> aStar(int[][] initialState) {
        PuzzleState initial = new PuzzleState(initialState, 0, 0, null);
        initial.heuristic = initial.calculateHeuristic();

        PriorityQueue<PuzzleState> frontier = new PriorityQueue<>(Comparator.comparingInt(
                (PuzzleState a) -> a.cost + a.heuristic));
        frontier.add(initial);

        Set<PuzzleState> visited = new HashSet<>();

        while (!frontier.isEmpty()) {
            PuzzleState current = frontier.poll();

            if (current.isGoal()) {
                // Reconstruct the path
                List<int[][]> path = new ArrayList<>();
                while (current != null) {
                    path.add(current.board);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            visited.add(current);

            // Generate next states
            List<PuzzleState> nextStates = current.getNextStates();
            for (PuzzleState nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    frontier.add(nextState);
                }
            }
        }

        return Collections.emptyList(); // No solution found
    }

    static void printPuzzle(int[][] puzzle) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println(); // Add newline after each row
        }
        System.out.println(); // Add extra newline after the entire puzzle
    }

    public static void main(String[] args) {
        int[][] initialState = {
                {2, 8, 3},
                {1, 6, 4},
                {7, 0, 5}};

        System.out.println("Initial State:");
        printPuzzle(initialState);

        List<int[][]> finalState = aStar(initialState);

        System.out.println("Solution Path:");
        for (int[][] state : finalState) {
            printPuzzle(state);
        }
    }
}
