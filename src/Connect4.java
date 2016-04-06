import java.io.IOException;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    public static int currentPlayer;
    final static int boardPieces = 32;
    static int board[][]=new int[6][7];
    static int winner = 0;

    public static void main(String[] args) throws IOException
    {
        Board b = new Board();
        board = b.runBoard();
        currentPlayer=1;
        miniMax miniMax= new miniMax(30000000,b);
        do {
            int column=0;
            column= miniMax.miniMaxDecision(b);
            b.printBoard(board);
            changePlayer();
            winner = b.isFinished();
        }
        while(winner ==-1);
            System.out.print(winner);

        System.out.println("Got here");
        b.printBoard(board);
    }
    public static void changePlayer() {
        if (currentPlayer == 1)
            currentPlayer = 2;
        else currentPlayer = 1;
    }
}
