import java.util.Scanner;

public class tic_tac_toe_magicsquare {

  public static int win = 0;
  public static int[] xPos = new int[10];
  public static int[] oPos = new int[10];

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

  public static int possWin(int check, int[] board){
    if(check == 3){
      for(int i=1; i<10; i++){
        if(xPos[i]==1){
          for(int j=i+1; j<10; j++){
            if(xPos[j]==1){
              if(board[15-(i+j)] == 2){
                return 15-(i+j);
              }
            }
          }
        }
      }
    }
    else if(check == 5){
      for(int i=1; i<10; i++){
        if(oPos[i]==1){
          for(int j=i+1; j<10; j++){
            if(oPos[j]==1){
              if(board[15-(i+j)] == 2){
                return 15-(i+j);
              }
            }
          }
        }
      }
    }
    return -1;
  }

  public static int[] go(int i, int[] board, int value){
    if(value == 3){
      xPos[i+1] = 1;
    }
    else if(value == 5){
      oPos[i+1] = 1;
    }
    board[i] = value;
    return board;
  }

  public static int make2(int[] board){
    if(board[4] == 2){
      return 4;
    }
    else if(board[1] == 2){
      return 1;
    }
    else if(board[3] == 2){
      return 3;
    }
    else if(board[5] == 2){
      return 5;
    }
    else if(board[6] == 2){
      return 6;
    }
    return -1;
  }

  public static int getBlank(int[] board){
    for(int i=0; i<9; i++){
      if(board[i] == 2){
        return i;
      }
    }
    return -1;
  }

  public static int[] turnE(int i, int[] board){
    System.out.println("PossWin for O: " + possWin(5, board));
    System.out.println("PossWin for X: " + possWin(3, board));
    if(i==2){
      if(board[4] == 2){
        board = go(4, board, 5);
      }
      else{
        board = go(0, board, 5);
      }
    }
    if(i==4){
      int pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);      
      }
      else{
        board = go(1, board, 5);
      }
    }
    if(i==6){
      int pw = possWin(5, board);
      if(pw != -1){
        board = go(pw, board, 5);
        win = 5;
        return board;
      }
      pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);
      }
      else{
        board = go(make2(board), board, 5);
      }
    }
    if(i==8){
      int pw = possWin(5, board);
      if(pw != -1){
        board = go(pw, board, 5);
        win = 5;
        return board;
      }
      pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);
      }
      else{
        board = go(getBlank(board), board, 5);
      }
    }
    return board;
  }

  public static int[] turnO(int i, int[] board){
    System.out.println("PossWin for O: " + possWin(5, board));
    System.out.println("PossWin for X: " + possWin(3, board));
    if(i==1){
      board = go(0, board, 5);
    }
    if(i==3){
      if(board[8]==2){
        board = go(8, board, 5);      
      }
      else{
        board = go(2, board, 5);
      }
    }
    if(i==5){
      int pw = possWin(5, board);
      if(pw != -1){
        board = go(pw, board, 5);
        win = 5;
        return board;
      }
      pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);
      }
      else if(board[6]==2){
        board = go(6, board, 5);
      }
      else{
        board = go(2, board, 5);
      }
    }
    if(i==7){
      int pw = possWin(5, board);
      if(pw != -1){
        board = go(pw, board, 5);
        win = 5;
        return board;
      }
      pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);
      }
      else{
        board = go(getBlank(board), board, 5);
      }
    }
    if(i==9){
      int pw = possWin(5, board);
      if(pw != -1){
        board = go(pw, board, 5);
        win = 5;
        return board;
      }
      pw = possWin(3, board);
      if(pw != -1){
        board = go(pw, board, 5);
      }
      else{
        board = go(getBlank(board), board, 5);
      }
    }
    return board;
  }

  public static int[] humanTurn(int i, int[] board){
    System.out.println("Enter the index where you want to enter X:");
    Scanner sc = new Scanner(System.in);
    int humanIndexO = sc.nextInt();
    int humanIndex = humanIndexO - 1;
    if(board[humanIndex] == 2){
      board[humanIndex] = 3;
      int pw = WinCheck(5, board);
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

    // int emojiCelebration = 0x1F389;

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
          board = turnE(i, board);
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
          board = turnO(i, board);
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
