package ygraphs.ai.smart_fox.games;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ygraphs.ai.smart_fox.GameMessage;

public class AImazon extends GamePlayer {

	private GameClient gameClient;
	private JFrame guiFrame = null;
	private GameBoard board = null;
	private AmazonStateSpace stateSpace = null;
	private boolean gameStarted = false;
	public String usrName = null;
	
	//private int[][] board = null;

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param passwd
	 */
	public AImazon(String name, String passwd) {

		this.usrName = name;
		// setupGUI();

		connectToServer(name, passwd);
	}

	@Override
	public void onLogin() {
		// once logged in, the gameClient will have the names of available game
		// rooms
		ArrayList<String> rooms = gameClient.getRoomList();
		this.gameClient.joinRoom(rooms.get(1));

	}

	private void connectToServer(String name, String passwd) {
		// create a client and use "this" class (a GamePlayer) as the delegate.
		// the client will take care of the communication with the server.
		gameClient = new GameClient(name, passwd, this);
	}

	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		
		if (messageType.equals(GameMessage.GAME_ACTION_START)) {
			board = new GameBoard((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			stateSpace = new AmazonStateSpace(board);
			if (((String) msgDetails.get("player-black")).equals(this.userName())) {
				System.out.println("Game State: " + msgDetails.get("player-black"));
			}
			else
				playerMove(9, 4, 9, 5, 1, 4);
				
		} else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
			handleOpponentMove(msgDetails);
		}
		return true;
	}

	// handle the event that the opponent makes a move.
	private void handleOpponentMove(Map<String, Object> msgDetails) {
		System.out.println("OpponentMove(): " + msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR));
		ArrayList<Integer> qcurr = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		ArrayList<Integer> qnew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
		ArrayList<Integer> arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		System.out.println("QCurr: " + qcurr);
		System.out.println("QNew: " + qnew);
		System.out.println("Arrow: " + arrow);

		//board.markPosition(qnew.get(0), qnew.get(1), arrow.get(0), arrow.get(1), qcurr.get(0), qcurr.get(1), true);
		board.update(qnew.get(0), qnew.get(1), arrow.get(0), arrow.get(1), qcurr.get(0), qcurr.get(1));
		
		stateSpace.absearch(board);
	}

	public void playerMove(int x, int y, int arow, int acol, int qfr, int qfc) {

		int[] qf = new int[2];
		qf[0] = qfr;
		qf[1] = qfc;

		int[] qn = new int[2];
		qn[0] = x;
		qn[1] = y;

		int[] ar = new int[2];
		ar[0] = arow;
		ar[1] = acol;

		// To send a move message, call this method with the required data
		this.gameClient.sendMoveMessage(qf, qn, ar);

	}

	public boolean handleMessage(String msg) {
		System.out.println("Time Out ------ " + msg);
		return true;
	}

	@Override
	public String userName() {
		return usrName;
	}

	public static void main(String[] args) {
		// AImazon game = new AImazon("daniel", "cosc322");
		AImazon game = new AImazon(args[0], args[1]);
	}
}
