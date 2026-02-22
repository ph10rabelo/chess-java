package chess.puzzle;

import java.util.List;

public class Puzzle {
	private String id;
	private String fen;
	private List<String> themes;
	private Integer rating;
	private List<String> solution;
	public Puzzle(String id, String fen, List<String> themes, Integer rating, List<String> solution) {
		this.id = id;
		this.fen = fen;
		this.themes = themes;
		this.rating = rating;
		this.solution = solution;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public List<String> getThemes() {
		return themes;
	}
	public void setThemes(List<String> themes) {
		this.themes = themes;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public List<String> getSolution(){
		return solution;
	}
	public void setSolution(List<String> solution) {
		this.solution = solution;
	}
}
