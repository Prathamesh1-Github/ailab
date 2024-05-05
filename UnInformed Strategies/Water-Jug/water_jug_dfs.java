import java.util.*;

public class water_jug_dfs {

    public static int total = 0;

    static class Node {
        int x;
        int y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static Stack<Node> result = new Stack<Node>();
    static boolean[][] m = new boolean[5][5];
    static int found = 0;

    static boolean solveDFS(int curj1, int curj2, int jug1, int jug2, int tx, int ty) {
        if (curj1 == tx && curj2 == ty) {
            result.add(new Node(curj1, curj2));
            return true;
        }
        total++;
        if (m[curj1][curj2]) {
            return false;
        }
        m[curj1][curj2] = true;
        if (curj1 < jug1 && solveDFS(jug1, curj2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }
        if (curj2 < jug2 && solveDFS(curj1, jug2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }
        if (curj1 > 0 && solveDFS(0, curj2, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }
        if (curj2 > 0 && solveDFS(curj1, 0, jug1, jug2, tx, ty)) {
            result.add(new Node(curj1, curj2));
            return true;
        }
        if (curj1 > 0 && curj2 < jug2) {
            boolean temp;
            if (curj2 + curj1 <= jug2) {
                temp = solveDFS(0, curj1 + curj2, jug1, jug2, tx, ty);
            } else {
                temp = solveDFS(curj1 - (jug2 - curj2), jug2, jug1, jug2, tx, ty);
            }
            if (temp) {
                result.add(new Node(curj1, curj2));
                return true;
            }
        }
        if (curj2 > 0 && curj1 < jug1) {
            boolean temp;
            if (curj2 + curj1 <= jug1) {
                temp = solveDFS(curj1 + curj2, 0, jug1, jug2, tx, ty);
            } else {
                temp = solveDFS(jug1, curj2 - (jug1 - curj2), jug1, jug2, tx, ty);
            }
            if (temp) {
                result.add(new Node(curj1, curj2));
                return true;
            }
        }
        m[curj1][curj2] = false;
        return false;
    }

    public static void printReverseStack(Stack<Node> stack) {
        if (stack.isEmpty())
            return;
        Stack<Node> temporaryStack = new Stack<>();
        while (stack.size() > 0) {
            temporaryStack.push(stack.pop());
        }
        for (Node n : temporaryStack) {
            System.out.println("(" + n.x + "," + n.y + ")");
        }
    }

    public static void main(String[] args) {
        boolean res = solveDFS(0, 0, 4, 3, 2, 0);
        printReverseStack(result);
        if (res == false) {
            System.out.println("Not Possible to obtain target");
        }
    }
}
