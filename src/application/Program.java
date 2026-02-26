package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import woodpecker.chess.ChessException;
import woodpecker.chess.ChessMatch;
import woodpecker.chess.ChessPiece;
import woodpecker.chess.ChessPosition;
import woodpecker.chess.puzzle.Puzzle;
import woodpecker.chess.puzzle.PuzzleManager;

public class Program {

	public static void main(String[] args) {
	    Scanner sc = new Scanner(System.in);
	    
	    String id = "1232";
	    String fen = "r1bqkbnr/pppp1ppp/2n1p3/8/2PP4/2N2N2/PP2BPPP/R1BQK2R b KQkq - 0 1";
	    List<String> themes = List.of("themes");
	    Integer rating = 1500;
	    List<String> solution = List.of("d7d5", "c4d5", "e6d5");
	    Puzzle puzzle = new Puzzle(id,fen,themes,rating,solution); 

	    ChessMatch match = new ChessMatch(puzzle.getFen());
	    PuzzleManager manager = new PuzzleManager(puzzle, match);
	    List<ChessPiece> captured = new ArrayList<>();

	    while (!manager.isResolved()) {
	        try {
	            UI.clearScreen();
	            UI.printMatch(match, captured);
	            System.out.println("\n--- MODO WOODPECKER ---");
	            System.out.println("Sua vez! Encontre o lance correto.");
	            
	            System.out.print("\nSource: ");
	            ChessPosition source = UI.readChessPosition(sc);

	            boolean[][] possibleMoves = match.possibleMoves(source);
	            UI.clearScreen();
	            UI.printBoard(match.getPieces(), possibleMoves);

	            System.out.print("\nTarget: ");
	            ChessPosition target = UI.readChessPosition(sc);
	            sc.nextLine();
	            
	            boolean correct = manager.verifyAndExecute(source, target);

	            if (correct) {
	                System.out.println("\nCORRETO!");
	                Thread.sleep(800);

	            } else {
	                System.out.println("\nLANCE ERRADO! Tente novamente.");
	                Thread.sleep(1500);
	            }

	            if (match.getPromoted() != null) {
	                System.out.print("Enter piece for promotion (Q/B/N/R): ");
	                String type = sc.nextLine().toUpperCase();
	                match.replacePromotedPiece(type);
	            }
	        } 
	        catch (ChessException | InputMismatchException e) {
	            System.out.println(e.getMessage());
	            sc.nextLine();
	        } 
	        catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }

	    UI.clearScreen();
	    UI.printMatch(match, captured);
	    System.out.println("\n✅PUZZLE CONCLUIDO COM SUCESSO!");
	}
}
