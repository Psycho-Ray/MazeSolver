package maze;

import java.awt.Point;

public class StarCell implements Comparable<StarCell>{
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
	
	public StarCell(Point p, int d) {
		this.x = p.x;
		this.y = p.y;
		this.d = d;
	}
	
	public StarCell(StarCell aux) {
		this.x = aux.x;
		this.y = aux.y;
		this.d = aux.d;
	}
	
	public Point point() {
		return new Point(this.x, this.y);
	}
	
	//Retorna a distância até o ponto destino
	private int ManhattanDistance(Point goal) {
		return Math.abs(goal.x - this.x) + Math.abs(goal.y - this.x);
	}
	
	//Retorna a distância total utilizada para o algoritmo A*
	public int f(Point goal) {
		return d + ManhattanDistance(goal);
	}
	
	@Override
	public int compareTo(StarCell aux) {
		return this.d - aux.d;
	}
}
