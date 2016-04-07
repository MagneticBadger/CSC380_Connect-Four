import java.util.Random;
import java.util.Scanner;

/**
 * Created by krm1929 on 4/6/2016.
 */
public class miniMax
{
    private Board b;
    private Random rand;
    private int nextMoveLocation=-1;
    private int maxDepth = 8;

    public miniMax(Board b){
        this.b = b;
        rand = new Random();
    }
    public void letOpponentMove()
    {
        System.out.println("Your move (1-7): ");
        int move = Math.abs((rand.nextInt()*100)%6);
        while(move<1 || move > 7 || !b.isLegalMove(move)){
            System.out.println("Invalid move.\n\nYour move (1-7): ");
            move = Math.abs((rand.nextInt()*100)%6);
        }

        b.placeMove(move, 1);
    }

    int calculateScore(int aiScore, int moreMoves){
        int moveScore = 4 - moreMoves;
        if(aiScore==0)return 0;
        else if(aiScore==1)return 1*moveScore;
        else if(aiScore==2)return 10*moveScore;
        else if(aiScore==3)return 100*moveScore;
        else return 1000;
    }
    public int evaluateBoard(Board b) {

        int aiScore = 1;
        int score = 0;
        int blanks = 0;
        int k = 0, moreMoves = 0;
        for (int i = 5; i >= 0; --i) {
            for (int j = 0; j <= 6; ++j) {

                if (b.getBoard()[i][j] == 0 || b.getBoard()[i][j] == 2) continue;

                if (j <= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.getBoard()[i][j + k] == 1) aiScore++;
                        else if (b.getBoard()[i][j + k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }

                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c;
                            for (int m = i; m <= 5; m++) {
                                if (b.getBoard()[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.getBoard()[i - k][j] == 1) aiScore++;
                        else if (b.getBoard()[i - k][j] == 2) {
                            aiScore = 0;
                            break;
                        }
                    }
                    moreMoves = 0;

                    if (aiScore > 0) {
                        int column = j;
                        for (int m = i - k + 1; m <= i - 1; m++) {
                            if (b.getBoard()[m][column] == 0) moreMoves++;
                            else break;
                        }
                    }
                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.getBoard()[i][j - k] == 1) aiScore++;
                        else if (b.getBoard()[i][j - k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0)
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c;
                            for (int m = i; m <= 5; m++) {
                                if (b.getBoard()[m][column] == 0) moreMoves++;
                                else break;
                            }
                        }

                    if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                    aiScore = 1;
                    blanks = 0;
                }

                if (j <= 3 && i >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.getBoard()[i - k][j + k] == 1) aiScore++;
                        else if (b.getBoard()[i - k][j + k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j + c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (b.getBoard()[m][column] == 0) moreMoves++;
                                else if (b.getBoard()[m][column] == 1) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }

                if (i >= 3 && j >= 3) {
                    for (k = 1; k < 4; ++k) {
                        if (b.getBoard()[i - k][j - k] == 1) aiScore++;
                        else if (b.getBoard()[i - k][j - k] == 2) {
                            aiScore = 0;
                            blanks = 0;
                            break;
                        } else blanks++;
                    }
                    moreMoves = 0;
                    if (blanks > 0) {
                        for (int c = 1; c < 4; ++c) {
                            int column = j - c, row = i - c;
                            for (int m = row; m <= 5; ++m) {
                                if (b.getBoard()[m][column] == 0) moreMoves++;
                                else if (b.getBoard()[m][column] == 1) ;
                                else break;
                            }
                        }
                        if (moreMoves != 0) score += calculateScore(aiScore, moreMoves);
                        aiScore = 1;
                        blanks = 0;
                    }
                }
            }
        }
        return score;
    }

    public int minimax(int depth, int turn){
        int gameResult = b.gameResult(b);
        if(gameResult==1)return Integer.MAX_VALUE;
        else if(gameResult==2)return Integer.MIN_VALUE;
        else if(gameResult==0)return 0;

        if(depth==maxDepth)return evaluateBoard(b);

        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
        for(int j=0;j<=6;++j){
            if(!b.isLegalMove(j)) continue;

            if(turn==1){
                b.placeMove(j, 1);
                int currentScore = minimax(depth+1, 2);
                maxScore = Math.max(currentScore, maxScore);
                if(depth==0){
                    System.out.println("Score for location "+j+" = "+currentScore);
                    if(maxScore==currentScore) nextMoveLocation = j;
                }
            }else if(turn==2){
                b.placeMove(j, 2);
                int currentScore = minimax(depth+1, 1);
                minScore = Math.min(currentScore, minScore);
            }
            b.undoMove(j);
        }
        return turn==1?maxScore:minScore;
    }
    public void startMinimax()
    {//
        while(true){
            letOpponentMove();
            b.printBoard();

            int gameResult = b.gameResult(b);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}

            b.placeMove(getAIMove(), 2);
            gameResult = b.gameResult(b);
            if(gameResult==1){System.out.println("AI Wins!");break;}
            else if(gameResult==2){System.out.println("You Win!");break;}
            else if(gameResult==0){System.out.println("Draw!");break;}
        }

    }
    public int getAIMove(){
        nextMoveLocation = -1;
        minimax(0, 1);
        return nextMoveLocation;
    }

}

