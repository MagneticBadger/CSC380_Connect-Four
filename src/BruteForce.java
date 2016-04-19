import java.util.Random;

/**
 * Created by mjs3607 on 4/18/2016.
 */
public class BruteForce
{
    public static Board b;
    public static int[][] board ;
    public static int player= 1;
    private static int numberOfMoves=0;
    public BruteForce(Board b)
    {
        this.b =b;
        this.board  =b.board;
    }
    public static int findFour()
    {

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

        for(int j=0;j<7;++j)
        {
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

    public  int run()
    {
        Random rand = new Random();
        boolean win=true;
        //initialize variables
        int winner=0;


        while (full(board)||win)
        {
            int col = rand.nextInt(7);

            boolean notValid = b.placeMove(col,player);
            while(!notValid)
            {
                    col = rand.nextInt(7);
                    notValid = b.placeMove(col,player);
            }
            if(findFour()==-99)
            {
                win= false ;
                System.out.println("player " + player + "is the winner");
                break;
            }
            if(player==1)
            {
                numberOfMoves = getNumberOfMoves() + 1;
                player=2;
            }
            else
            {
                player=1;
            }
        }
        return player;
    }

    public  int getNumberOfMoves() {
        return numberOfMoves;
    }
}
