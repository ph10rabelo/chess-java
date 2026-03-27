package woodpecker.chess.pieces;

import woodpecker.boardgame.Board;
import woodpecker.boardgame.Position;
import woodpecker.chess.ChessMatch;
import woodpecker.chess.ChessPiece;
import woodpecker.chess.Color;

public class Pawn extends ChessPiece{
	
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}
	
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
		
		boolean[][] mat = new boolean [ getBoard().getRows()][getBoard().getColunms()];
		Position p = new Position(0,0);
		if(getColor() == Color.WHITE) {
			p.setValues(position.getRow()-1,position.getColunm());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
				p.setValues(position.getRow()-2,position.getColunm());
				
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount()== 0) {
					mat[p.getRow()][p.getColunm()] = true;
				}
			}
			p.setValues(position.getRow()-1,position.getColunm()-1);
			if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
			}
			p.setValues(position.getRow()-1,position.getColunm()+1);
			if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
			}	
			//en passant white
			if(position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getColunm()-1);
				if(getBoard().positionExists(left) && isThereOponnentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()-1][left.getColunm()] = true;
				}
				Position right= new Position(position.getRow(), position.getColunm()+1);
				if(getBoard().positionExists(right) && isThereOponnentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()-1][right.getColunm()] = true;
				}
			}
			
		}else {
			p.setValues(position.getRow()+1,position.getColunm());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
				p.setValues(position.getRow()+2,position.getColunm());
				
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount()== 0) {
					mat[p.getRow()][p.getColunm()] = true;
				}
			}
			p.setValues(position.getRow()+1,position.getColunm()-1);
			if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
			}
			p.setValues(position.getRow()+1,position.getColunm()+1);
			if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
				mat[p.getRow()][p.getColunm()] = true;
			}
			//en passant black
			if(position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getColunm()-1);
				if(getBoard().positionExists(left) && isThereOponnentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()+1][left.getColunm()] = true;
				}
				Position right= new Position(position.getRow(), position.getColunm()+1);
				if(getBoard().positionExists(right) && isThereOponnentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()+1][right.getColunm()] = true;
				}
			}
		}
		return mat;
	}
	
}
