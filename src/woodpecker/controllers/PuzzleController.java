package woodpecker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woodpecker.chess.puzzle.Puzzle;
import woodpecker.integration.lichess.LichessService;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {
	@Autowired 
	private LichessService lichessService;
	
	@GetMapping("/daily")
    public Puzzle getDailyPuzzle() {
        return lichessService.getDailyPuzzle();
    }
	
}
