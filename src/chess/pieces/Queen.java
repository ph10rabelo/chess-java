package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "Q";
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [ getBoard().getRows()][getBoard().getColunms()];
		Position p = new Position(0,0);
		
		//up
		p.setValues(position.getRow()-1, position.getColunm());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()-1, p.getColunm());
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		//down
		p.setValues(position.getRow()+1, position.getColunm());
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()+1, p.getColunm());
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		//left
		p.setValues(position.getRow(), position.getColunm()-1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow(), p.getColunm()-1);
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		p.setValues(position.getRow()-1, position.getColunm()-1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()-1, p.getColunm()-1);
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		p.setValues(position.getRow()-1, position.getColunm()+1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()-1, p.getColunm()+1);
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		p.setValues(position.getRow()+1, position.getColunm()+1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()+1, p.getColunm()+1);
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		p.setValues(position.getRow()+1, position.getColunm()-1);
		while(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
			p.setValues(p.getRow()+1, p.getColunm()-1);
		}
		if(getBoard().positionExists(p) && isThereOponnentPiece(p)) {
			mat[p.getRow()][p.getColunm()] = true;
		}
		
		return mat;
	}
	
}
