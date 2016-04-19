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
    static FileWriter writerMiniMax;
    static FileWriter writerAlphaBeta;

    public static File fileMiniMax;
    public static File filealphaBeta;

    public static void main(String[] args) throws IOException
    {
        int runNumber = 10;
        double winsMiniMax=0.0,winsAlphaBeta=0.0;
        Timer timer = new Timer();
        long startTime;
        long endTime;

        fileMiniMax = new File(fileMini);
        writerMiniMax = new FileWriter(fileMiniMax,true);
        writerMiniMax.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");

        filealphaBeta = new File(fileAlpha);
        writerAlphaBeta = new FileWriter(filealphaBeta,true);
        writerAlphaBeta.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");
        boardNumber++;

         while(runNumber !=0)
        {
            System.out.println("MINIMAX\n");
            runNumber--;
            b = new Board(bGenerator.generateBoard(2));

            int[][] tempBoard = new int[6][7];
            tempBoard= (testCopy2DArray(b.board));
            Board b2 = new Board(tempBoard);

            tempBoard = new int[6][7];
            tempBoard= (testCopy2DArray(b.board));
            Board b3 = new Board(tempBoard);

            startTime = System.currentTimeMillis();
            MiniMax minimax = new MiniMax(b);
            boolean winner = minimax.play();
            if (winner)
            {
                winsMiniMax++;
                endTime = System.currentTimeMillis();
                double averageChild = 0.0;
                for (Integer mark : minimax.getRunningChilTotal()) {
                    //System.out.println(mark);
                    averageChild += mark;
                }
                averageChild = averageChild / minimax.getRunningChilTotal().size();
                long millisMiniMax = endTime - startTime;
                //double fullSeconds = (millis);
                int secondsMiniMax = (int) (millisMiniMax / 1000) % 60;
                int minutesMiniMax = (int) ((millisMiniMax / (1000 * 60)) % 60);
                System.out.print(minutesMiniMax + ":" + secondsMiniMax + "Wins: " + winsMiniMax + " runNumber: " + runNumber +
                        " Number Of Moves " + minimax.getNumberOfMoves() + " Size: " + averageChild + "\n\n\n");

                writerMiniMax.append(winsMiniMax / (10 - runNumber) + "," + millisMiniMax + "," + averageChild + "," + minimax.getNumberOfMoves() + "\n");
                System.out.println("MINIMAX WITH ALPHA BETA\n");
                startTime = System.currentTimeMillis();
                AlphaBeta alphabeta = new AlphaBeta(b2);
                boolean alphaBetaWinner =alphabeta.play();
                if (alphaBetaWinner)
                {
                    winsAlphaBeta++;
                    endTime = System.currentTimeMillis();
                    averageChild = 0.0;
                    for (Integer mark : alphabeta.getRunningChilTotal()){
                        averageChild += mark;
                    }
                    averageChild = averageChild / alphabeta.getRunningChilTotal().size();
                    long millis = endTime - startTime;
                    //double fullSeconds = (millis);
                    int seconds = (int) (millis / 1000) % 60;
                    int minutes = (int) ((millis / (1000 * 60)) % 60);
                    System.out.print(minutes + ":" + seconds + "Wins: " + winsAlphaBeta + " runNumber: " + runNumber +
                            " Number Of Moves " + alphabeta.getNumberOfMoves() + " Size: " + averageChild + "\n\n\n");

                    writerAlphaBeta.append(winsAlphaBeta / (10 - runNumber) + "," + millis + "," + averageChild + "," + alphabeta.getNumberOfMoves() + "\n");
                }

                System.out.println("____________________________________________________________________________");
            }                BruteForce force = new BruteForce(b3);
            force.run();

        }

        writerMiniMax.flush();
        writerMiniMax.close();
        writerAlphaBeta.flush();
        writerAlphaBeta.close();



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

