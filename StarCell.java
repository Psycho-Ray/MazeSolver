package maze;

import java.awt.Point;

public class StarCell {
	public int x,y,d;
	
	public StarCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.d = 0;
	}
	
	public StarCell(int x, int y, int d) {
		this.x = x;
		this.y = y;
		this.d = d;
	}
	
	public StarCell(StarCell aux) {
		this.x = aux.x;
		this.y = aux.y;
		this.d = aux.d;
	}
	
	//Retorna a distância até o ponto destino
	private int ManhattanDistance(Point goal) {
		return Math.abs(goal.x - this.x) + Math.abs(goal.y - this.x);
	}
	
	//Retorna a distância total utilizada para o algoritmo A*
	public int f(Point goal) {
		return d + ManhattanDistance(goal);
	}
}
