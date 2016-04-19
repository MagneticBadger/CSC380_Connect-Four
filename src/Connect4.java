import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import MTSC.Run;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    //    public static int currentPlayer;
//    static int winner = 0;
    public static int boardNumber = 0;
    public static int test = 100;
    private static Board b;
    private static Board b2;
    private static boardGeneration bGenerator = new boardGeneration();
    public static String fileMini = "miniMaxData.csv";
    public static String fileAlpha = "alphaBetaData.csv";
    public static String fileGreddy = "Greddy.csv";
    public static String fileBrute = "Brute.csv";
    static FileWriter writerMiniMax;
    static FileWriter writerAlphaBeta;
    static FileWriter writerGreddy;
    static FileWriter writerBrute;

    public static File fileMiniMax;
    public static File filealphaBeta;
    public static File filegreddy;
    public static File filebrute;
    public static int NumberOfRuns=0;

    public static void main(String[] args) throws IOException
    {

        int runNumber = 10;
        double winsMiniMax=0.0,winsAlphaBeta=0.0,winsGreddy=0.0, bruteWins=0.0;
        Timer timer = new Timer();
        long startTime;
        long endTime;

        fileMiniMax = new File(fileMini);
        writerMiniMax = new FileWriter(fileMiniMax,true);
        writerMiniMax.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");

        filealphaBeta = new File(fileAlpha);
        writerAlphaBeta = new FileWriter(filealphaBeta,true);
        writerAlphaBeta.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");

        filegreddy = new File(fileGreddy);
        writerGreddy = new FileWriter(filegreddy,true);
        writerGreddy.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");

        filebrute = new File(fileBrute);
        writerBrute = new FileWriter(filebrute,true);
        writerBrute.append("WinNumber,Runtime(ms),Number of Moves\n");
        boardNumber++;

         while(runNumber!=0)
         {
             NumberOfRuns++;

             double averageChildMiniMax = 0.0,averageChildAlphaBeta = 0.0,averageChildGreddy = 0.0;
             long millisGreddy=0,millisMiniMax = 0,millis=0;
             System.out.println("MINIMAX\n");
             b = new Board(bGenerator.generateBoard(18));

             int[][] tempBoard = new int[6][7];
             tempBoard = (testCopy2DArray(b.board));
             Board b2 = new Board(tempBoard);

             int[][] tempboard2 = new int[6][7];
             tempboard2 = (testCopy2DArray(b.board));
             Board b3 = new Board(tempboard2);

             int[][] tempboard3 = new int[6][7];
             tempboard3 = (testCopy2DArray(b.board));
             Board b4 = new Board(tempboard3);

             startTime = System.nanoTime();
             MiniMax minimax = new MiniMax(b);
             boolean winner = minimax.play();
             if (winner)
             {
                 winsMiniMax++;
                 endTime = System.nanoTime();

                 for (Integer mark : minimax.getRunningChilTotal())
                 {
                     averageChildMiniMax += mark;
                 }
                 averageChildMiniMax = averageChildMiniMax / minimax.getRunningChilTotal().size();
                 millisMiniMax = endTime - startTime;
             }
            System.out.println("MINIMAX WITH ALPHA BETA\n");
            startTime = System.nanoTime();
            AlphaBeta alphabeta = new AlphaBeta(b2);
            boolean alphaBetaWinner =alphabeta.play();
            if (alphaBetaWinner)
            {
                winsAlphaBeta++;

                endTime = System.nanoTime();
                averageChildAlphaBeta = 0.0;
                for (Integer mark : alphabeta.getRunningChilTotal()){
                    averageChildAlphaBeta += mark;
                }
                averageChildAlphaBeta = averageChildAlphaBeta / alphabeta.getRunningChilTotal().size();
                millis = endTime - startTime;
            }

            System.out.println("Greddy my NIG\n");

            Greddy greddy = new Greddy(b4);
            startTime = System.nanoTime();
            boolean greddyWinner = greddy.play();
            if (greddyWinner)
            {
                winsGreddy++;
                endTime = System.nanoTime();
                for (Integer mark : greddy.getRunningChilTotal()) {
                    averageChildGreddy += mark;
                }
                averageChildGreddy = averageChildGreddy / greddy.getRunningChilTotal().size();
                millisGreddy = endTime - startTime;
            }
             startTime = System.nanoTime();
             BruteForce force = new BruteForce(b3);
             int player =force.run();
//             int bruteWins = 0;
             if (player==1)
             {
                 bruteWins++;
             }
             endTime = System.nanoTime();
             long nanoBrute = endTime-startTime;
             if(greddyWinner||alphaBetaWinner||winner||player==1)
             {
                 runNumber--;
                 writerMiniMax.append((winsMiniMax / (NumberOfRuns)) + "," + millisMiniMax + "," + averageChildMiniMax + "," + minimax.getNumberOfMoves() + "\n");
                 writerAlphaBeta.append((winsAlphaBeta / (NumberOfRuns)) + "," + millis + "," + averageChildAlphaBeta + "," + alphabeta.getNumberOfMoves() + "\n");
                 writerGreddy.append((winsGreddy / (NumberOfRuns)) + "," + millisGreddy + "," + averageChildGreddy + "," + greddy.getNumberOfMoves() + "\n");
                 writerBrute.append((bruteWins / (NumberOfRuns)) + "," + nanoBrute + "," +force.getNumberOfMoves()+"\n");
             }


        }

        writerMiniMax.flush();
        writerMiniMax.close();
        writerAlphaBeta.flush();
        writerAlphaBeta.close();
        writerGreddy.flush();
        writerGreddy.close();
        writerBrute.flush();
        writerBrute.close();



    }
    public static int[][] testCopy2DArray(int[][] b)
    {
        int[][] tempboard = new int[6][7];
        for (int i = 0; i < b.length; i++)
            for (int j = 0; j < b[i].length; j++) {
                tempboard[i][j] = b[i][j];
            }
        return tempboard;
    }
}

