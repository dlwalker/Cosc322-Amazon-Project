package ygraphs.ai.smart_fox.games;

import java.util.ArrayList;
import java.util.LinkedList;

public class GameBoard {

	private int[][] board;
	private int row, col, size;
	int[][] white = {{7, 1}, {10, 4}, { 10, 7}, {7, 10}};
	int[][] black = {{4, 1}, {1, 4}, {1, 7}, {4, 10}};

	public GameBoard(ArrayList<Integer> state) {
		int size = state.size();
		row = (int) Math.sqrt(size);
		col = row;
		board = new int[row][col];
	
		for (int i = 0; i < size; i++) {
			board[i / row][i % col] = state.get(i);
		}
		printArray(board);
	}
	//default constructor
	public GameBoard() {
		board = new int[11][11];
		row = 11;
		col = 11;

		board[4][1] = 2;
		board[1][4] = 2;
		board[1][7] = 2;
		board[4][10] = 2;
		board[7][1] = 1;
		board[10][4] = 1;
		board[10][7] = 1;
		board[7][10] = 1;

		printArray(board);
	}
	//constructor to generate result of an action
	public GameBoard(GameBoard state, int[] a) {
		this.board = arrayCopy(state.board);
		this.white = arrayCopy(state.white);
		this.black = arrayCopy(state.black);
		update(a);
	}

	public int getRow(){ return row;}
	public int getCol(){ return col;}
	public int getSize(){ return size;}
	public int[][] getWhite(){return white;}
	public int[][] getBlack(){return black;}
	
	
	public boolean update(int qrow, int qcol, int arow, int acol, int qfr, int qfc) {
		
		int player = board[qfr][qfc];
		board[qfr][qfc] = 0;
		board[qrow][qcol] = player;
		board[arow][acol] = 3;
		
		if(player == 1){
			for(int i = 0; i < 4; i++){
				if(qfr == white[i][0] && qfc == white[i][1]){
					white[i][0] = qrow;
					white[i][1] = qcol;
				}
			}
		}
		else if(player == 2){
			for(int i = 0; i < 4; i++){
				if(qfr == black[i][0] && qfc == black[i][1]){
					black[i][0] = qrow;
					black[i][1] = qcol;
				}
			}
		}
		else return false;
		return true;
	}
	public boolean update(int[] action){
		if(action.length != 6) return false;
		else return update(action[0], action[1], action[2], action[3], action[4], action[5]);
	}
	
	/* The following three methods *should* generate every possible move available to a given player (fingers crossed) */
	public ArrayList<int[]> getActions(boolean isWhiteMove){
		ArrayList<int[]> actions = new ArrayList<>();
		
		int[][] pieces;
		if(isWhiteMove)
			pieces = white;
		else 
			pieces = black;
		
		for(int i = 1; i < 4; i++) {
			actions.addAll(moveQueen(pieces[i][0], pieces[i][1]));
			}
		return actions;
	}
	/* Generates the available moves for a queen at a specified location.*/
	private ArrayList<int[]> moveQueen(int fr, int fc) {
		
		ArrayList<int[]> moves = new ArrayList<>();
		
		int qr = fr;
		int qc = fc;
		while(++qr <= 10 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(++qr <= 10 && ++qc <= 10 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(++qc <= 10 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(--qr >= 1 && ++qc <= 10 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(--qr >= 1 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(--qr >= 1 && --qc >= 1 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(--qc >= 1 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		qr = fr;
		qc = fc;
		while(++qr <= 10 && --qc >= 1 && board[qr][qc] == 0 ){
			moves.addAll(shootArrow(qr, qc, fr, fc));
		}
		
		return moves;
	}
	/* Generates the locations an arrow can be shot at from a specified position */
	private ArrayList<int[]> shootArrow(int qr, int qc, int fr, int fc) {

		ArrayList<int[]> actions = new ArrayList<>();
		//clears the position of the moved queen
		int temp = board[fr][fc];
		board[fr][fc] = 0;
		
		int ar = qr;
		int ac = qc;
		while(++ar <= 10 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(++ar <= 10 && ++ac <= 10 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while( ++ac <= 10 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(--ar >= 1 && ++ac <= 10 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(--ar >= 1 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(--ar >= 1 && --ac >= 1 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(--ac >= 1 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		ar = qr;
		ac = qc;
		while(++ar <= 10 && --ac >= 1 && board[ar][ac] == 0 ){
			int[] a = {qr, qc, ar, ac, fr, fc};
			actions.add(a);
		}
		
		board[fr][fc] = temp;
		
		return actions;
	}
	//heuristic evaluation function for the GameBoard - uses simple min-distance function
	public int eval(boolean isWhite) {
		int[][] b = minDistance(black);
		int[][] w = minDistance(white);
		int count = 0;
		for(int i = 1; i < row; i ++)
			for(int j = 1; j < col; j++){
				if(w[i][j] > b[i][j])
					count++;
				else if(w[i][j] < b[i][j])
					count--;
			}
		if(isWhite)
			return count;
		else
			return -count;
	}
	private int[][] minDistance(int[][] player){
		int[][] dist = new int[row][col];
		LinkedList<int[]> queue = new LinkedList<>();
		for(int i = 0; i < 4; i++){
			int[] root = {0, 0, player[i][0], player[i][1]};
			queue.add(root);
		}
		while(!queue.isEmpty()){
			int[] a = queue.remove();
			for(int[] n: shootArrow(a[2], a[3], 0, 0)){
				if(dist[n[2]][n[3]] == 0){
					dist[n[2]][n[3]] = dist[n[0]][n[1]] + 1;
					queue.add(n);
				}
			}
		}
		return dist;
	}
	//for testing/debugging
	private int[][] arrayCopy(int[][] old){
		int[][] copy = new int[old.length][old[0].length];
		for(int i=0; i< old.length; i++)
			  for(int j=0; j< old[0].length; j++)
			    copy[i][j] = old[i][j];
		return copy;
	}
	public String toString(){
		String temp = new String();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				temp = temp + board[i][j];
			}
			temp = temp + "\n";
		}
		return temp;
	}
	public void printArray(int[][] a){
		for(int i = 0; i < a.length; i++){
			for(int j = 0; j < a[0].length; j++)
				System.out.print(a[i][j]);
			System.out.println();
		}
		System.out.println();
	}
	//for testing
	public static void main(String[] args) {
		GameBoard game = new GameBoard();
		System.out.println(game);
	}
}