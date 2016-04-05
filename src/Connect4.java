import java.io.IOException;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    final static int boardPieces = 32;

    public static void main(String[] args) throws IOException
    {
        boolean win;
        boardGeneration b = new boardGeneration();
        /*for (int i = 0; i < 10; i++) {
            b.generateBoard();
            b.clearboard();
        }
        */
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

    }

}
