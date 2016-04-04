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
            for (int i = 0; i < 6; i++) {
                col = Math.abs(col % 7);
                checkWin(row,col,board,players);
                if (p1) {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    checkWin(row,col,board,players);
                    players = 2;
                    p1 = false;
                } else {
                    getBoard()[getRowCol()[0]][getRowCol()[1]] = getPlayers();
                    checkWin(row,col,board,players);
                    players = 1;
                    p1 = true;
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
     * @param player
     * @return
     */
    public boolean checkWin(int row,int col, int[][] board,int player)
    {
        System.out.println((row+1)+" "+ (col+1)+" "+player);
        int verticalIndex=0;
        Boolean checkWin=false;
        //Vertical checking
        int piecesInCol =0;
        int temp=0;

        for(int i=0;i<board.length;i++)
        {
            if(board[i][col]!=0)
            {
                System.out.println("temp" + board[i][col]);
                piecesInCol++;
            }
        }

        for(int i=0;i<piecesInCol;i++)
        {
            temp = board[row][col];
        }

        System.out.println(piecesInCol);

        /*while(row!=0&& piecesInCol !=4)
        {
            counter=0;
            for(int f = board.length - 1; 0 <= f; f--)
            {
                System.out.println(board[f][col]);
                if(piecesInCol==0)
                {
                    return checkWin;
                }
                if (counter==3)
                {
                    checkWin=true;
                    System.out.println("broke that ish");
                    break;
                }
                if(board[piecesInCol][col]==player)
                {
                    System.out.println("Row:"+(row+1)+" Col:"+(col+1));
                    System.out.println("Player:" + getPlayers());
                    System.out.println("Board:" +board[row][col]);
                    System.out.println("Row Pieces:" + piecesInCol);
                    System.out.println("Pieces in Row:" + counter);
                    counter++;
                }
                printBoard(board);
                piecesInCol--;
            }
        }
        */
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