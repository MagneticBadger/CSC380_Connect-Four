import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 * pull success pull success
 */
public class boardGeneration extends Connect4 {
    private int[][] board = new int[6][7];
    Random rand = new Random();
    private int[] rowCol = new int[2];
    private int players = 1;
    int counter = 0;

    int row = 0;



    public boolean generateBoard() throws IOException {
        boolean p1 = true;
        boolean win=false;
        int col = rand.nextInt();
            for (int i = 0; i < 32; i++) {
                col = Math.abs(col % 7);
                checkRowCol(row, col);
                if (p1) {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    players = 2;
                    p1 = false;
                } else
                {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    players = 1;
                    p1 = true;
                }
                win = checkWin(row,col,board);
                if(win)
                {
                    return win;
                }
                col = rand.nextInt();
            }
            printBoard(board);
        return win;
    }

    /**
     * Checking verical columns for wins (4 in a row)
     * @param row
     * @param col
     * @param board
     * @return
     */
    public boolean checkWin(int row,int col, int[][] board)
    {
        int verticalIndex=0;
        int horizontalIndex=0;
        Boolean checkWin=false;
        //Vertical checking
        int piecesInCol =0;
        int piecesInRow = 0;
        int temp=0;
        temp = board[0][col];
        for(int i=0;i<board.length;i++)
        {
            if(board[i][col]!=0)
            {
                piecesInCol++;
            }
        }
        for(int i=0;i<piecesInCol;i++)
        {
            if(temp==0)
            {
                break;
            }
            if(temp==board[i][col])
            {
                verticalIndex++;
            }
            else
            {
                temp = board[i][col];
                verticalIndex=1;
            }
            if(verticalIndex==4)
            {
                System.out.println("caught vertical");
                checkWin=true;
                return checkWin;
            }
        }
        temp = board[getRowCol()[0]][0];
        for(int i=0;i<board.length-1;i++)
        {
            while(board[getRowCol()[0]][i]==0&&i!=6)
            {
                i++;
                horizontalIndex=0;
            }
            if(temp==board[getRowCol()[0]][i])
            {
                horizontalIndex++;
                System.out.println("Index: "+horizontalIndex+" Row:"+(getRowCol()[0]+1)+" Col:"+ (i+1));
                printBoard(board);
            }
            else
            {
                temp = board[getRowCol()[0]][i];
                horizontalIndex=1;
            }
            if(horizontalIndex==4)
            {
                checkWin=true;
                System.out.println("Broke this ish");
                return checkWin;
            }
        }


        return checkWin;
    }
    private int[] checkRowCol(int row, int col)
    {
        if (getBoard()[row][col] == 0) {
            //board[row][col] = 1;
            getRowCol()[0] = row;
            getRowCol()[1] = col;
            //System.out.println(row + " " + col);
            return getRowCol();
        } else if (getBoard()[row][col] != 0 && row < 5)
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
        board = new int[6][7];
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
}