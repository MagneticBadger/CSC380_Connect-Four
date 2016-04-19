package MTSC;

import java.util.ArrayList;
import java.util.Random;

public class MCTS {
	private Random random;
	private Node rootNode;
	private double explorationConstant = Math.sqrt(2.0);
	private double pessimisticBias;
	private double optimisticBias;

	private boolean scoreBounds;
	private boolean trackTime; // display thinking time used
	private int timeLimit; // Set a time limit per move.

	private ArrayList<Node> path;
	
	public MCTS() {
		random = new Random();
	}

	public Move runMCTS(Board s, int runs)
	{
		rootNode = new Node(s);

		long startTime = System.nanoTime();

		for (int i = 0; i < runs; i++) {
			select(s.duplicate(), rootNode);
		}

		long endTime = System.nanoTime();

		if (this.trackTime)
			System.out.println("Thinking time per move in milliseconds: "
					+ (endTime - startTime) / 1000000+ "Children Nodes: " +rootNode.getChildren().size());

		return finalSelect(rootNode);
	}

	private void select(Board brd, Node node) {
		Node currentNode = node;
		Board currentBoard = brd;
		ArrayList<Node> visited = new ArrayList<Node>();
		
		while (true) {
			// Break procedure if end of tree
			if (currentBoard.gameOver()) {
				currentNode.backPropagateScore(currentBoard.getScore());
				//System.out.println("Le whut: " + Arrays.toString(currentBoard.getScore()));
				if (scoreBounds) {
					// This runs only if bounds propagation is enabled.
					// It propagates bounds from solved nodes and prunes
					// branches from the when needed.
					currentNode.backPropagateBounds(currentBoard.optimisticBounds(),
							currentBoard.pessimisticBounds());
				}
				return;
			}

			if (currentNode.getUnvisitedChildren() == null) {
				//currentNode.initializeUnexplored(currentBoard);
				ArrayList<Move> legalMoves = currentBoard.getMoves();
				currentNode.unvisitedChildren = new ArrayList<Node>();
				for (int i = 0; i < legalMoves.size(); i++) {
					Node tempState = new Node(currentBoard, legalMoves.get(i), currentNode);
					currentNode.getUnvisitedChildren().add(tempState);
				}
			}

			if (!currentNode.getUnvisitedChildren().isEmpty()) {
				// it picks a move at random from list of unvisited children
				Node temp = currentNode.getUnvisitedChildren().remove(random.nextInt(currentNode.getUnvisitedChildren().size()));
				currentNode.getChildren().add(temp);
				playout(temp, brd);
				return;
			} else {
				double bestValue = Double.NEGATIVE_INFINITY;
				Node bestChild = null;
				double tempBest;
				ArrayList<Node> bestNodes = new ArrayList<Node>();

				for (Node s : currentNode.getChildren()) {
					// Pruned is only ever true if a branch has been pruned 
					// from the tree and that can only happen if bounds 
					// propagation mode is enabled.
					if (s.pruned == false) {
						tempBest = s.upperConfidenceBound(explorationConstant)
								+ optimisticBias * s.opti[currentNode.player]
								+ pessimisticBias * s.pess[currentNode.player];

						// If we found a better node
						if (tempBest > bestValue) {
							bestNodes.clear();
							bestNodes.add(s);
							bestChild = s;
							bestValue = tempBest;
						} else if (tempBest == bestValue) {
							// If we found an equal node
							bestNodes.add(s);
						}
					}
				}

				// This only occurs when all branches have been pruned from the
				// tree
				if (currentNode == rootNode && bestChild == null)
					return;

				Node finalNode = bestNodes.get(random.nextInt(bestNodes.size()));
				currentNode = finalNode;
				currentBoard.makeMove(finalNode.move);
			}
		}
	}

	private Move finalSelect(Node n) {
		double bestValue = Double.NEGATIVE_INFINITY;
		double tempBest;
		ArrayList<Node> bestNodes = new ArrayList<Node>();

		for (Node s : n.getChildren()) {
			tempBest = s.games;
			// tempBest += 1.0 / Math.sqrt(s.games);
			// tempBest = Math.min(tempBest, s.opti[n.player]);
			// tempBest = Math.max(tempBest, s.pess[n.player]);
			if (tempBest > bestValue) {
				bestNodes.clear();
				bestNodes.add(s);
				bestValue = tempBest;
			} else if (tempBest == bestValue) {
				bestNodes.add(s);
			}
		}

		Node finalNode = bestNodes.get(random.nextInt(bestNodes.size()));
		
		System.out.println("Highest value: " + bestValue + ", O/P Bounds: "
				+ finalNode.opti[n.player] + ", " + finalNode.pess[n.player]);
		return finalNode.move;
	}

	private void playout(Node state, Board board) {
		ArrayList<Move> moves;
		Move mv;
		Board brd = board.duplicate();

		// Start playing random moves until the game is over
		while (true) {
			if (brd.gameOver()) {
				state.backPropagateScore(brd.getScore());
				return;
			}

			moves = brd.getMoves();
			mv = moves.get(random.nextInt(moves.size()));
			brd.makeMove(mv);
		}
	}

	public void setExplorationConstant(double exp) {
		explorationConstant = exp;
	}

	public void setPessimisticBias(double b) {
		pessimisticBias = b;
	}

	public void setOptimisticBias(double b) {
		optimisticBias = b;
	}

	public void setScoreBounds(boolean b) {
		scoreBounds = b;
	}

	public Move runFlatMCTS(Board s, int runs) {
		ArrayList<Move> legalMoves = s.getMoves();
		ArrayList<Board> boards = s.duplicate(legalMoves.size());
		ArrayList<FlatGameState> states = new ArrayList<FlatGameState>();

		// There's only one move left, make that move
		if (legalMoves.size() == 1)
			return legalMoves.get(0);

		for (Board b : boards)
			states.add(new FlatGameState(b,
					legalMoves.remove(legalMoves.size() - 1)));

		for (FlatGameState st : states) {
			st.board.makeMove(st.move);
			// ConnectFour cf = (ConnectFour) st.board;
			for (int i = 0; i < runs; i++) {
				playout(st);
			}
		}

		double ratio = 0;
		Move bestMove = null;

		for (FlatGameState st : states) {
			if (ratio <= (double) st.wins / (double) st.games) {
				ratio = (double) st.wins / (double) st.games;
				bestMove = st.move;
			}
		}

		return bestMove;
	}

	private void playout(FlatGameState st) {
		ArrayList<Move> moves;
		Move mv;
		Board brd = st.board.duplicate();
		// Start playing random moves until the game is over
		while (true) {
			if (checkWinCondition(st, brd))
				return;
			moves = brd.getMoves();
			mv = moves.get(random.nextInt(moves.size()));
			brd.makeMove(mv);
		}
	}

	private boolean checkWinCondition(FlatGameState state, Board brd) {
		// if AI won the game
		if (brd.checkWinCondition(brd.getCurrentPlayer())) {
			state.wins++;
			state.games++;
			return true;
		}

		// if AI lost the game
		if (brd.checkLossCondition(brd.getCurrentPlayer())) {
			state.games++;
			return true;
		}

		// if draw
		if (brd.checkDraw()) {
			state.games++;
			return true;
		}

		return false;
	}

	public void setTimeDisplay(boolean displayTime) {
		this.trackTime = displayTime;
	}
}
