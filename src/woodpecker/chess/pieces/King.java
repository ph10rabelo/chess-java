package woodpecker.chess.pieces;

import woodpecker.boardgame.Board;
import woodpecker.boardgame.Position;
import woodpecker.chess.ChessMatch;
import woodpecker.chess.ChessPiece;
import woodpecker.chess.Color;

public class King extends ChessPiece{
	
	private ChessMatch chessMatch;
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}
	
	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p == null || p.getColor()!=getColor();
	}
	
	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p!= null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount()==0;
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean [ getBoard().getRows()][getBoard().getColunms()];
		
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
		
		//special move castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) {
			//castling kingside rook
			Position posT1 = new Position(position.getRow(),position.getColunm()+3);
			if(testRookCastling(posT1)) {
				Position p1 = new Position(position.getRow(),position.getColunm()+1);
				Position p2 = new Position(position.getRow(),position.getColunm()+2);
				if(getBoard().piece(p1) == null && getBoard().piece(p2)==null) {
					mat[position.getRow()][position.getColunm()+2]= true;
				}
			}
			//castling queenside rook
			Position posT2= new Position(position.getRow(),position.getColunm()-4);
			if(testRookCastling(posT2)) {
				Position p1 = new Position(position.getRow(),position.getColunm()-1);
				Position p2= new Position(position.getRow(),position.getColunm()-2);
				Position p3= new Position(position.getRow(),position.getColunm()-3);
				if(getBoard().piece(p1) == null && getBoard().piece(p2)==null && getBoard().piece(p3)==null) {
					mat[position.getRow()][position.getColunm()-2]=true;
				}
			}
		}
		
		return mat;
	}
	
	
}
