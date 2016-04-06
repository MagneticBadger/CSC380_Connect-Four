import java.io.IOException;

/**
 * Created by krm1929 on 4/6/2016.
 */
public class Board extends boardGeneration {
    public final int rows = 6;
    public final int columns = 7;
    int[][] board = new int[rows][columns];

    public int[][] runBoard() throws IOException
    {
        boolean win;
        boardGeneration b = new boardGeneration();

        win = b.generateBoard(boardPieces);
        while (win) {
            b.clearboard();
            win = b.generateBoard(boardPieces);
            if (!win) {
                break;
            }
        }
        b.boardWriter(b.getBoard());
        board = b.getBoard();
        return board;
    }

    public int isFinished() {
        //check for win horizontally
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < columns - 3; col++)
                if (board[row][col] != ' ' &&
                        board[row][col] == board[row][col + 1] &&
                        board[row][col] == board[row][col + 2] &&
                        board[row][col] == board[row][col + 3])
                    return board[row][col];
        //check for win vertically
        for (int row = 0; row < rows - 3; row++)
            for (int col = 0; col < columns; col++)
                if (board[row][col] != ' ' &&
                        board[row][col] == board[row + 1][col] &&
                        board[row][col] == board[row + 2][col] &&
                        board[row][col] == board[row + 3][col])
                    return board[row][col];
        //check for win diagonally (upper left to lower right)
        for (int row = 0; row < rows - 3; row++)
            for (int col = 0; col < columns - 3; col++)
                if (board[row][col] != ' ' &&
                        board[row][col] == board[row + 1][col + 1] &&
                        board[row][col] == board[row + 2][col + 2] &&
                        board[row][col] == board[row + 3][col + 3])
                    return board[row][col];
        //check for win diagonally (lower left to upper right)
        for (int row = 3; row < rows; row++)
            for (int col = 0; col < columns - 3; col++)
                if (board[row][col] != ' ' &&
                        board[row][col] == board[row - 1][col + 1] &&
                        board[row][col] == board[row - 2][col + 2] &&
                        board[row][col] == board[row - 3][col + 3])
                    return board[row][col];
        return -1;
    }

    public boolean isLegalMove(int column) {
        if (column > 6 || column < 0 || board[0][column] != 0)
            return false;
        return true;
    }

    public boolean insert(int column, int currentPlayer) {
        if (column > 6 || column < 0 || board[0][column] != 0)
            return false;
        else {
            for (int i = rows - 1; i >= 0; i--)
                if (board[i][column] == 0) {
                    board[i][column] = currentPlayer;
                    break;
                }
            return true;
        }
    }

    public void remove(int column) {
        for (int i = 0; i < rows; i++) {
            if (board[i][column] != ' ') {
                board[i][column] = ' ';
                break;
            }
        }
    }
    public void printBoard(int[][] board)
    {
        String s = (char)27 + "[36mbla-bla-bla";
        System.out.println("Row");
        for (int f = board.length - 1; 0 <= f; f--)
        {
            s=(char) 27 + "[30m" + " "+(f+1)+" "+"   | ";
            System.out.print(s);
            for (int k = 0; k < board[f].length; k++)
            {
                if(board[f][k]==1) {
                    s = (char) 27 + "[31;1m" + board[f][k] + " ";
                    System.out.print(s);
                }
                else if(board[f][k]==2)
                {
                    s = (char) 27 + "[34;1m" + board[f][k] + " ";
                    System.out.print(s);
                }
                else
                {
                    s=(char) 27 + "[30m" + board[f][k] + " ";
                    System.out.print(s);
                }
            }
            System.out.println();
        }
        s=(char) 27 + "[30m" + "      ----------------\n        ";
        System.out.print(s);
        for(int g=0;g<=board.length;g++)
        {

            System.out.print(g+1+" ");
        }
        System.out.println("Col");
    }
}
