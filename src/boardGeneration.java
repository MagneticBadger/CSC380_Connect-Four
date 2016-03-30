import javafx.scene.transform.MatrixType;

import java.util.Random;

/**
 * Created by krm1929 on 3/2/2016.
 */
public class boardGeneration extends Connect4 {
    private int[][] board = new int[6][7];
    Random rand = new Random();
    int[] rowCol = new int[2];

    public int[][] generateBoard()
    {
        int col = rand.nextInt();
        System.out.println(Math.abs(col % 7));

        int row = 0;

        for (int i = 0; i < 5; i++)
        {
            col = Math.abs(col % 7);
            checkRowCol(row, col);
            board[rowCol[0]][rowCol[1]] = 1;


            for (int f = board.length - 1; 0 <= f; f--) {

                for (int k = 0; k < board[f].length; k++) {
                    System.out.print(board[f][k] + " ");
                }
                System.out.println();
            }
            System.out.println();

        }

        for (int i = board.length - 1; 0 <= i; i--) {
            /*
            while(board[row][col]!=0&&row<board[i].length)
            {
                row++;
                if(board[row][col]==0)
                {
                    board[row][col]=1;
                    break;
                }
                if(row>5)
                {
                    board[row-1][col] =1;
                    break;
                }
            }
            */

            //not doing anything
            for (int j = 0; j < board[i].length; j++) {
                //board[0][col] = 1;
            }
        }


        return board;
    }

    private int[] checkRowCol(int row, int col)
    {
        if (board[row][col] == 0) {
            //board[row][col] = 1;
            rowCol[0] = row;
            rowCol[1] = col;
            System.out.println(row + " " + col);
            return rowCol;
        } else if (row==0)
        {
            return checkRowCol(0, generateCol());

        } else
        {
            row++;
            return checkRowCol(row, col);

        }

    }

    private int generateCol() {
        int col = rand.nextInt();
        System.out.println(Math.abs(col % 7));
        col = Math.abs(col % 7);

        return col;

    }

}