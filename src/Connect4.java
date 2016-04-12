import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4 {
    //    public static int currentPlayer;
//    static int winner = 0;
    public static int boardNumber = 0;
    public static int test = 100;
    private static Board b;
    private static boardGeneration bGenerator = new boardGeneration();
    private String algorithmName = "MiniMax";

    public static void main(String[] args) throws IOException
    {
        int runNumber = 10;
        double numberOfWins=0.0, time=0.0, space=0.0, numberOfMoves=0.0;
        Timer timer = new Timer();
        long startTime;
        long endTime;

        int piecesInBoard = 0;
        boardNumber++;
        while(runNumber !=0) {
            b = new Board(bGenerator.generateBoard(12));

            startTime = System.currentTimeMillis();
            MiniMax minimax = new MiniMax(b);
            boolean winner = minimax.play();
            if (winner)
            {
                numberOfWins++;
            }
            endTime = System.currentTimeMillis();

            long millis = endTime - startTime;
            int seconds = (int) (millis / 1000) % 60;
            int minutes = (int) ((millis / (1000 * 60)) % 60);
            System.out.print(minutes + ":" + seconds + "Wins: " + numberOfWins + " runNumber: " + runNumber+
                            "Number Of Moves" + minimax.getNumberOfMoves()+ "Size: "+ (minimax.getHeap()/1024)/1024+ "\n\n\n");
            runNumber--;
        }
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

    public void dataWriter(double numberOfWins, double time, double space, double numberOfMoves) throws IOException {
        File file = new File("runtimeData.csv");

        if (!file.createNewFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file, true);

        writer.append(numberOfWins + ", " + time + ", " + space + ", " + numberOfMoves);
        writer.flush();
        writer.close();
    }


}
