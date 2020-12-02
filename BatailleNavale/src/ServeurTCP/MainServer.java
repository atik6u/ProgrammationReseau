package ServeurTCP;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
	public static void main(String[] args) {
		try {
			ServerSocket ecoute = new ServerSocket(1500);
			System.out.println("Serveur lancé!");
			while(true) {
				
				Socket player1 = ecoute.accept();
				System.out.println("Joueur 1 connecté!");
				
				Socket player2 = ecoute.accept();
				System.out.println("Joueur 2 connecté!");
				
				Game game = CreateGame();
				
				new ThreadGameP1(player1, game).start();
				new ThreadGameP2(player2, game).start();
			}
		} catch(Exception e) {
			System.out.println("Erreur de démarrage du serveur");
		}
	}
	
	private static Game CreateGame() {
		// Ici vous pouvez modifier les paramètres de la partie
		
		int width = 4, length = 4;
		ArrayList<Integer> ships = new ArrayList<Integer>();
		ships.add(2);
		ships.add(2);
		ships.add(2);
//		ships.add(3);
//		ships.add(4);
//		ships.add(4);
//		ships.add(5);

		Game game = new Game(width, length, ships);
		
		return game;
	}
}
