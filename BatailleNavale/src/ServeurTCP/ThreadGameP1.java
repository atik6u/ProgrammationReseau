package ServeurTCP;

import java.net.Socket;


public class ThreadGameP1 extends ThreadGame {

	public ThreadGameP1(Socket joueur1, Game game) {
		super(joueur1, game);
		try {
			this.playerNum = 1;
			out.println("Vous etes joueur 1"); // leave this line
		} catch (Exception e) {
			System.out.println("Erreur instanciation ThreadGameP1");
		}
	}
}
