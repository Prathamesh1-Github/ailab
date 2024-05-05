package CSP;

import java.util.Scanner;

public class SudokuSolver {

    static boolean helper(int i, int j, int k, char[][] board) {
        for (int p = 0; p < 9; p++) {
            if (board[i][p] == (char)('0' + k))
                return false;
            if (board[p][j] == (char)('0' + k))
                return false;
            if (board[3 * (i / 3) + (p / 3)][3 * (j / 3) + p % 3] == (char)('0' + k))
                return false;
        }
        return true;
    }

    static boolean solve(char[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    for (int k = 1; k <= 9; k++) {
                        if (helper(i, j, k, board)) {
                            board[i][j] = (char)('0' + k);
                            if (solve(board)) {
                                return true;
                            } else {
                                board[i][j] = '.';
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the Sudoku board (use '.' for empty cells):");
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // board[i][j] = scanner.next().charAt(0);
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        solve(board);

        System.out.println("Solved Sudoku board:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        scanner.close();
    }
}
