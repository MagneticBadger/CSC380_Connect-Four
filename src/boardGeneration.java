import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 * pull success pull success
 */
public class boardGeneration extends Connect4 {
    Random rand = new Random();
    int row = 0;
    private int[][] board = new int[6][7];
    private int[] rowCol = new int[2];
    private int players = 1;
    private int numberofboards=0;

    public int[][] generateBoard(int pieces) throws IOException {
        boolean p1 = true;
        int win = 0;
        int col = rand.nextInt();
        for (int i = 0; i < pieces; i++) {
            col = Math.abs(col % 7);
            checkRowCol(row, col);
            if (p1) {
                getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                players = 2;
                p1 = false;
            } else {
                getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                players = 1;
                p1 = true;
            }
            win = checkWin();
            if (win==-99)
            {
                break;
            }
            col = rand.nextInt();
        }

        if (win==-99)
        {
            clearboard();
            //numberofboards++;
            generateBoard(pieces);
            //System.out.println(numberofboards);

        }
        else return board;

        return board;
        //printBoard(board);
    }

    public int checkWin() {

        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(board[i][j]==0) continue;

                //Checking cells to the right
                if(j<=3){
                    for(int k=0;k<4;++k){
                        if(board[i][j+k]==1) aiScore++;
                        else if(board[i][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking cells up
                if(i>=3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j]==1) aiScore++;
                        else if (board[i-k][j]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-right
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j+k]==1) aiScore++;
                        else if(board[i-k][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-left
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j-k]==1) aiScore++;
                        else if(board[i-k][j-k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }
            }
        }

        for(int j=0;j<7;++j){
            //Game has not ended yet
            if(board[0][j]==0)return -1;
        }
        return 0;
    }

    private int[] checkRowCol(int row, int col) {
        if (getBoard()[row][col] == 0) {
            //board[row][col] = 1;
            getRowCol()[0] = row;
            getRowCol()[1] = col;
            //System.out.println(row + " " + col);
            return getRowCol();
        } else if (getBoard()[row][col] != 0 && row < 5) {
            row += 1;
            return checkRowCol(row, col);

        } else {
            row = 0;
            return checkRowCol(row, generateCol());

        }

    }

    private int generateCol() {
        int col = rand.nextInt();
        //System.out.println(Math.abs(col % 7));
        col = Math.abs(col % 7);

        return col;

    }

    public void clearboard() {
        for (int f = getBoard().length - 1; 0 <= f; f--) {

            for (int k = 0; k < getBoard()[f].length; k++) {
                getBoard()[f][k] = 0;
            }
        }
        setBoard(new int[6][7]);
        rowCol = new int[2];
        players = 1;
        row = 0;

    }

    public int[][] getBoard() {
        return board;
    }

    public int[] getRowCol() {
        return rowCol;
    }

    public int getPlayers() {
        return players;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}