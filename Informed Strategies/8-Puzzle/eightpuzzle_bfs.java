import java.util.*;

public class eightpuzzle_bfs {
    static final int[][] GOAL_STATE = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}
    };

    static int calculateHeuristic(int[][] state) {
        int heuristic = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int value = state[i][j];
                if (value != GOAL_STATE[i][j]) {
                    heuristic += 1;
                }
            }
        }
        return heuristic;
    }

    static class Node {
        int[][] state;
        int heuristic;
        int cost;
        Node parent;

        Node(int[][] state, Node parent) {
            this.state = state;
            this.parent = parent;
            heuristic = calculateHeuristic(state);
            cost = (parent != null) ? parent.cost + 1 : 0;
        }
    }

    static class CompareNode implements Comparator<Node> {
        public int compare(Node a, Node b) {
            return a.heuristic - b.heuristic;
        }
    }

    static Node bestFirstSearch(int[][] initial) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(new CompareNode());
        Set<Node> visited = new HashSet<>();
        Node root = new Node(initial, null);
        frontier.add(root);

        while (!frontier.isEmpty()) {
            Node current = frontier.poll();

            if (Arrays.deepEquals(current.state, GOAL_STATE)) {
                return current;
            }

            visited.add(current);

            int zeroX = 0, zeroY = 0;
            outer:
            for (zeroX = 0; zeroX < current.state.length; zeroX++) {
                for (zeroY = 0; zeroY < current.state[zeroX].length; zeroY++) {
                    if (current.state[zeroX][zeroY] == 0) {
                        break outer;
                    }
                }
            }

            int[] dx = {0, 0, -1, 1};
            int[] dy = {-1, 1, 0, 0};
            for (int i = 0; i < 4; i++) {
                int newX = zeroX + dx[i];
                int newY = zeroY + dy[i];
                if (newX >= 0 && newX < current.state.length && newY >= 0 && newY < current.state[newX].length) {
                    int[][] newState = new int[current.state.length][current.state[0].length];
                    for (int k = 0; k < current.state.length; k++) {
                        System.arraycopy(current.state[k], 0, newState[k], 0, current.state[k].length);
                    }
                    newState[zeroX][zeroY] = current.state[newX][newY];
                    newState[newX][newY] = 0;
                    Node child = new Node(newState, current);
                    if (!visited.contains(child)) {
                        frontier.add(child);
                    }
                }
            }
        }
        return null;
    }

    static void printSolution(Node solution) {
        List<int[][]> path = new ArrayList<>();
        while (solution != null) {
            path.add(solution.state);
            solution = solution.parent;
        }
        Collections.reverse(path);
        for (int[][] state : path) {
            for (int[] row : state) {
                for (int value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] initial = {
                {1, 2, 3},
                {0, 4, 6},
                {7, 5, 8}
        };

        System.out.println("Initial State:");
        for (int[] row : initial) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("Goal State:");
        for (int[] row : GOAL_STATE) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        System.out.println();

        Node solution = bestFirstSearch(initial);

        if (solution != null) {
            System.out.println("Solution found:");
            printSolution(solution);
        } else {
            System.out.println("No solution found.");
        }
    }
}
