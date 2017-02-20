package ygraphs.ai.smart_fox.games;

import java.util.ArrayList;

public class ActionFactoryAmazons {
	
	public ArrayList<GameBoard> getActions(GameBoard state, boolean isWhiteMove){
		ArrayList<GameBoard> actions = new ArrayList<>();
		
		GameBoard curr = state;
		int[][] pieces;
		if(isWhiteMove)
			pieces = state.getWhite();
		else pieces = state.getBlack();
		
		for(int i = 1; i < 4; i++) {
			actions.addAll(moveQueen(pieces[i][0], pieces[i][1]));
			}
		return actions;
	}

	private ArrayList<? extends GameBoard> moveQueen(int qrow, int qcol) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
}
