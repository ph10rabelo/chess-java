package woodpecker.chess;

import woodpecker.boardgame.Board;
import woodpecker.boardgame.Piece;
import woodpecker.boardgame.Position;

public abstract class ChessPiece extends Piece {
	private Color color;
	private int moveCount;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	protected boolean isThereOponnentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		if(p == null || p.getColor() == color) {
			return false;
		}
		return true;
	}
}
