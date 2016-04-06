import java.io.IOException;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    final static int boardPieces = 32;
    static int board[][]=new int[6][7];

    public static void main(String[] args) throws IOException
    {
        Board b = new Board();
        b.runBoard();
        miniMax miniMax= new miniMax(b);
        miniMax.playAgainstAIConsole();
    }
}
