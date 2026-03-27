package woodpecker.integration.lichess;

import woodpecker.chess.puzzle.Puzzle;
import woodpecker.chess.puzzle.Game;
public class LichessResponse {
	private Puzzle puzzle;
	private Game game;
	
	public Puzzle getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game){
		this.game = game;
	}
}
