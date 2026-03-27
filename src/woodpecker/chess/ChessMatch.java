package woodpecker.chess;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import woodpecker.boardgame.Board;
import woodpecker.boardgame.Piece;
import woodpecker.boardgame.Position;
import woodpecker.chess.pieces.Bishop;
import woodpecker.chess.pieces.King;
import woodpecker.chess.pieces.Knight;
import woodpecker.chess.pieces.Pawn;
import woodpecker.chess.pieces.Queen;
import woodpecker.chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;
	
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch(){
		turn = 1;
		currentPlayer = Color.WHITE;
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessMatch(String fen) {
		board = new Board(8,8);
	}
	
	public int getTurn() {
		return turn;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}
	
	public void setPromoted(String s) {
		replacePromotedPiece(s);
	}
	
	private Color oponnent(Color color) {
		return (color == Color.WHITE)?Color.BLACK: Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x-> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p:list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("there is no "+color+" king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> oponnentPieces = piecesOnTheBoard.stream().filter(x-> ((ChessPiece)x).getColor() == oponnent(color)).collect(Collectors.toList());
		for(Piece p: oponnentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColunm()]) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color){
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x-> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p: list) {
			boolean[][] mat = p.possibleMoves();
			for(int i=0;i<board.getRows();i++) {
				for(int j=0;j<board.getColunms();j++) {
					if(mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source,target);
						boolean testCheck = testCheck(color);
						undoMove(source,target,capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColunms()];
		for (int i=0;i<board.getRows();i++) {
			for (int j=0;j<board.getColunms();j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
						}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source,target);
		Piece capturedPiece = makeMove(source,target);
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		//promotion
		promoted = null;
		if((movedPiece.getColor()==Color.WHITE && target.getRow()==0)||(movedPiece.getColor()==Color.BLACK && target.getRow()==7)) {
			promoted = (ChessPiece)board.piece(target);
			promoted = replacePromotedPiece("Q");
		}
		
		if(testCheck(currentPlayer)) {
			undoMove(source,target,capturedPiece);
			throw new ChessException("you can't put yourself in check");
		}
		check = (testCheck(oponnent(currentPlayer)))? true : false;
		if(testCheckMate(oponnent(currentPlayer))) {
			checkMate = true;
		}else {
			nextTurn();
		}
		
		// en passant
		if(movedPiece instanceof Pawn && (target.getRow()== source.getRow()+2 || target.getRow() == source.getRow()-2)) {
			enPassantVulnerable = movedPiece;
		}else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if(promoted == null) {
			throw new IllegalStateException("there is no piece to be promoted");
		}
		if(!type.equalsIgnoreCase("B") && !type.equalsIgnoreCase("N") && !type.equalsIgnoreCase("Q") && !type.equalsIgnoreCase("R")) {
			return promoted;
		}
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equalsIgnoreCase("B")) return new Bishop(board,color);
		if (type.equalsIgnoreCase("N")) return new Knight(board,color);
		if (type.equalsIgnoreCase("Q")) return new Queen(board,color);
		if (type.equalsIgnoreCase("R"))return new Rook(board,color);
		if (type.equalsIgnoreCase("P")) return new Pawn(board, color, this);
	    if (type.equalsIgnoreCase("K")) return new King(board, color, this);
	    throw new ChessException("Tipo de peça inválido: " + type);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece)board.removePiece(source);
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		
		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		//castling kingside rook
		if (p instanceof King && target.getColunm()== source.getColunm()+2) {
			Position sourceT = new Position(source.getRow(),source.getColunm()+3);
			Position targetT = new Position(source.getRow(),source.getColunm()+1);
			ChessPiece rook =(ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		//castling queenside rook
		if (p instanceof King && target.getColunm()== source.getColunm()-2) {
			Position sourceT = new Position(source.getRow(),source.getColunm()-4);
			Position targetT = new Position(source.getRow(),source.getColunm()-1);
			ChessPiece rook =(ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//en passant
		if(p instanceof Pawn) {
			if(source.getColunm() != target.getColunm() && capturedPiece == null ) {
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow()+1,target.getColunm());
				}else {
					pawnPosition = new Position(target.getRow()-1,target.getColunm());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if(capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		
		//castling kingside rook
		if (p instanceof King && target.getColunm()== source.getColunm()+2) {
			Position sourceT = new Position(source.getRow(),source.getColunm()+3);
			Position targetT = new Position(source.getRow(),source.getColunm()+1);
			ChessPiece rook =(ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
			}
		
		//castling queenside rook
		if (p instanceof King && target.getColunm()== source.getColunm()-2) {
			Position sourceT = new Position(source.getRow(),source.getColunm()-4);
			Position targetT = new Position(source.getRow(),source.getColunm()-1);
			ChessPiece rook =(ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
			}
		
		//en passant
		if(p instanceof Pawn) {
			if(source.getColunm() != target.getColunm() && capturedPiece == enPassantVulnerable) {
				Position pawnPosition;
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				if(p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3,target.getColunm());
				}else {
					pawnPosition = new Position(4,target.getColunm());
				}
				board.placePiece(pawn,pawnPosition);
			}
		}
	}
	
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece in source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessException("This piece is not yours");
		}
		if(!board.piece(position).isThereAnyPossibleMoves()) {
			throw new ChessException("There is no possible moves with this piece");
		}
	}
	
	private void validateTargetPosition(Position source,Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE)?Color.BLACK: Color.WHITE;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void clearBoard() {
	    for (int i = 0; i < 8; i++) {
	        for (int j = 0; j < 8; j++) {
	            board.removePiece(new Position(i, j));
	        }
	    }
	    piecesOnTheBoard.clear();
	    capturedPieces.clear();

	    check = false;
	    checkMate = false;
	    promoted = null;
	    currentPlayer = Color.WHITE; 
	}
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('h',1, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE,this));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));
		
		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('h',8, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK,this));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
	}
}
