import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 * pull success pull success
 */
public class boardGeneration extends Connect4
{
    Random rand = new Random();
    int counter = 0;
    int row = 0;
    private int[][] board = new int[6][7];
    private int[] rowCol = new int[2];
    private int players = 1;



    public int[][] generateBoard(int pieces) throws IOException {
        boolean p1 = true;
        boolean win=false;
        int col = rand.nextInt();
        for (int i = 0; i < pieces; i++) {
                col = Math.abs(col % 7);
            checkRowCol(row, col);
                if (p1)
                {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    players = 2;
                    p1 = false;
                } else {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    players = 1;
                    p1 = true;
                }
            win = checkWin(row, col, board);
            if (win)
            {
                clearboard();

            }
                col = rand.nextInt();
            }
        //printBoard(board);
        return board;
    }

//    /**
//     * Checking verical columns for wins (4 in a row)
//     * @param row
//     * @param col
//     * @param board
//     * @return
//     */
//    public boolean checkWin(int row, int col, int[][] board)
//    {
//        int verticalIndex=0;
//        int horizontalIndex = 0;
//        int diagonalIndexLeft = 0;
//        int diagonalIndexRight = 0;
//        int diagonalIndexRow = 0;
//        int diagonalIndexCol=0;
//
//        Boolean checkWin=false;
//        //Vertical checking
//        int piecesInCol =0;
//        int piecesInRow = 0;
//        int temp=0;
//        temp = board[0][col];
//        for(int i=0;i<board.length;i++)
//        {
//            if(board[i][col]!=0)
//            {
//                piecesInCol++;
//            }
//        }
//        for(int i=0;i<piecesInCol;i++)
//        {
//            if (temp == 0) {
//                break;
//            }
//            if (temp == board[i][col]) {
//                verticalIndex++;
//            } else {
//                temp = board[i][col];
//                verticalIndex = 1;
//            }
//            if (verticalIndex == 4) {
//                //System.out.println("caught vertical");
//                checkWin = true;
//                return checkWin;
//            }
//        }
//        //
//        for (int i = 0; i < 6; i++) {
//            for (int j = 1; j < 7; j++)
//            {
//                if (board[i][j] != 0 && board[i][j] == board[i][j - 1]) {
//                    horizontalIndex++;
//                } else {
//                    horizontalIndex = 1;
//                }
//                if (horizontalIndex == 4) {
//                    checkWin = true;
//                    //System.out.println("caught horizontal");
//                    return checkWin;
//                }
//            }
//        }
//        for (int i = 0; i < 6; i++)
//        {
//            for (int j = 1; j < 7; j++) {
//                if (i + j >= 6) break;
//                if (board[j][i + j] != 0 && board[j - 1][(j + i) - 1] == board[j][i + j]) {
//                    diagonalIndexLeft++;
//                } else
//                {
//                    diagonalIndexLeft = 1;
//                }
//                if (diagonalIndexLeft == 4 || diagonalIndexRow == 4 ) {
//                    checkWin = true;
//                    //System.out.println("Caught diagonal bottom left to top right");
//                    //printBoard(board);
//                    return checkWin;
//                }
//            }
//        }
//        for (int i = 0; i < 6; i++) {
//            for (int j = 1; j < 7; j++)
//            {
//                if(i+j>=6)break;
//                if (board[j + i][j] != 0 && board[(i + j) - 1][j - 1] == board[i + j][j]) {
//                    diagonalIndexRow++;
//                } else {
//                    diagonalIndexRow = 1;
//                }
//                if (diagonalIndexRow == 4 ) {
//                    checkWin = true;
//                    //System.out.println("Caught diagonal row");
//                    //printBoard(board);
//                    return checkWin;
//                }
//            }
//
//        }
//            for (int i = 0; i < 6; i++) {
//                for (int j = 1; j < 7; j++)
//                {
//                    diagonalIndexRight=0;
//                    if(i-j<0)break;
//                    if (board[j][i - j] != 0 && board[j - 1][(i - j) + 1] == board[j][i - j]) {
//                        diagonalIndexRight++;
//                    } else {
//                        diagonalIndexRight = 1;
//                    }
//                    if (diagonalIndexRight == 4 ) {
//                        checkWin = true;
//                        //System.out.println("Caught diagonal righty");
//                        //printBoard(board);
//                        return checkWin;
//                    }
//                }
//            }
//        for (int i = 0; i < 6; i++)
//        {
//            for (int j = 4; j >= 0; j--)
//            {
//                if(j-i<0)break;
//                if (board[j-i][j] != 0 && board[(j-i)+1][j + 1] == board[j-i][j]) {
//                    diagonalIndexCol++;
//                } else {
//                    diagonalIndexCol = 1;
//                }
//                if (diagonalIndexCol == 4 ) {
//                    checkWin = true;
//                    //System.out.println("Caught diagonal col");
//                    //printBoard(board);
//                    return checkWin;
//                }
//            }
//        }
//        return checkWin;
//    }
//
//    private int[] checkRowCol(int row, int col)
//    {
//        if (getBoard()[row][col] == 0) {
//            //board[row][col] = 1;
//            getRowCol()[0] = row;
//            getRowCol()[1] = col;
//            //System.out.println(row + " " + col);
//            return getRowCol();
//        } else if (getBoard()[row][col] != 0 && row < 5)
//        {
//            row += 1;
//            return checkRowCol(row, col);
//
//        } else
//        {
//            row = 0;
//            return checkRowCol(row, generateCol());
//
//        }
//
//    }
public int checkWin(Board b){
    int aiScore = 0, humanScore = 0;
    for(int i=5;i>=0;--i) {
        for (int j = 0; j <= 6; ++j) {
            if (b.board[i][j] == 0) continue;

            //Checking cells to the right
            if (j <= 3) {
                for (int k = 0; k < 4; ++k) {
                    if (b.board[i][j + k] == 1) aiScore++;
                    else if (b.board[i][j + k] == 2) humanScore++;
                    else break;
                }
                if (aiScore == 4) return 1;
                else if (humanScore == 4) return 2;
                aiScore = 0;
                humanScore = 0;
            }

            //Checking cells up
            if (i >= 3) {
                for (int k = 0; k < 4; ++k) {
                    if (b.board[i - k][j] == 1) aiScore++;
                    else if (b.board[i - k][j] == 2) humanScore++;
                    else break;
                }
                if (aiScore == 4) return 1;
                else if (humanScore == 4) return 2;
                aiScore = 0;
                humanScore = 0;
            }

            //Checking diagonal up-right
            if (j <= 3 && i >= 3) {
                for (int k = 0; k < 4; ++k) {
                    if (b.board[i - k][j + k] == 1) aiScore++;
                    else if (b.board[i - k][j + k] == 2) humanScore++;
                    else break;
                }
                if (aiScore == 4) return 1;
                else if (humanScore == 4) return 2;
                aiScore = 0;
                humanScore = 0;
            }

            //Checking diagonal up-left
            if (j >= 3 && i >= 3) {
                for (int k = 0; k < 4; ++k) {
                    if (b.board[i - k][j - k] == 1) aiScore++;
                    else if (b.board[i - k][j - k] == 2) humanScore++;
                    else break;
                }
                if (aiScore == 4) return 1;
                else if (humanScore == 4) return 2;
                aiScore = 0;
                humanScore = 0;
            }
        }
    }

    for(int j=0;j<7;++j){
        //Game has not ended yet
        if(b.board[0][j]==0)return -1;
    }
    //Game draw!
    return -1;
}

    private int generateCol() {
        int col = rand.nextInt();
        //System.out.println(Math.abs(col % 7));
        col = Math.abs(col % 7);

        return col;

    }

    public void boardWriter(int[][] board) throws IOException
    {
        File file = new File("boards.txt");
        if(!file.createNewFile()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file,true);
        writer.append(counter + ":\n");
        for (int f = board.length - 1; 0 <= f; f--) {

            for (int k = 0; k < board[f].length; k++)
            {
                writer.append(board[f][k] + " ");
            }
            writer.append("\n");
        }
        writer.append("\n");
        writer.flush();
        writer.close();
        counter++;
    }

    public void clearboard() {
        for (int f = getBoard().length - 1; 0 <= f; f--) {

            for (int k = 0; k < getBoard()[f].length; k++) {
                getBoard()[f][k] = 0;
            }
        }
        setBoard(new int[6][7]);
        rowCol = new int[2];
        players = 1;
        counter = 0;
        row = 0;

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


    public int[][] getBoard() {
        return board;
    }

    public int[] getRowCol() {
        return rowCol;
    }

    public int getPlayers() {
        return players;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
}