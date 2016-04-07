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
        board = b.runBoard();
        currentPlayer=1;
        Random rand = new Random();
        b.printBoard(board);
        miniMax miniMax= new miniMax(30000000,b);
        do {
            int column=0;
            if (currentPlayer == 1)
            {
                column = miniMax.miniMaxDecision(b);
            } else
            {
                column = (rand.nextInt() * 100) % 6;
            }
            while(!b.insert(column, currentPlayer))
                column = (rand.nextInt()*100)%6;
            b.printBoard(board);
            changePlayer();
            winner = b.isFinished();
        }
        while (winner == -1);
            System.out.println(winner);

//        while(b.isFinished()==-1)
//        {
//            if(b.isFinished()==1)
//            {
//                winner=1;
//                break;
//            }
//            else if(b.isFinished()==2)
//            {
//                winner=2;
//                break;
//            }
//            else
//            {
//                int column = 0;
//                column = miniMax.miniMaxDecision(b);
//                b.insert(column, currentPlayer);
//                b.printBoard(board);
//                changePlayer();
//            }
//
//        }
    }
    public static void changePlayer()
    {
        if (currentPlayer == 1)
            currentPlayer = 2;
        else currentPlayer = 1;
    }
}
