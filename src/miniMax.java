import java.util.Scanner;

/**
 * Created by krm1929 on 4/6/2016.
 */
public class miniMax
{
    private int mySymbol = 1;
    private int opponentSymbol = 2;
    private int searchLimit;
    private int maxColumn;
    Board board;


    public miniMax(int limit,Board b)
    {
        searchLimit = limit;
        this.board=b;
    }

    /**
     * This method calls the miniMaxValue method and it returns the
     * column that corresponds to the column with the highest minimax
     * value.
     */
    public int miniMaxDecision(Board board) {
        miniMaxValue(board, 0, mySymbol);
        return maxColumn;
    }

    /**
     * This method implements the minimax algorithm. It returns either the
     * highest minimax value that is possible for the max player
     * starting from the passed board state or the lowest minimax value
     * that is possible for the min player starting from the passed board
     * state. It also modifies the global variable maxColumn that is
     * associated to the column with the highest minimax value.
     */
    private int miniMaxValue(Board board, int depth, int playerToMove) {
        //check if the board is in a terminal (winning) state and
        //return the maximum or minimum utility value (255 - depth or
        //0 + depth) if the max player or min player is winning.
        if (board.isFinished() != -1)
            if (board.isFinished() == mySymbol)
                return 255 - depth;
            else return 0 + depth;
        //check if the depth has reached its limit or if the
        //board is in a terminal (tie) state and return its utility value.
//        if (depth == searchLimit || board.isTie()) {
//            return board.evaluateContent();
//        }
        depth = depth + 1;
        if (playerToMove == mySymbol) {
            int max = Integer.MIN_VALUE;
            int column = 0;
            for (int i = 0; i < board.columns; i++)
                if (board.isLegalMove(i)) {
                    board.insert(i, mySymbol);
                    int value = miniMaxValue(board, depth, opponentSymbol);
                    if (max < value) {
                        max = value;
                        column = i;
                    }
                    board.remove(i);
                }
            maxColumn = column;
            return max;
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < board.columns; i++)
                if (board.isLegalMove(i)) {
                    board.insert(i, 'X');
                    int value = miniMaxValue(board, depth, mySymbol);
                    if (min > value)
                        min = value;
                    board.remove(i);
                }
            return min;
        }
    }
}