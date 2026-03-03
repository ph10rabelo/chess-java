package woodpecker.integration.lichess;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LichessService {
	public static final String PUZZLE = "https://lichess.org/api/puzzle/DtMnN";
	
	public LichessResponse getPuzzle() {
		RestTemplate restTemplate = new RestTemplate();
	    
	    String jsonPuro = restTemplate.getForObject(PUZZLE, String.class);
	    System.out.println("DEBUG - JSON QUE VEM DO LICHESS: " + jsonPuro);

	    LichessResponse response = restTemplate.getForObject(PUZZLE, LichessResponse.class);
	    
	    if (response != null && response.getPuzzle() != null && response.getGame() != null) {
	        return response;
	    }
	    return null;
	}

}