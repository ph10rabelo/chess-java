package integration.lichess;

import java.io.IOException;
import java.net.URI; 
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LichessService {
	
	public static final String DAILY_PUZZLE = "https://lichess.org/api/puzzle/daily";
	
	public void getDailyPuzzle() {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(DAILY_PUZZLE)).GET().build();
		
		try {
			HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
			System.out.println("Status code: " + response.statusCode());
			System.out.println("Resposta do Lichess:");
			System.out.println(response.body());
		} catch(IOException | InterruptedException e) {
			System.out.println("Erro ao conectar com o Lichess: "+ e.getMessage());
			e.printStackTrace();
		}
		
	}
}
