import java.util.Random;

/**
 * Created by Michael Suggs on 4/7/16.
 */
public class MiniMax {

    private Board b;
    //    private Scanner sca
    Random rand = new Random();
    private int nextMoveLocation=-1;
    private int maxDepth = 6;

    public MiniMax(Board b)
    {
        this.b = b;
    }

    //Opponent's turn
    public void letOpponentMove(){

        int move = rand.nextInt(6)+1;
        while(move<1 || move > 7 || !b.isLegalMove(move))
        {
            move = rand.nextInt(6)+1;
        }

        //Assume 2 is the opponent
        b.placeMove(move, 2);
    }

    //Game Result
    public int gameResult(Board b){
        int aiScore = 0, humanScore = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.getBoard()[i][j]==0) continue;

                //Checking cells to the right
                if(j<=3){
                    for(int k=0;k<4;++k){
                        if(b.getBoard()[i][j+k]==1) aiScore++;
                        else if(b.getBoard()[i][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }

                //Checking cells up
                if(i>=3){
                    for(int k=0;k<4;++k){
                        if(b.getBoard()[i-k][j]==1) aiScore++;
                        else if(b.getBoard()[i-k][j]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-right
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.getBoard()[i-k][j+k]==1) aiScore++;
                        else if(b.getBoard()[i-k][j+k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }

                //Checking diagonal up-left
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.getBoard()[i-k][j-k]==1) aiScore++;
                        else if(b.board[i-k][j-k]==2) humanScore++;
                        else break;
                    }
                    if(aiScore==4)return 1; else if (humanScore==4)return 2;
                    aiScore = 0; humanScore = 0;
                }
            }
        }

        for(int j=0;j<7;++j)
        {
            for (int i = 0; i < 6; i++) {
                if(b.getBoard()[j][i]==0)return -99;
            }
            //Game has not ended yet

        }
        //Game draw!
        return 0;
    }

    int calculateScore(int aiScore, int moreMoves){
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
    }
    //Evaluate board favorableness for AI
    public int evaluateBoard(Board b){

        int aiScore=1;
        int score=0;
        int blanks = 0;
        int k=0, moreMoves=0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){

                if(b.board[i][j]==0 || b.board[i][j]==2) continue;

                if(j<=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j+k]==1)aiScore++;
                        else if(b.board[i][j+k]==2){aiScore=0;blanks = 0;break;}
                        else blanks++;
                    }

                    moreMoves = 0;
                    if(blanks>0)
                        for(int c=1;c<4;++c){
                            int column = j+c;
                            for(int m=i; m<= 5;m++){
                                if(b.board[m][column]==0)moreMoves++;
                                else break;
                            }
                        }

                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;
                    blanks = 0;
                }

                if(i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j]==1)aiScore++;
                        else if(b.board[i-k][j]==2){aiScore=0;break;}
                    }
                    moreMoves = 0;

                    if(aiScore>0){
                        int column = j;
                        for(int m=i-k+1; m<=i-1;m++){
                            if(b.board[m][column]==0)moreMoves++;
                            else break;
                        }
                    }
                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;
                    blanks = 0;
                }

                if(j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i][j-k]==1)aiScore++;
                        else if(b.board[i][j-k]==2){aiScore=0; blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0)
                        for(int c=1;c<4;++c){
                            int column = j- c;
                            for(int m=i; m<= 5;m++){
                                if(b.board[m][column]==0)moreMoves++;
                                else break;
                            }
                        }

                    if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                    aiScore=1;
                    blanks = 0;
                }

                if(j<=3 && i>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j+k]==1)aiScore++;
                        else if(b.board[i-k][j+k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j+c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        }
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }

                if(i>=3 && j>=3){
                    for(k=1;k<4;++k){
                        if(b.board[i-k][j-k]==1)aiScore++;
                        else if(b.board[i-k][j-k]==2){aiScore=0;blanks=0;break;}
                        else blanks++;
                    }
                    moreMoves=0;
                    if(blanks>0){
                        for(int c=1;c<4;++c){
                            int column = j-c, row = i-c;
                            for(int m=row;m<=5;++m){
                                if(b.board[m][column]==0)moreMoves++;
                                else if(b.board[m][column]==1);
                                else break;
                            }
                        }
                        if(moreMoves!=0) score += calculateScore(aiScore, moreMoves);
                        aiScore=1;
                        blanks = 0;
                    }
                }
            }
        }
        return score;
    }

    public int minimax(int depth, int turn)
    {
        int gameResult = gameResult(b);
        if(gameResult==1)return Integer.MAX_VALUE;
        else if(gameResult==2)return Integer.MIN_VALUE;
        //else if(gameResult==0)return 0;

        if(depth==maxDepth)return evaluateBoard(b);

        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        for (int j = 0; j < 7; j++) {
            for (int i = 0; i < 6; i++) {
                if (!b.isLegalMove(i,j)) continue;
                if (turn == 1) {
                    b.placeMoveMiniMac(i,j, 1);
                    int currentScore = minimax(depth + 1, 2);
                    maxScore = Math.max(currentScore, maxScore);
                    if (depth == 0)
                    {
                        if (maxScore == currentScore)
                        {
                            nextMoveLocation = j;
                        }
                    }
                } else if (turn == 2)
                {
                    b.placeMoveMiniMac(i,j, 2);
                    int currentScore = minimax(depth + 1, 1);
                    minScore = Math.min(currentScore, minScore);
                }
                b.undoMoveMiniMax(i,j);
            }
        }
        return turn==1?maxScore:minScore;
    }

    public int getAIMove()
    {
        nextMoveLocation = -1;
        minimax(0, 1);
        return nextMoveLocation;
    }

    /**
     * Algorithm player is equal to 1
     * Simple reflex agent as random Generator is 2
     */
    public void play(){
        int humanMove=-1;
        int player;
        Random rand = new Random();
        player = rand.nextInt(10);
//        if(player<5) {
//            player = 1;
//            letOpponentMove();
//        }
//        else
//            player = 2;

        while(true)
        {
            System.out.println("This is the inital board state ");
            b.printBoard();
            letOpponentMove();
            System.out.println("After reflex Agent ");
            b.printBoard();
            int gameResult = gameResult(b);
            if (gameResult == 1) {
                System.out.println("MiniMax Wins!");
                break;
            } else if (gameResult == 2) {
                System.out.println("Simple Reflex Wins!");
                break;
            } else if (gameResult == 0) {
                System.out.println("Draw!");
            }
            int inde=0;
            b.placeMove(inde = getAIMove(), 1);
            System.out.println("After MiniMax ");
            b.printBoard();
            gameResult = gameResult(b);
            if (gameResult == 1) {
                System.out.println("MiniMax Wins!");
                break;
            } else if (gameResult == 2) {
                System.out.println("Simple Reflex Wins!");
                break;
            } else if (gameResult == 0) {
                System.out.println("Draw!");
            }
        }

    }
    public void printPlayerChange()
    {

    }

}
