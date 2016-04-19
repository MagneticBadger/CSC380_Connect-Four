import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by mjs3607 on 4/18/2016.
 */
public class GameBoard
{
    public static Board b;
    //Gameboard size dimensions
    public static int rows = 6;
    public static int columns = 7;
    //Player that goes first
    public static int turn = 2;
    public static int playOutsX = 3000; //adjust iterations for computer player 1
    public static int playOutsO = 3000; //adjust iterations for computer player 2
    public static int bestAI = 0;
    public static boolean humanPlayer1 = true;
    public static boolean humanPlayer2 = false;
    static Scanner play = new Scanner(System.in);
    public static int[][] board;
   public static Random rand = new Random();
    public GameBoard(Board b)
    {
        this.board = b.board;
        this.b = b;
    }

    public void Run()
    {
        long startTime = System.nanoTime();
        boolean gameLoop = true;
        while(gameLoop){
            playTurn(turn,board);
            if (checkWin(board, turn))
            {
                break;
            }
            else if (checkTie(board))
            {
                break;
            }
            playAI(board);
            if (checkWin(board, turn))
            {
                break;
            }
            else if (checkTie(board))
            {
                System.out.println("It's a tie!");
                break;
            }
            if (turn == 2){turn = 1;} //changes turn
            else{turn=2;}

        }
        long elapsedTime = System.nanoTime() - startTime;
        //System.out.println(elapsedTime);

    }
    //sees if board is full if there is no winner
    public static boolean checkTie(int[][] gameBoard) {
        if (!checkWin(gameBoard, turn))
        {
            for (int i = 0; i < rows; i++)
            {
                for (int j = 0; j < columns; j++)
                {
                    if (gameBoard[i][j] == 0)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void buildBoard(int[][] boardState, char player)
    {

        System.out.println("");

        for(int i = 0; i < rows; i++){

            for(int j = 0; j < columns; j++){
                if (j == 0){System.out.print("| ");}
                if (boardState[i][j] == '\0'){boardState[i][j] = 0;} //fills empty spaces with 0


                System.out.print(boardState[i][j] + " | ");
            }
            System.out.println("\n-----------------------------");
        }
    }

    //asks human player to make move
    public static void playTurn (int color, int[][] boardState){

        int piece = 0;
        int move = rand.nextInt(6) + 1;
        while (move < 1 || move > 7 || !b.isLegalMove(move)) {
            move = rand.nextInt(6) + 1;
        }

        //Assume 2 is the opponent
        b.placeMove(move, 2);
    }

    public static boolean checkWin (int[][] boardState, int winner){

        //check horizontal win
        for (int i = 0; i < rows; i++){
            int horizontalCounter = 0;
            for (int j = 0; j < columns; j++){

                if (boardState[i][j] == winner){
                    horizontalCounter++; //add 1 for every time the color in question comes up
                }

                else{horizontalCounter = 0;} //resets counter if same two pieces are not in a row

                if (horizontalCounter == 4){

                    return true;
                }

            }

        }

        //check vertical win
        for (int i = 0; i < columns; i++){
            int vertCounter = 0;
            for (int j = 0; j < rows; j++){

                if (boardState[j][i] == winner){
                    vertCounter++; //add 1 for every time the color in question comes up
                }

                else{vertCounter = 0;} //resets counter if same two pieces are not in a row

                if (vertCounter == 4){

                    return true;
                }

            }

        }

        for (int i = 0; i < columns; i++){

            for (int j = 0; j < rows; j++){
                //check diagonals down and to the right (negative slope)
                if (j <= rows - 4 && i <= columns - 4) //starts 4 up from the bottom and 4 away from the right to prevent OutOfIndex
                {
                    if (boardState[j][i]==winner
                            && boardState[j+1][i+1]==winner
                            && boardState[j+2][i+2]==winner
                            && boardState[j+3][i+3]==winner){

                        return true;
                    }
                }
                //check diagonals down and to the left (positive slope)
                if (j >= 3 && i <= 3) //starts 4 down from the top and 4 away from the left to prevent OutOfIndex
                {
                    if (boardState[j][i]==winner
                            && boardState[j-1][i+1]==winner
                            && boardState[j-2][i+2]==winner
                            && boardState[j-3][i+3]==winner){

                        return true;
                    }
                }
            }

        }

        return false;

    }

    public static void playAI (int[][] gameBoard)
    {
        Node root = new Node(gameBoard);
        int playOuts; //number of iterations to go through (level of MCTS)
        if (turn == 2){playOuts = playOutsO;}
        else {playOuts = playOutsX;}
        for (int i = 0; i < playOuts; i++) //runs through MCTS algorithm sequence for number of iterations
        {
            root.monteCarloTreeSearch();
        }
        double bestNodeValue = 0;
        double nodeValue = 0;
        for (int j = 0; j < root.children.size(); j++) //iterate through root's children to find node with greatest average reward (estimated value)
        {
            if (root.children.get(j) == null){continue;} //accounts for place-holders in root node's children that occurs when columns fill up
            nodeValue = root.children.get(j).nodeWins / root.children.get(j).nodeVisits; //calculate average reward
            System.out.println(j + ": " + root.children.get(j).nodeWins +" " + root.children.get(j).nodeVisits); //prints each child's number of wins and number of visits
            if (nodeValue >= bestNodeValue)
            {
                bestAI = j; //stores index of best child to drop piece, as index correlates with column drop
                bestNodeValue = nodeValue;

            }
        }

        System.out.println(bestAI);
        for(int i = (rows-1); i >= 0; i--)
        {
            if (gameBoard[i][bestAI]==0)
            {

                gameBoard[i][bestAI]= turn; //drops piece based on child with highest value
                break;

            }
        }
    }

}
