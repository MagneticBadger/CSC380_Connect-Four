import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
//    public static int currentPlayer;
//    static int winner = 0;

    static Board b;
    static boardGeneration bGenerator = new boardGeneration();

    public static void main(String[] args) {
        try {
            b = new Board(bGenerator.generateBoard(32));
        } catch (IOException e) {};
        MiniMaxCodeBytes minimax = new MiniMaxCodeBytes(b);
        minimax.play();
    }

//    public static void main(String[] args) throws IOException
//    {
//        Board b = new Board();
//        board = b.runBoard();
//        currentPlayer=1;
//        Random rand = new Random();
//        System.out.println("INITAL BOARD");
//        b.printBoard(board);
//        miniMax miniMax= new miniMax(30000000,b);
//        int column=0;
//        while(b.isFinished()==-1)
//        {
//            if(currentPlayer==1)
//            {
//                System.out.println("After player 1");
//
//                column = (rand.nextInt() * 100) % 6;
//            }
//            else
//            {
//                System.out.println("After player 2");
//                column = miniMax.miniMaxDecision(b);
//            }
//            while(!b.insert(column,currentPlayer))
//            {
//                column = (rand.nextInt() * 100) % 6;
//            }
//            b.printBoard(board);
//            changePlayer();
//            winner =b.isFinished();
//        }
//    }
//    public static void changePlayer()
//    {
//        if (currentPlayer == 1)
//            currentPlayer = 2;
//        else currentPlayer = 1;
//    }
}
