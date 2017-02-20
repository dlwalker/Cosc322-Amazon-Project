package ygraphs.ai.smart_fox.games;
import java.util.ArrayList;
import java.util.Timer;

public class AmazonStateSpace {
	
	private GameBoard board;
//	private Timer timer = new Timer();
//	private int limit = 1;
	
	public AmazonStateSpace(GameBoard board) {
		this.board = board;
	}

	public static int[] absearch(GameBoard state) {
    	//function ALPHA-BETA-SEARCH(state) returns an action
		long time0,time1;
		long timeLimit = 30000;
	     time0=System.currentTimeMillis();
	     int[] v;
	     int depth = 1;
	    do{
	    	//v <-- MAX-VALUE(state,-inf,+inf)
    		v = maxValue(state, depth++, Integer.MIN_VALUE, Integer.MAX_VALUE);
    		
	        time1=System.currentTimeMillis();
	    }
	    while (time1-time0<timeLimit);
		    	 
		//return the action in ACTIONS(state) with value v
		return v;
	}
	private static int[] maxValue(GameBoard state, int depth, int alpha, int beta){
		//function MAX-VALUE(state,alpha, beta) returns a utility value
				//if CUTOFF-TEST(state, depth) then return EVAL(state)
		if(depth <= 0){
			int[] v = {state.eval(true)};
			return v;
		}
		ArrayList<int[]> actions = state.getActions(true);
		if(actions.size() <= 0){
			int[] v = {state.eval(true)};
			return v;
		}
		int v[] = {Integer.MIN_VALUE, 0, 0, 0, 0, 0, 0};
		for(int[] a: actions){
			
			v[0] = Math.max(v[0], minValue(new GameBoard(state, a), depth-1, alpha, beta)[0]);
			
			if(v[0] >= beta){
				v[1] = a[0];
				v[2] = a[1];
				v[3] = a[2];
				v[4] = a[3];
				v[5] = a[4];
				v[6] = a[5];
				return v;
			}
			alpha = Math.max(beta, v[0]);
		}
		return v;
	}
	private static int[] minValue(GameBoard state, int depth, int alpha, int beta){
		//function MIN-VALUE(state,alpha, beta) returns a utility value
				//if CUTOFF-TEST(state, depth) then return EVAL(state)
		if(depth <= 0){
			int[] v = {state.eval(false)};
			return v;
		}
		ArrayList<int[]> actions = state.getActions(true);
		if(actions.size() <= 0){
			int[] v = {state.eval(false)};
			return v;
		}
		int v[] = {Integer.MAX_VALUE, 0, 0, 0, 0, 0, 0};
		for(int[] a: actions){
			
			v[0] = Math.min(v[0], maxValue(new GameBoard(state, a), depth-1, alpha, beta)[0]);
			
			if(v[0] <= alpha){
				v[1] = a[0];
				v[2] = a[1];
				v[3] = a[2];
				v[4] = a[3];
				v[5] = a[4];
				v[6] = a[5];
				return v;
			}
			beta = Math.min(beta, v[0]);
		}
		return v;
	}
	
	/*
	private static class SearchNode{
		
		private GameBoard state;
		private SearchNode parent;
		int[] action;
	}
	*/
}
