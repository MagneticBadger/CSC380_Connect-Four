import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    public static int currentPlayer;

    static int board[][]=new int[6][7];
    static int winner = 0;

    public static void main(String[] args) throws IOException
    {
        Board b = new Board();
        b.runBoard();
        miniMax minimax = new miniMax(b);
        minimax.startMinimax();
    }
}
