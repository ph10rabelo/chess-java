package woodpecker.integration.lichess;

import woodpecker.chess.puzzle.Puzzle;

public class LichessResponse {
	private Puzzle puzzle;
	
	public Puzzle getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
	}
}
