package ServeurTCP;

import java.net.Socket;


public class ThreadGameP2 extends ThreadGame {

	public ThreadGameP2(Socket joueur2, Game game) {
		super(joueur2, game);
		try {
			this.playerNum = 2;
			out.println("Vous etes joueur 2"); // leave this line
		} catch (Exception e) {
			System.out.println("Erreur instanciation ThreadGameP2");
		}
	}
}
