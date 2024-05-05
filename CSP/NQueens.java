package CSP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NQueens {

    private static boolean canPlace(int i, int j, int n, List<String> temp) {
        for (int p = 0; p < n; p++) {
            if (temp.get(p).charAt(j) == 'Q') {
                return false;
            }
        }
        int t1 = i;
        int t2 = j;
        while (t1 >= 0 && t2 >= 0) {
            if (temp.get(t1).charAt(t2) == 'Q') {
                return false;
            }
            t1--;
            t2--;
        }
        t1 = i;
        t2 = j;
        while (t1 >= 0 && t2 < n) {
            if (temp.get(t1).charAt(t2) == 'Q') {
                return false;
            }
            t1--;
            t2++;
        }
        return true;
    }

    private static void solve(int i, int n, List<String> temp, List<List<String>> res) {
        if (i == n) {
            res.add(new ArrayList<>(temp));
            return;
        }
        for (int j = 0; j < n; j++) {
            if (canPlace(i, j, n, temp)) {
                StringBuilder row = new StringBuilder(temp.get(i));
                row.setCharAt(j, 'Q');
                temp.set(i, row.toString());
                solve(i + 1, n, temp, res);
                row.setCharAt(j, '.'); // backtrack
                temp.set(i, row.toString());
            }
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        StringBuilder x = new StringBuilder();
        for (int i = 0; i < n; i++) {
            x.append('.');
        }
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            temp.add(x.toString());
        }
        solve(0, n, temp, res);
        return res;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens (N): ");
        int n = scanner.nextInt();

        List<List<String>> solutions = solveNQueens(n);

        System.out.println("Total solutions: " + solutions.size());
        for (int i = 0; i < solutions.size(); i++) {
            System.out.println("Solution " + (i + 1) + ":");
            List<String> solution = solutions.get(i);
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}

