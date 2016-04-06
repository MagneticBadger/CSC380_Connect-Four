import java.io.IOException;

/**
 * Created by krm1929 on 4/6/2016.
 */
public class Board extends boardGeneration
{
    static int[][] board = new int[6][7];
    public static void runBoard() throws IOException {
        boolean win;
        boardGeneration b = new boardGeneration();

        win = b.generateBoard(boardPieces);
        while(win)
        {
            b.clearboard();
            win = b.generateBoard(boardPieces);
            if (!win) {
                break;
            }
        }
        b.boardWriter(b.getBoard());
        board = b.getBoard();
    }

    public boolean isLegalMove(int column){
        return board[0][column]==0;
    }

    //Placing a Move on the board
    public boolean placeMove(int column, int player){
        if(!isLegalMove(column)) {System.out.println("Illegal move!"); return false;}
        for(int i=5;i>=0;--i){
            if(board[i][column] == 0) {
                board[i][column] = (byte)player;
                return true;
            }
        }
        return false;
    }

}
