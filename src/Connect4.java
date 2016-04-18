import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;

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
    private static boardGeneration bGenerator = new boardGeneration();
    private String algorithmName = "MiniMax.csv";
    public static String fileMini = "miniMaxData.csv";
    public static String fileAlpha = "alphaBetaData.csv";
    static FileWriter writerMiniMax;

    public static File fileMiniMax;
    public static File filealphaBeta;

    public static void main(String[] args) throws IOException
    {
        int runNumber = 10;
        double winsMiniMax=0.0, time=0.0, childCount=0.0, numberOfMoves=0.0,winsAlphaBeta=0.0;
        Timer timer = new Timer();
        long startTime;
        long endTime;
        fileMiniMax = new File(fileMini);
        writerMiniMax = new FileWriter(fileMiniMax,true);
        writerMiniMax.append("WinNumber,Runtime(ms),Amount of Children,Number of Moves\n");
        boardNumber++;

        while(runNumber !=0)
        {
            int[][] tempBoard = new int[6][7];
            Board alphAbeta=new Board(tempBoard);
            runNumber--;
            b = new Board(bGenerator.generateBoard(12));

            startTime = System.currentTimeMillis();
            MiniMax minimax = new MiniMax(b);
            boolean winner = minimax.play();
            if (winner) {
                winsMiniMax++;
                endTime = System.currentTimeMillis();
                double averageChild = 0.0;
                for (Integer mark : minimax.getRunningChilTotal()) {
                    //System.out.println(mark);
                    averageChild += mark;
                }
                averageChild = averageChild / minimax.getRunningChilTotal().size();
                long millis = endTime - startTime;
                //double fullSeconds = (millis);
                int seconds = (int) (millis / 1000) % 60;
                int minutes = (int) ((millis / (1000 * 60)) % 60);
                System.out.print(minutes + ":" + seconds + "Wins: " + winsMiniMax + " runNumber: " + runNumber +
                        " Number Of Moves " + minimax.getNumberOfMoves() + " Size: " + averageChild + "\n\n\n");

                writerMiniMax.append(winsMiniMax / (10 - runNumber) + "," + millis + "," + averageChild + "," + minimax.getNumberOfMoves() + "\n");
            }
        }
        writerMiniMax.flush();
        writerMiniMax.close();
        //System.out.println("Board Number: " +boardNumber + "\tPieces in board: " + piecesInBoard);

        Board b2 = new Board(bGenerator.generateBoard(12));


        startTime = System.currentTimeMillis();
        AlphaBeta alphabeta = new AlphaBeta(b2);
        alphabeta.play();
        endTime = System.currentTimeMillis();
        long millis = endTime - startTime;
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        millis = endTime - startTime;
        seconds = (int) (millis / 1000) % 60;
        minutes = (int) ((millis / (1000 * 60)) % 60);
        System.out.print(minutes + ":" + seconds);


    }

    public static void dataWriter(double numberOfWins, double time, double space, double numberOfMoves,FileWriter writer) throws IOException {

        //file.createNewFile();


    }
}
