package ygraphs.ai.smart_fox.games;

public class Test {
	public static void main(String[] args) {
		GameBoard game = new GameBoard();
		System.out.println(game.eval(true));
		
		int[] bestmove = AmazonStateSpace.absearch(game);
		for(int n: bestmove)
			System.out.print(n);
	}
}
