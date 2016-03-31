import java.io.IOException;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class Connect4
{
    final int boardPieces = 20;

    public static void main(String[] args) throws IOException {
        boardGeneration b = new boardGeneration();
        for (int i = 0; i < 10; i++) {
            b.generateBoard();
            b.clearboard();
        }

    }

}
