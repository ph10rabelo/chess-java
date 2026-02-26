package woodpecker.integration.lichess;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import woodpecker.chess.puzzle.Puzzle; 

@Service
public class LichessService {
	public static final String DAILY_PUZZLE = "https://lichess.org/api/puzzle/daily";
	
	public Puzzle getDailyPuzzle() {
		RestTemplate restTemplate = new RestTemplate();
		LichessResponse response =  restTemplate.getForObject(DAILY_PUZZLE,LichessResponse.class);
		return response != null? response.getPuzzle():null;
	}

}