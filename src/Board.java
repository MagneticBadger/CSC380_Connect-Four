import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by krm1929 on 4/6/2016.
 */
public class Board {
    public final int rows = 6;
    public final int columns = 7;
    public int[][] board;
    final static int boardPieces = 20;
    int[] rowsEdited = new int[42];

    public Board(int[][] board) {
        this.board = board;
    }

    //Placing a Move on the board
    public boolean placeMove(int column, int player) {

        if (!isLegalMove(column)) {
            System.out.println("Illegal move!");
            return false;
        }
        for (int i = 0; i < 6; i++) {
            if (board[i][column] == 0) {
                board[i][column] = player;
                return true;
            }
        }
        return false;
    }

    public boolean placeMoveMiniMac(int row, int column, int player) {
        if (board[row][column] == 0) {
            board[row][column] = player;
            return true;
        }
        return false;
    }

    public boolean isLegalMove(int column) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isLegalMove(int row, int column) {
        return board[row][column] == 0;
    }

    public int findRow(int column) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == 0) {
                return i;
            }
        }
        return -1;
    }

    public void undoMove(int column) {
        for (int i = 0; i <= 5; ++i) {
            if (board[i][column] != 0) {
                board[i][column] = 0;
                break;
            }
        }
    }

    public void undoMoveMiniMax(int row, int column) {
        if (board[row][column] != 0) {
            board[row][column] = 0;
        }
    }

    public void printBoard() {
        String s = (char) 27 + "[36mbla-bla-bla";
        System.out.println("Row");
        for (int f = board.length - 1; 0 <= f; f--) {
            s = (char) 27 + "[30m" + " " + (f + 1) + " " + "   | ";
            System.out.print(s);
            for (int k = 0; k < board[f].length; k++) {
                if (board[f][k] == 1) {
                    s = (char) 27 + "[31;1m" + board[f][k] + " ";
                    System.out.print(s);
                } else if (board[f][k] == 2) {
                    s = (char) 27 + "[34;1m" + board[f][k] + " ";
                    System.out.print(s);
                } else {
                    s = (char) 27 + "[30m" + board[f][k] + " ";
                    System.out.print(s);
                }
            }
            System.out.println();
        }
        s = (char) 27 + "[30m" + "      ----------------\n        ";
        System.out.print(s);
        for (int g = 0; g <= board.length; g++) {

            System.out.print(g + 1 + " ");
        }
        System.out.println("Col");
    }

    public void boardWriter(int[][] board, int counter) throws IOException {
        File file = new File("boards.txt");
        if (!file.createNewFile()) {
            file.delete();
        }
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
        counter++;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}
