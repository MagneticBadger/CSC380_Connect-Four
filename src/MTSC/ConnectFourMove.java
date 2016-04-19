package MTSC;

import java.util.ArrayList;

import MTSC.Move;

public class ConnectFourMove implements Move {
	int player;
	int row;
	
	ConnectFourMove(int player, int row){
		this.player = player;
		this.row = row;
	}
	
	@Override
	public String toString(){
		StringBuilder strb = new StringBuilder();
		strb.append(row);
		return strb.toString();
	}

	@Override
	public boolean isStochastic() {
		return false;
	}

	@Override
	public ArrayList<Move> unpack() {
		return null;
	}
}
