package ServeurTCP;

import java.util.ArrayList;

public class Game {
	private int width = 10;
	private int length = 10;
	private int [][] grid1;
	private int [][] grid2;
	private ArrayList<Integer> ships = new ArrayList<Integer>();
	
	public Game(int width, int length, ArrayList<Integer> ships) {
		super();
		this.width = width;
		this.length = length;
		this.grid1 = this.grid2 = new int [width][length];
		for (int i = 0; i < grid1.length; i++) {
			for (int j = 0; j < grid1[i].length; j++) {
				grid1[i][j] = 0;
				grid2[i][j] = 0;
			}
		}
		this.ships = ships;
	}
	
	private boolean verifier(int shipLength, int x, int y, char o, int gridNum) {
		if((o != 'h') && (o != 'v')) {
			return false;
		}
		
		int [][] grid;
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		
		
		for (int i = 0; i < shipLength; i++) {
			if((x < 0) || (x >= width) || (y < 0) || (y >= length))
				return false;
			if(grid[x][y] != 0)
				return false;
			if (o == 'h')
				x += 1;
			else
				y += 1;
		}
		
		return true;
	}
	
	public boolean remplir(int shipLength, int x, int y, char o, int gridNum) {
		int [][] grid;
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		if(!verifier(shipLength, x, y, o, gridNum)) {
			return false;
		}
		
		//remplissage		

		for (int i = 0; i < shipLength; i++) {
			if(o == 'h') {
				grid[x + i][y] = 1;
			} else {
				grid[x][y + i] = 1;
			}
		}
		
		return true;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int[][] getGrid1() {
		return grid1;
	}

	public void setGrid1(int[][] grid1) {
		this.grid1 = grid1;
	}

	public int[][] getGrid2() {
		return grid2;
	}

	public void setGrid2(int[][] grid2) {
		this.grid2 = grid2;
	}

	public ArrayList<Integer> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Integer> ships) {
		this.ships = ships;
	}
	
	
	
}
