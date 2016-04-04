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
    int[] rowCol = new int[2];
    private int players = 1;
    int counter = 0;
    int winCounter=0;

    int row = 0;

    public int[][] generateBoard() throws IOException {
        boolean p1 = true;
        boolean win=false;
        int col = rand.nextInt();


        for (int i = 0; i < 32; i++) {
            col = Math.abs(col % 7);
            checkRowCol(row, col);
            win = checkWin(rowCol[0], rowCol[1], board, players);
            if (!win)
            {
                if (p1) {
                    board[rowCol[0]][rowCol[1]] = players;
                    if (!win) {
                        win = checkWin(rowCol[0], rowCol[1], board, players);
                    }
                    players = 2;
                    p1 = false;
                } else {
                    board[rowCol[0]][rowCol[1]] = players;
                    if (!win)
                    {
                        checkWin(rowCol[0], rowCol[1], board, players);
                    }
                    players = 2;
                    players = 1;
                    p1 = true;
                }
                col = rand.nextInt();
            }
            else
            {
                System.out.println("break that ish");
                break;
            }
        }
        boardWriter(board);
        return board;
    }
    private boolean checkWin(int row,int col, int[][] board,int player)
    {
        Boolean checkWin=false;
        //Horizontal checking
        winCounter=0;
        for(int i=0;i<board.length-1;i++)
        {
            if(board[i][col]!=0)
            {
                winCounter++;
            }
        }
        System.out.println(winCounter);
        row=0;
        while(row!=6&&winCounter!=4)
        {
            counter=1;
            for(int f = board.length - 1; 0 <= f; f--)
            {
                if(row==6)
                {
                    break;
                }
                if (counter==4)
                {
                    checkWin=true;
                    break;
                }
                if(board[row][col]==player)
                {
                    System.out.println("Row:"+(row+1)+" Col:"+(col+1));
                    System.out.println("Player:" + players);
                    System.out.println("Board:" +board[row][col]);
                    System.out.println("Win Counter:" +counter);
                    printBoard(board);
                    counter++;
                }
                row++;
            }
        }
        return checkWin;
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
        for (int f = board.length - 1; 0 <= f; f--) {

            for (int k = 0; k < board[f].length; k++) {
                board[f][k] = 0;
            }

        }
    }
    private void printBoard(int[][] board)
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