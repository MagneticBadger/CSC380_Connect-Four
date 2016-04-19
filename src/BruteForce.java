import java.util.Random;

/**
 * Created by mjs3607 on 4/18/2016.
 */
public class BruteForce
{
    public static Board b;
    public static int[][] board ;
    public static int player= 1;
    public BruteForce(Board b)
    {
        this.b =b;
        this.board  =b.board;
    }
    public static int findFour() {

        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(board[i][j]==0) continue;

                //Checking cells to the right
                if(j<=3){
                    for(int k=0;k<4;++k){
                        if(board[i][j+k]==1) aiScore++;
                        else if(board[i][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking cells up
                if(i>=3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j]==1) aiScore++;
                        else if (board[i-k][j]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-right
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j+k]==1) aiScore++;
                        else if(board[i-k][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-left
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(board[i-k][j-k]==1) aiScore++;
                        else if(board[i-k][j-k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4||humanScore==4)
                        return -99;
                    aiScore = 0; humanScore = 0;
                }
            }
        }

        for(int j=0;j<7;++j){
            //Game has not ended yet
            if(board[0][j]==0)return -1;
        }
        return 0;
    }


    //inputs: board
    //return: boolean if the board is full or not
    private static boolean full(int board[][])
    {
        for(int i=0;i<6;i++){
            for(int j=0;j<7;j++){
                //at least one space has not been placed, then not full
                if(board[i][j]!=1 && board[i][j]!=2)
                    return false;
            }
        }
        //if you get to this point, no empty space was found
        return true;
    }//full

    //takes in the board
    //outputs the board status to the screen
    //no return

    public static void run()
    {
        Random rand = new Random();

        //initialize variables
        int winner=0;

        //use do while cause you always want to execute once
        do{

            int row = rand.nextInt(6)+1;
            while(row<1 || row > 7)
            {
                row = rand.nextInt(6)+1;
            }

            //try to place in the row wanted
            //use row-1 for the actual array index starting at 0, not user input
            boolean placed = b.placeMove(row,player);

            //if you couldnt place it try again
            while(!placed)
            {
                //if out of bounds
                if(row>6 || row<0){
                    System.out.println("Sorry the row is invalid");
                }else{
                    System.out.print("Sorry row "+ row +" is full.\n\nplease select a row:");
                }

                row = rand.nextInt(6)+1;
                while(row<1 || row > 7)
                {
                    row = rand.nextInt(6)+1;
                }

                //try to place in the row wanted
                //use row-1 for the actual array index starting at 0, not user input
                placed = b.placeMove(row,player);

            }
            if(player == 1)
            {
                player = 2 ;
            }
            else
            {
                player = 1;
            }
            //find winner if one exists
            winner = findFour();

            //print status
            b.printBoard();

            //stop when there are four in a row or its full
        }while(!full(board) && winner==-1);

        //print winner statement
        if(winner==1){
            System.out.println("Congradulations "+player+" You Won!!");
        }else if(winner==0){
            System.out.println("Congradulations "+player+" You Won!!");
        }else{
            System.out.println("Sorry, no one won.");
        }
    }
}
