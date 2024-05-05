import java.util.Scanner;

public class tic_tac_toe_minmax {

  public static int win = 0;

  public static int minimax(int board[], int depth, Boolean isMax){
    int check = WinCheck(5, board);
    if(check == 1){
      return 6;
    }
    check = WinCheck(3, board);
    if(check == 1){
      return -6;
    }
    if(getBlank(board) == -1){
      return 0;
    }

    if(isMax){
      int best = Integer.MIN_VALUE;
      for(int i=0; i<9; i++){
        if(board[i] == 2){
          board[i] = 5;
          best = Math.max(best, minimax(board, depth+1, !isMax));
          board[i] = 2;
        }
      }
      return best;
    }
    else{
      int best = Integer.MAX_VALUE;
      for(int i=0; i<9; i++){
        if(board[i] == 2){
          board[i] = 3;
          best = Math.min(best, minimax(board, depth+1, !isMax));
          board[i] = 2;
        }
      }
      return best;
    }
  }

  public static int findBestMove(int[] board){
    int bestVal = Integer.MIN_VALUE;
    int bestMove = -1;
    for(int i=0; i<9; i++){
      if(board[i] == 2){
        board[i] = 5;
        int moveVal = minimax(board, 0, false);
        board[i] = 2;
        if(moveVal > bestVal){
          bestMove = i;
          bestVal = moveVal;
        }
      }
    }
    System.out.println("The value of best move is : " + bestVal);
    return bestMove;
  }

  public static void printMatrix(int[] board){
    System.out.println("---------");
    for(int i=0; i<3; i++){
      System.out.print("| ");
      for(int j=0; j<3; j++){
        int index = (3*i)+j;
        if(board[index] == 3){
          System.out.print("X ");
        }
        else if(board[index] == 5){
          System.out.print("O ");
        }
        else{
          System.out.print(". ");
        }
      }
      System.out.print("|");
      System.out.println();
    }
    System.out.println("---------");
  }

  public static int WinCheck(int check, int[] board){
    // row
    for(int i=0; i<3; i++){
      int product = 1;
      for(int j=0; j<3; j++){
        int index = (i*3)+j;
        product *= board[index];
      }
      if(check==3 && product==27){
        return 1;
      }
      else if(check==5 && product==125){
        return 1;
      }
    }
    // col
    for(int i=0; i<3; i++){
      int product = 1;
      for(int j=0; j<3; j++){
        int index = i+(j*3);
        product *= board[index];
      }
      if(check==3 && product==27){
        return 1;
      }
      else if(check==5 && product==125){
        return 1;
      }
    }
    // diagonal
    int product = 1;
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
        if(i==j){
          int index = (i*3)+j;
          product *= board[index];
        }
      }
      if(check==3 && product==27){
        return 1;
      }
      else if(check==5 && product==125){
        return 1;
      }
    }
    // diagonal
    product = 1;
    for(int i=0; i<3; i++){
      for(int j=0; j<3; j++){
        if(i==0 && j==2){
          product *= board[2];
        }
        if(i==1 && j==1){
          product *= board[4];
        }
        if(i==2 && j==0){
          product *= board[6];
        }
      }
      if(check==3 && product==27){
        return 1;
      }
      else if(check==5 && product==125){
        return 1;
      }
    }
    return 0;
  }


  public static int[] go(int i, int[] board, int value){
    board[i] = value;
    return board;
  }

  public static int getBlank(int[] board){
    for(int i=0; i<9; i++){
      if(board[i] == 2){
        return i;
      }
    }
    return -1;
  }

  public static int[] humanTurn(int i, int[] board){
    System.out.println("Enter the index where you want to enter X:");
    Scanner sc = new Scanner(System.in);
    int humanIndexO = sc.nextInt();
    int humanIndex = humanIndexO - 1;
    if(board[humanIndex] == 2){
      board[humanIndex] = 3;
      int pw = WinCheck(3, board);
      if(pw==1){
        win = 3;
      }
    }
    else{
      System.out.println("Index is already filled !!");
      humanTurn(i, board);
    }
    return board;
  }

  public static void main(String[] args) {
    int board[] = new int[9];
    for(int i=0; i<9; i++){
      board[i] = 2;
    }
    printMatrix(board);
    System.out.println("Enter the turn (1st or 2nd):");
    Scanner sc = new Scanner(System.in);
    int humanChance = sc.nextInt();
    for(int i=1; i<=9; i++){
      if(humanChance == 1){
        if(i%2 == 0){
          int m = findBestMove(board);
          System.out.println("m: " + m);
          if(m !=- 1){
            board = go(m, board, 5);
          }
          int pw = WinCheck(5, board);
          if(pw==1){
            win = 5;
          }
          System.out.println("***** Board After Computer Play *****");
          printMatrix(board);
        }
        else{
          board = humanTurn(i, board);
          System.out.println("***** Board After Your Play *****");
          printMatrix(board);
        }
      }
      else if(humanChance == 2){
        if(i%2 != 0){
          int m = findBestMove(board);
          board = go(m, board, 5);
          int pw = WinCheck(5, board);
          if(pw==1){
            win = 5;
          }
          System.out.println("***** Board After Computer Play *****");
          printMatrix(board);
        }
        else{
          board = humanTurn(i, board);
          System.out.println("***** Board After Your Play *****");
          printMatrix(board);
        }
      }
      if(win != 0){
        if(win == 3){
          System.out.println("*** You have won the Game! ***");
          break;
        }
        else if(win == 5){
          System.out.println("*** Computer has won the Game! ***");
          break;
        }
      }
    }

    // sc.close();
  }

}
