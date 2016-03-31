import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class boardGeneration extends Connect4 {
    private int[][] board = new int[6][7];
    Random rand = new Random();
    int[] rowCol = new int[2];
    final int player1 = 1;
    final int player2 = 2;
    int counter = 0;

    public int[][] generateBoard() throws IOException {
        boolean p1 = true;
        int col = rand.nextInt();
        //System.out.println(Math.abs(col % 7));

        int row = 0;

        for (int i = 0; i < 32; i++) {

            col = Math.abs(col % 7);
            checkRowCol(row, col);

            if (p1) {
                board[rowCol[0]][rowCol[1]] = player1;
                p1 = false;
            } else {
                board[rowCol[0]][rowCol[1]] = player2;
                p1 = true;
            }
            col = rand.nextInt();
        }
        boardWriter(board);
        return board;
    }

    private int[] checkRowCol(int row, int col)
    {
        if (board[row][col] == 0) {
            //board[row][col] = 1;
            rowCol[0] = row;
            rowCol[1] = col;
            //System.out.println(row + " " + col);
            return rowCol;
        } else if (board[row][col] != 0 && row < 5)
        {
            row += 1;
            return checkRowCol(row, col);

        } else
        {
            row = 0;
            return checkRowCol(row, generateCol());

        }

    }

    private int generateCol() {
        int col = rand.nextInt();
        //System.out.println(Math.abs(col % 7));
        col = Math.abs(col % 7);

        return col;

    }

    public void boardWriter(int[][] board) throws IOException {

        File file = new File("boards.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file, true);
        writer.append(counter + ":\n");
        for (int f = board.length - 1; 0 <= f; f--) {

            for (int k = 0; k < board[f].length; k++) {
                writer.append(board[f][k] + " ");
            }
            writer.append("\n");
        }
        writer.append("\n");
        writer.flush();
        writer.close();
        //sdsdsd
        counter++;
    }

    public void clearboard() {
        for (int f = board.length - 1; 0 <= f; f--) {

            for (int k = 0; k < board[f].length; k++) {
                board[f][k] = 0;
            }

        }
    }



}