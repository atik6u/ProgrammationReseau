package ServeurTCP;

import java.util.ArrayList;

public class Game {
	private int width = 10;
	private int length = 10;
	private int [][] grid1;
	private int [][] grid2;
	private ArrayList<Integer> ships = new ArrayList<Integer>();
	private int turn;
	
	public Game(int width, int length, ArrayList<Integer> ships) {
		super();
		this.width = width;
		this.length = length;
		this.grid1 = new int [width][length];
		this.grid2 = new int [width][length];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				grid1[i][j] = 0;
				grid2[i][j] = 0;
			}
		}
		this.ships = ships;
		this.turn = 1;
	}
	
	private boolean checkAround(int x, int y, int gridNum) {
		int [][] grid;
		
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		
		if((x-1) >= 0) {
			if((y-1) >= 0)
				if(grid[x-1][y-1] != 0)
					return false;
			if(grid[x-1][y] != 0)
				return false;
			if((y+1) < length)
				if(grid[x-1][y+1] != 0)
					return false;
		}
		
		if((y-1) >= 0)
			if(grid[x][y-1] != 0)
				return false;
		if((y+1) < length)
			if(grid[x][y+1] != 0)
				return false;
		
		if((x+1) < width) {
			if((y-1) >= 0)
				if(grid[x+1][y-1] != 0)
					return false;
			if(grid[x+1][y] != 0)
				return false;
			if((y+1) < length)
				if(grid[x+1][y+1] != 0)
					return false;
		}
		
		return true;
	}
	
	private boolean check(int shipLength, int x, int y, char o, int gridNum) {
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
			if(!checkAround(x, y, gridNum))
				return false;
			if (o == 'h')
				x += 1;
			else
				y += 1;
		}
		
		return true;
	}
	
	public boolean fill(int shipLength, int x, int y, char o, int gridNum) {
		int [][] grid;
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		if(!check(shipLength, x, y, o, gridNum)) {
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

	public boolean checkTarget(int x, int y, int gridNum) {
		int [][] grid;
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		
		if ((grid[x][y] != 0) && (grid[x][y] != 1)) {
			return false;
		}
		return true;
	}

	public void attack(int x, int y, int gridNum) {
		int [][] grid;
		if(gridNum == 1) {
			grid = this.grid1;
		} else {
			grid = this.grid2;
		}
		
		if (grid[x][y] == 0) {
			grid[x][y] = 3;
		}
		
		if (grid[x][y] == 1) {
			grid[x][y] = 2;
		}
		
		//a completer (blow)
	}

	public int checkWin() {
		int [][] grid = this.grid1;
		
		boolean p1Win = true;
		boolean p2Win = true;
		
		for (int j = 0; j < length; j++) {
			for (int i = 0; i < width; i++) {
				if (grid[i][j] == 1) {
					p2Win = false;
					break;
				}
			}
			if (!p2Win) {
				break;
			}
		}
		
		grid = this.grid2;
		
		for (int j = 0; j < length; j++) {
			for (int i = 0; i < width; i++) {
				if (grid[i][j] == 1) {
					p1Win = false;
					break;
				}
			}
			if (!p1Win) {
				break;
			}
		}
		
		if (p1Win) {
			return 1;
		}
		if (p2Win) {
			return 2;
		}
		return 0;
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

	
	public int getTurn() {
		return turn;
	}

	
	public void setTurn(int turn) {
		this.turn = turn;
	}

	
}
