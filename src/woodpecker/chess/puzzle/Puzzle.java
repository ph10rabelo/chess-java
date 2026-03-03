package woodpecker.chess.puzzle;

import java.util.List;

public class Puzzle {
	private int initialPly;
	private String id;
	private List<String> themes;
	private Integer rating;
	private List<String> solution;
	
	public Puzzle() {}
	
	public Puzzle(String id, List<String> themes, Integer rating, List<String> solution, int initialPly) {
		this.id = id;
		this.themes = themes;
		this.rating = rating;
		this.solution = solution;
		this.initialPly = initialPly;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public int getInitialPly() {
		return initialPly;
	}
	public void setInitialPly(int initialPly) {
		this.initialPly = initialPly;
	}
}
