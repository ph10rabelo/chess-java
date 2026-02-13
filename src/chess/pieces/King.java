package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece{

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor()!=getColor();
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [ getBoard().getColunms()][getBoard().getRows()];
		
		Position p = new Position(0,0);
		
		p.setValues(position.getRow()+1, position.getColunm());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()+1][position.getColunm()] = true;
		}
		
		p.setValues(position.getRow()+1, position.getColunm()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()+1][position.getColunm()+1] = true;
		}
		
		p.setValues(position.getRow(),position.getColunm()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()][position.getColunm()+1] = true;
		}
		
		p.setValues(position.getRow()-1, position.getColunm());
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()-1][position.getColunm()] = true;
		}
		
		p.setValues(position.getRow()-1, position.getColunm()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()-1][position.getColunm()-1] = true;
		}

		p.setValues(position.getRow(),position.getColunm()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()][position.getColunm()-1] = true;
		}
		
		p.setValues(position.getRow()+1,position.getColunm()-1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()+1][position.getColunm()-1] = true;
		}
		
		p.setValues(position.getRow()-1,position.getColunm()+1);
		if(getBoard().positionExists(p) && canMove(p)) {
			mat[position.getRow()-1][position.getColunm()+1] = true;
		}
		
		return mat;
	}
}
