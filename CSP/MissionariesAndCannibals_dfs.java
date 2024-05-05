package CSP;

import java.util.ArrayList;

class State {
    int missionariesLeft;
    int cannibalsLeft;
    int boat;
    int missionariesRight;
    int cannibalsRight;

    public State(int missionariesLeft, int cannibalsLeft, int boat, int missionariesRight, int cannibalsRight) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.boat = boat;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
    }
}


public class MissionariesAndCannibals_dfs {

    static boolean isValidState(int missionaries, int cannibals) {
        if (missionaries < 0 || cannibals < 0 || missionaries > 3 || cannibals > 3)
            return false;
        if ((missionaries < cannibals && missionaries > 0) || (missionaries > cannibals && missionaries < 3))
            return false;
        return true;
    }

    static boolean isGoalState(State state) {
        return state.missionariesLeft == 0 && state.cannibalsLeft == 0;
    }

    static boolean isVisited(State state, ArrayList<State> visited) {
        for (State visitedState : visited) {
            if (visitedState.missionariesLeft == state.missionariesLeft &&
                visitedState.cannibalsLeft == state.cannibalsLeft &&
                visitedState.boat == state.boat &&
                visitedState.missionariesRight == state.missionariesRight &&
                visitedState.cannibalsRight == state.cannibalsRight) {
                return true;
            }
        }
        return false;
    }

    static void printState(State state) {
        System.out.print(state.missionariesLeft + " Missionaries, " + state.cannibalsLeft + " Cannibals, Boat: ");
        if (state.boat == 0)
            System.out.print("Left Bank -> ");
        else
            System.out.print("Right Bank -> ");
        System.out.println(state.missionariesRight + " Missionaries, " + state.cannibalsRight + " Cannibals");
    }

    static void solve(State currentState, ArrayList<State> visited) {
        if (isGoalState(currentState)) {
            printState(currentState);
            return;
        }

        visited.add(currentState);

        for (int m = 0; m <= 2; ++m) {
            for (int c = 0; c <= 2; ++c) {
                if (m + c <= 2 && m + c > 0) {
                    State nextState = new State(currentState.missionariesLeft, currentState.cannibalsLeft,
                                                currentState.boat, currentState.missionariesRight,
                                                currentState.cannibalsRight);
                    if (currentState.boat == 0) {
                        nextState.missionariesLeft -= m;
                        nextState.cannibalsLeft -= c;
                        nextState.missionariesRight += m;
                        nextState.cannibalsRight += c;
                        nextState.boat = 1;
                    } else {
                        nextState.missionariesLeft += m;
                        nextState.cannibalsLeft += c;
                        nextState.missionariesRight -= m;
                        nextState.cannibalsRight -= c;
                        nextState.boat = 0;
                    }

                    if (isValidState(nextState.missionariesLeft, nextState.cannibalsLeft) &&
                        isValidState(nextState.missionariesRight, nextState.cannibalsRight) &&
                        !isVisited(nextState, visited)) {
                        solve(nextState, visited);
                    }
                }
            }
        }

        visited.remove(visited.size() - 1);
    }

    public static void main(String[] args) {
        State initialState = new State(3,3,0,0,0);
        ArrayList<State> visited = new ArrayList<>();
        System.out.println("Possible Solutions:");
        solve(initialState, visited);
    }
}
