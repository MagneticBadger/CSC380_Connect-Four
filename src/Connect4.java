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
    private String algorithmName = "MiniMaxCodeBytes";

    public static void main(String[] args) throws IOException {
        double numberOfWins, time, space, numberOfMoves;
        Timer timer = new Timer();

        int piecesInBoard = 0;
        boardNumber++;
        b = new Board(bGenerator.generateBoard(16));

        long startTime = System.currentTimeMillis();
        MiniMaxCodeBytes minimax = new MiniMaxCodeBytes(b);
        minimax.play();
        long endTime = System.currentTimeMillis();

        long millis = endTime - startTime;
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        System.out.print(minutes + ":" + seconds + "\n");

        //System.out.println("Board Number: " +boardNumber + "\tPieces in board: " + piecesInBoard);

        Board b2 = new Board(bGenerator.generateBoard(16));

        startTime = System.currentTimeMillis();
        AlphaBeta alphabeta = new AlphaBeta(b2);
        alphabeta.play();
        endTime = System.currentTimeMillis();

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
