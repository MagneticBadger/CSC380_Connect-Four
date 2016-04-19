import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mjs3607 on 4/18/2016.
 */
public class Node
{
    static Random randomNumb = new Random();
    static double buffer = 1e-6;
    static char opp;
    static int counter = 0;

    List<Node> children = new ArrayList<Node>();
    int[][] boardState;
    double nodeVisits;
    double nodeWins;
    boolean oppMove = false;

    public Node(int[][] gameBoard) {
        this.boardState = new int[GameBoard.rows][GameBoard.columns];
        this.boardState = gameBoard;
    }

    public Node(int[][] gameBoard, boolean oppMoveIn)
    {
        this.boardState = new int[GameBoard.rows][GameBoard.columns];
        this.boardState = gameBoard;
        this.oppMove = oppMoveIn;
    }

    public void monteCarloTreeSearch()
    {
        List<Node> visitedNodes = new ArrayList<Node>();
        //set opponents character based on current player character
        if (GameBoard.turn == 1){opp = 2;}
        else if (GameBoard.turn == 2){opp = 1;}
        Node currentNode = this;
        visitedNodes.add(this);
        boolean firstIteration = true;
        //Selection: find next optimal node to explore, given that the current node has children to select from
        //first iteration has no children, thus this loop will be skipped for the first iteration each time
        while (currentNode.hasChildren()
                && !GameBoard.checkWin(currentNode.boardState, 2)
                && !GameBoard.checkWin(currentNode.boardState, 1)
                && !GameBoard.checkTie(currentNode.boardState))
        {
            firstIteration = false; //first iteration cannot have children (as it has not reached expansion yet) thus this must become false in this loop
            if (opp == 1){currentNode = currentNode.select();} //selects optimal node based on UCB and follows it
            //else {currentNode = currentNode.egreedy();} for testing against egreedy
            else {currentNode = currentNode.select();}
            if (!currentNode.oppMove) {visitedNodes.add(currentNode);}
        }
        //Simulation/Expansion: simulate (random) game until there is a winner or there is a tie.
        while (!GameBoard.checkWin(currentNode.boardState, 2)
                && !GameBoard.checkWin(currentNode.boardState, 1)
                && !GameBoard.checkTie(currentNode.boardState))
        {
            currentNode.expansion(currentNode.boardState, currentNode.children, firstIteration); //Expands a given node to create child nodes for the current players turn
            firstIteration = false; //after any node has expanded, it is impossible for it to be the root node
            currentNode.oppExpand(currentNode.boardState, currentNode.children); //Simulates all possible random opponent moves as children of players possible moves
            currentNode = currentNode.select(); //selects a player child node to continue simulation (randomly since none of these nodes have been visited yet as they are newly made)
            if (!currentNode.oppMove) {visitedNodes.add(currentNode);}
            if (!GameBoard.checkWin(currentNode.boardState, 2)
                    && !GameBoard.checkWin(currentNode.boardState, 1)
                    && !GameBoard.checkTie(currentNode.boardState))
            {
                currentNode = currentNode.select(); //selects opponent child of player children randomly
            }
        }


        if (opp == 1)
        {

            if (GameBoard.checkWin(currentNode.boardState, GameBoard.turn))
            {
                //backpropogate and update stats for positive result (win game)
                for (Node visitedNode : visitedNodes)
                {
                    //GameBoard.buildBoard(visitedNode.boardState, GameBoard.turn);
                    visitedNode.nodeVisits++;
                    visitedNode.nodeWins+=2; //adds value for a win
                }
            }

            else if (GameBoard.checkWin(currentNode.boardState, opp))
            {
                //backpropogate and update stats for negative result (lose game)
                for (Node visitedNode : visitedNodes)
                {
                    visitedNode.nodeVisits++;
                    //visitedNode.nodeWins-=0; //adds nothing for a loss
                }
            }

            else
            {
                for (Node visitedNode : visitedNodes)
                {
                    //backpropogate and update stats for a neutral result (tie game)
                    visitedNode.nodeVisits++;
                    visitedNode.nodeWins+=15; //adds value for a tie
                }
            }
        }

        else
        {
            if (GameBoard.checkWin(currentNode.boardState, GameBoard.turn))
            {
                //backpropogate and update stats for positive result (win game)
                for (Node visitedNode : visitedNodes)
                {
                    //GameBoard.buildBoard(visitedNode.boardState, GameBoard.turn);
                    visitedNode.nodeVisits++;
                    visitedNode.nodeWins+=2; //adds value for a win
                }
            }

            else if (GameBoard.checkWin(currentNode.boardState, opp))
            {
                //backpropogate and update stats for negative result (lose game)
                for (Node visitedNode : visitedNodes)
                {
                    visitedNode.nodeVisits++;
                    //visitedNode.nodeWins-=0; //adds nothing for a loss
                }
            }

            else
            {
                for (Node visitedNode : visitedNodes)
                {
                    //backpropogate and update stats for a neutral result (tie game)
                    visitedNode.nodeVisits++;
                    visitedNode.nodeWins+=15; //adds value for a tie
                }
            }
        }

    }

    private Node oppExpand(int[][] boardState, List<Node> children) {
        for (Node c : children)
        {
            if (c == null) {continue;}
            for (int i = 0; i < GameBoard.columns; i++)
            {
                for (int j = GameBoard.rows - 1; j >= 0; j--)
                {

                    if (c.boardState[j][i] == 0) //finds lower-most (if any) blanks spaces in each column to simulate opponent move
                    {
                        int[][] tempBoard = new int [GameBoard.rows][GameBoard.columns];

                        //boardClone(tempBoard, c.boardState);
                        if (GameBoard.turn == 1)
                        {
                            tempBoard[j][i] = 2;
                            c.children.add(new Node(tempBoard, true));
                            break;
                        }
                        else if (GameBoard.turn == 2)
                        {
                            tempBoard[j][i] = 1;
                            c.children.add(new Node(tempBoard, true));
                            break;

                        }

                    }
                }
            }
        }

        return children.get(randomNumb.nextInt(children.size()));
    }

    //clones an array in order to make multiple children from one parent
    public void boardClone (int[][] emptyArray, int[][] arrayToBeCloned)
    {

        for (int x = 0; x < GameBoard.rows; x++)
        {
            for (int y = 0; y < GameBoard.columns; y++)
            {
                emptyArray[x][y] = arrayToBeCloned[x][y];
            }
        }


    }

    public void expansion(int[][] boardState, List<Node> children, boolean firstIteration)
    {

        for (int i = 0; i < GameBoard.columns; i++)
        {
            for (int j = GameBoard.rows - 1; j >= 0; j--)
            {
                if (boardState[j][i] == 0) //finds lower-most (if any) blanks spaces in each column to simulate opponent move
                {
                    int[][] tempBoard = new int [GameBoard.rows][GameBoard.columns];

                    boardClone(tempBoard, boardState);

                    tempBoard[j][i] = GameBoard.turn;
                    children.add(new Node(tempBoard));
                    //if (counter < 100) {GameBoard.buildBoard(tempBoard, GameBoard.turn);}
                    counter++;
                    break;
                }
                else if (boardState[j][i] != 0 && j == 0 && firstIteration)
                {
                    children.add(null); //preserves indexes of children (in case one column fills up and is skipped)
                    //only necessary for firstIteration because the index is used to play move
                }
            }
        }
    }

    private boolean boardFull(int[][] board) {
        for (int r = 0; r < GameBoard.rows; r++)
        {
            for (int c = 0; c < GameBoard.columns; c++)
            {
                if (board[r][c] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasChildren()
    {
        return children.size() > 0;
    }

    private Node select() //Upper Confidence Bound (Bandit Policy) implemented to select most optimal node based on exploration-exploitation balance
    {
        Node selected = null;
        double bestUCB = 0;
        for (Node childNode : children)
        {
            if (childNode == null){continue;} //for the special case of firstIteration children
            // UCB = estimated value(average reward) + C * sqrt(ln(number of visits to parent) / (number of times (child) node has been visited))
            // Where C is some constant -- sqrt(2) in this case
            // buffer prevents divide by 0 errors in the case of unvisited nodes
            double ucbValue = childNode.nodeWins / (childNode.nodeVisits + buffer) //estimated value
                    + (2/Math.sqrt(2)) * Math.sqrt(2 * Math.log(nodeVisits+1) / (childNode.nodeVisits + buffer)) +
                    randomNumb.nextDouble() * buffer; //generates a very small random number to break ties between similar/unvisited nodes
            if (ucbValue > bestUCB)
            {
                selected = childNode;
                bestUCB = ucbValue;
            }

        }

        return selected;
    }

    private Node egreedy()
    {
        Node selected = null;
        double bestGreed = 0;
        if (Math.random() >= 0.2)
        {
            for (Node childNode : children)
            {
                if (childNode == null){continue;}
                double tempGreed = childNode.nodeWins / (childNode.nodeVisits + buffer) + buffer;
                if (tempGreed > bestGreed)
                {
                    selected = childNode;
                    bestGreed = tempGreed;
                }
            }
            return selected;
        }
        else
        {
            while (selected == null)
            {
                selected = children.get(randomNumb.nextInt(children.size()));
            }

            return selected;
        }
    }

}
