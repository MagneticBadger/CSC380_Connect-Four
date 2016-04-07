import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
//    public static int currentPlayer;
//    static int winner = 0;
    public static int boardNumber = 0;
    public static int test =100;
    private static Board b;
    private static boardGeneration bGenerator = new boardGeneration();

    public static void main(String[] args) throws IOException
    {
            int piecesInBoard=0;
            boardNumber++;
            b = new Board(bGenerator.generateBoard(32));
            MiniMaxCodeBytes minimax = new MiniMaxCodeBytes(b);
            minimax.play();
            //System.out.println("Board Number: " +boardNumber + "\tPieces in board: " + piecesInBoard);
    }
    }
