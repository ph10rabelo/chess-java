package woodpecker.chess.puzzle;

import woodpecker.chess.ChessMatch;
import woodpecker.chess.ChessPosition;
import java.util.List;

public class PuzzleManager {
	private ChessMatch match;
	private Puzzle currentPuzzle;
	private int moveIndex;
	
	public PuzzleManager(Puzzle puzzle, ChessMatch match) {
		this.currentPuzzle = puzzle;
		this.moveIndex = 0;
		this.match = match;
	}
	
	public boolean verifyAndExecute(ChessPosition source, ChessPosition target) {
		String userMove = source.toString()+target.toString();
		if(userMove.equals(currentPuzzle.getSolution().get(moveIndex))) {
			match.performChessMove(source, target);
			moveIndex++;
			if(moveIndex < currentPuzzle.getSolution().size()) {
				executeOpponentMove();
			}
			return true;
		}
		return false;
		
	}
	private void executeOpponentMove() {
		String opmove = currentPuzzle.getSolution().get(moveIndex);
		char sourceChar = opmove.charAt(0);
		String sourceStr = opmove.substring(1,2);
		int sourceInt = Integer.parseInt(sourceStr);
		
		char targetChar =  opmove.charAt(2);
		char targetRow = opmove.charAt(3);
		int targetInt = Character.getNumericValue(targetRow);
		
		ChessPosition source = new ChessPosition(sourceChar,sourceInt);
		ChessPosition target = new ChessPosition(targetChar,targetInt);
		
		match.performChessMove(source, target);
		if(opmove.length()>4) {
			match.setPromoted(opmove.substring(4,5));
		}
		moveIndex++;
	}
	
	
	
	public boolean isResolved() {
		return moveIndex >= currentPuzzle.getSolution().size();
	}
}
