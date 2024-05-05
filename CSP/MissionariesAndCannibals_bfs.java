package CSP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class State {
    int miss_left;
    int miss_right;
    int cann_left;
    int cann_right;
    int boat; // 0 for left, 1 for right

    State parent;

    public State(int miss_left, int miss_right, int cann_left, int cann_right, int boat, State parent) {
        this.miss_left = miss_left;
        this.miss_right = miss_right;
        this.cann_left = cann_left;
        this.cann_right = cann_right;
        this.boat = boat;
        this.parent = parent;
    }
}

public class MissionariesAndCannibals_bfs {

    static boolean isVisited(State curr, ArrayList<State> visited) {
        for (State state : visited) {
            if (state.boat == curr.boat &&
                state.miss_left == curr.miss_left &&
                state.cann_left == curr.cann_left &&
                state.cann_right == curr.cann_right) {
                return true;
            }
        }
        return false;
    }

    static boolean isGoal(State curr) {
        return curr.miss_left == 0 && curr.cann_left == 0;
    }

    static boolean isValid(State curr) {
        return curr.miss_left >= 0 && curr.miss_left <= 3 &&
                curr.miss_right >= 0 && curr.miss_right <= 3 &&
                curr.cann_left >= 0 && curr.cann_left <= 3 &&
                curr.cann_right >= 0 && curr.cann_right <= 3 &&
                !(curr.cann_left > curr.miss_left && curr.miss_left != 0) &&
                !(curr.cann_right > curr.miss_right && curr.miss_right != 0);
    }

    static void printState(State curr) {
        List<State> path = new ArrayList<>();
        while(curr != null){
            path.add(curr);
            curr = curr.parent;
        }
        Collections.reverse(path);
        for(int i=0; i<path.size(); i++){
            System.out.print("Left Bank -> ");
            System.out.print(path.get(i).miss_left + " Missionaries, " + path.get(i).cann_left + " Cannibals, Boat: ");
            System.out.println("Right Bank -> " + path.get(i).miss_right + " Missionaries, " + path.get(i).cann_right + " Cannibals");
        }
    }

    static void solve(State initial_state) {
        ArrayList<State> visited = new ArrayList<>();
        visited.add(initial_state);

        Queue<State> q = new LinkedList<>();
        q.add(initial_state);

        while (!q.isEmpty()) {
            State temp = q.poll();

            if (isGoal(temp)) {
                printState(temp);
                continue;
            }

            for (int m = 0; m <= 2; m++) {
                for (int c = 0; c <= 2; c++) {
                    if (m + c <= 2 && m + c > 0) {
                        State next = new State(temp.miss_left, temp.miss_right, temp.cann_left, temp.cann_right, temp.boat, temp);
                        if (temp.boat == 0) {
                            next.cann_left -= c;
                            next.cann_right += c;
                            next.miss_left -= m;
                            next.miss_right += m;
                            next.boat = 1;
                        } else {
                            next.cann_left += c;
                            next.cann_right -= c;
                            next.miss_left += m;
                            next.miss_right -= m;
                            next.boat = 0;
                        }

                        if (isValid(next) && !isVisited(next, visited)) {
                            q.add(next);
                            visited.add(next);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        State initial_state = new State(3, 0, 3, 0, 0, null);
        solve(initial_state);
    }
}
