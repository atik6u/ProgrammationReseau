package ServeurTCP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadGame extends Thread {
	BufferedReader in;
	PrintWriter out;
	Game game;
	int playerNum;
	
	public ThreadGame(Socket joueur, Game game) {
		try {
			in = new BufferedReader(new InputStreamReader(joueur.getInputStream()));
			out = new PrintWriter(joueur.getOutputStream(), true);
			this.game = game;

		} catch (Exception e) {
			System.out.println("Erreur instanciation ThreadGame");
			e.printStackTrace();
		}
	}
	
	private void enterPositions() {
		try {
			String choice;
			int x;
			int y;
			char o;
			String[] strCoord;
			ArrayList<Integer> ships = this.game.getShips();
			int i = 0;
			
			while (i < ships.size()){
				this.out.println("Position de navire " + ships.get(i) + " cases?");
				choice = in.readLine();
				try {
					strCoord = choice.split(",");
					if (strCoord.length != 3) {
						throw new IllegalArgumentException();
					}
					x = strCoord[0].charAt(0);
					y = Integer.parseInt(strCoord[1]);
					o = strCoord[2].charAt(0);
					if (!game.remplir(ships.get(i), (int)(x - 'a'), y, o, this.playerNum)) {
						throw new IllegalArgumentException();	
					}
					afficher();
					System.out.println("J" + this.playerNum + ": Position de navire " + ships.get(i) + " cases: x = "+ x + ", y = "+ y + ", o = "+ o);
					i++;
				} catch (Exception e) {
					this.out.println("Format mauvais de la position du navire.\nVoulez-vous réentrer la position au format suivant: x,y,o");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Problème enterPosition");
		}
		
	}

	public void afficher () {
		int [][] grid;
		if(this.playerNum == 1) {
			grid = this.game.getGrid1();
		} else {
			grid = this.game.getGrid2();
		}
		out.print("    ");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print((char)('a' + i) + " ");
		}
		out.print("\n  _|");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print("_|");
		}
		out.print("\n");
		for (int j = 0; j < this.game.getLength(); j++) {
			out.print(j + " _|");
			for (int i = 0; i < this.game.getWidth(); i++) {
				out.print(grid[i][j] + " ");
			}
			out.print("\n");
		}
	}
	
	public void run() {
		try {
			this.out.println("Le jeu est commencé!");
			
			// Affichage des paramètres de la partie
			this.out.println("Vous avez une gille de " + game.getWidth() + "x" + game.getLength() + " cases");
			this.out.println("Vous avez " + game.getShips().size() + " navires: ");

			for (Integer ship : game.getShips()) {
				this.out.println("Un navire de " + ship + " cases.");
			}
			
			// Selection de la position des navires
			enterPositions();
			
			out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");  
			
			while (true) {
				String message = in.readLine();
				message = "joueur" + this.playerNum + ": " + message;
				System.out.println(message);
			}
		} catch (Exception e) {
			System.out.println("Problème ThreadChat run()");
		}
	}
}
