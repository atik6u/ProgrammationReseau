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
//		try {
		String choice;
		int x;
		int y;
		char o;
		String[] strCoord;
		ArrayList<Integer> ships = this.game.getShips();
		int i = 0;
		
		showMyGrid();
		while (i < ships.size()){
			this.out.println("Position de navire " + ships.get(i) + " cases?");
			try {
				choice = in.readLine();
			} catch (Exception e) {
				out.println("Erreur dans scanne. Voulez-vous réessayer...");
				continue;
			}
			
			try {
				if (checkPositionFormat(choice)) {
					this.out.println("Mauvais format de la position du navire.");
					throw new IllegalArgumentException();
				}
				strCoord = choice.split(",");
				x = strCoord[0].charAt(0);
				y = Integer.parseInt(strCoord[1]);
				o = strCoord[2].charAt(0);
				if (!game.fill(ships.get(i), (int)(x - 'a'), y - 1, o, this.playerNum)) {
					this.out.println("Mauvaise Position du navire.");
					throw new IllegalArgumentException();	
				}
				showMyGrid();
				System.out.println("J" + this.playerNum + ": Position de navire " + ships.get(i) + " cases: x = "+ x + ", y = "+ y + ", o = "+ o);
				i++;
			} catch (Exception e) {
				this.out.println("Voulez-vous réentrer la position au format suivant: x,y,o (ex: a,1,v)");
			}
		}
		out.println("En attendant l'adversaire.");
//		} catch (Exception e) {
//			System.out.println("Problème enterPosition");
//		}
		
	}

	public void showMyGrid() {
		out.println("Ma grille:");
		int [][] grid;
		if(this.playerNum == 1) {
			grid = this.game.getGrid1();
		} else {
			grid = this.game.getGrid2();
		}
		out.print("     ");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print((char)('a' + i) + " ");
		}
		out.print("\n  __|");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print("_|");
		}
		out.print("\n");
		for (int j = 0; j < this.game.getLength(); j++) {
			if (j > 8)
				out.print((j+1) + " _|");
			else
				out.print((j+1) + " __|");
			for (int i = 0; i < this.game.getWidth(); i++) {
				if(grid[i][j] == 1) {
					out.print("O ");
				}
				else if(grid[i][j] == 0) {
					out.print("  ");
				}
			}
			out.print("\n");
		}
	}
	
	public void showOtherGrid() {
		out.println("Grille de l'adversaire:");
		int [][] grid;
		if(this.playerNum == 1) {
			grid = this.game.getGrid2();
		} else {
			grid = this.game.getGrid1();
		}
		
		out.print("     ");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print((char)('a' + i) + " ");
		}
		out.print("\n  __|");
		for (int i = 0; i < this.game.getWidth(); i++) {
			out.print("_|");
		}
		out.print("\n");
		for (int j = 0; j < this.game.getLength(); j++) {
			if (j > 8)
				out.print((j+1) + " _|");
			else
				out.print((j+1) + " __|");
			for (int i = 0; i < this.game.getWidth(); i++) {
				if ((grid[i][j] == 0) || (grid[i][j] == 1)) {
					out.print("  ");
				}
				else if(grid[i][j] == 2) {
					out.print("X ");
				}
				else if(grid[i][j] == 3) {
					out.print("M ");
				}
				else {
					out.print("F ");
				}
			}
			out.print("\n");
		}
	}
	
	public void combat() {
		out.println("Le combat a commencé");
		int win = 0;
		String target;
		int x;
		int y;
		String[] strCoord;
		int otherPlayer;
		
		 while (win == 0) {
			if (game.getTurn() == playerNum) {
				out.println("À votre tour:");
				try {
					target = in.readLine();
				} catch (Exception e) {
					out.println("Erreur dans scanne. Voulez-vous réessayer...");
					continue;
				}
				
				if (!checkTargetFormat(target)) {
					out.println("Movais format. Voulez-vous réessayer...");
					continue;
				}
				
				strCoord = target.split(",");
				x = strCoord[0].charAt(0);
				y = Integer.parseInt(strCoord[1]);
				otherPlayer = (playerNum%2) + 1;
				
				if (game.checkTarget((int)(x - 'a'), y - 1, otherPlayer)) {
					game.attack((int)(x - 'a'), y - 1, otherPlayer);
					win = game.checkWin();
					if(win == 0) {
						game.setTurn(otherPlayer);
						out.println("En attendant l'adversaire.");
					}
				}
				else {
					out.println("Cible déjà touché. Voulez-vous réessayer...");
				}
			}
			
			if (win == playerNum) {
				out.print("GAME OVER\n Vous avez perdu :(");
			}
			else {
				out.print("GAME OVER\n Vous avez gagné :)");
			}
				
		}
	}	
	
	private boolean checkPositionFormat(String position) {
		try {
			String[] strCoord = position.split(",");
			
			if(strCoord.length != 3) {
				return false;
			}
			
			int x = strCoord[0].charAt(0);
			int y = Integer.parseInt(strCoord[1]);
			char o = strCoord[0].charAt(0);
			if ( ((int)x < 'a') || ((int)x >= ('a' + game.getWidth()))) {
				return false;
			}
			if ((y < 0) || (y >= game.getLength())) {
				return false;
			}
			if ((o != 'v') && (o != 'h')) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean checkTargetFormat(String target) {
		try {
			String[] strCoord = target.split(",");
			
			if(strCoord.length != 2) {
				return false;
			}
			
			int x = strCoord[0].charAt(0);
			int y = Integer.parseInt(strCoord[1]);
			
			if ( ((int)x < 'a') || ((int)x >= ('a' + game.getWidth()))) {
				return false;
			}
			if ((y < 0) || (y >= game.getLength())) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
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
			
			combat();
			
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
