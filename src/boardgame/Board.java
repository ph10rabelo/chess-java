package boardgame;

public class Board {
	private int rows;
	private int colunms;
	private Piece[][] pieces;
	public Board(int row, int colunm) {
		rows = row;
	    colunms = colunm;
		pieces = new Piece[row][colunm];
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColunms() {
		return colunms;
	}
	public void setColunms(int colunms) {
		this.colunms = colunms;
	}
	
	public Piece piece(int row, int colunm) {
		return pieces[row][colunm];
	}
	
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColunm()];
	}
}
