package maze;

import java.awt.Point;
import java.util.*;

public class PathFinder {
	private LinkedList<Point> auxPath;
	private LinkedList<Point> footPrint;
	private ArrayList<LinkedList<Point>> paths;
	
	private boolean[][] used;
	private byte[][] source;
	
	public PathFinder(byte source[][]) {
		this.source = source;
	}
	
	public LinkedList<Point> showFootPrint() {
		return footPrint;
	}
	
	/*DFS util*/
	private void dfsRun(int x, int y, int stage) {
		//Marca a posição atual como visitada e a adiciona ao caminho
		used[x][y] = true;
		footPrint.add(new Point(x, y));
		auxPath.add(new Point(x, y));
		
		//Caso saída encontrada
		if (source[x][y] == 3) {
			paths.add(new LinkedList<Point>());
			paths.get(stage++).addAll(auxPath);
		}
		
		//Visita os nós vizinhos
		if (x>0 && source[x-1][y] != 1) dfsRun(x-1, y, stage);	//Esquerda
		if (y+1 < source[x].length && source[x][y+1] != 1) dfsRun(x, y+1, stage);	//Baixo
		if (x+1 < source.length && source[x+1][y] != 1)	dfsRun(x+1, y, stage);	//Direita
		if (y>0 && source[x][y-1] != 1) dfsRun(x, y+1, stage);	//Cima
		
		//Remove a posição atual da lista.
		auxPath.remove();
	}
	
	/*DFS*/
	public ArrayList<LinkedList<Point>> dfs(Point entrance, int nExits) {
		//Inicialização
		auxPath = new LinkedList<Point>();
		footPrint = new LinkedList<Point>();
		paths = new ArrayList<LinkedList<Point>>(nExits);
		used = new boolean[source.length][];
		
		//Retorna o(s) caminho(s) encontrado(s) - ADICIONAR CASO SAÍDA NÃO ENCONTRADA(?)
		dfsRun(entrance.x, entrance.y, 0);
		return paths;
	}
	
	
	/*BFS util*/
	private void extractFromParent(LinkedList<Point> path, Point[][] parent, int x, int y) {
		if (parent[x][y] == null) return;
		extractFromParent(path, parent, parent[x][y].x, parent[x][y].y);
		path.add(new Point(x, y));
	}
	
	/*BFS*/
	public ArrayList<LinkedList<Point>> bfs(Point entrance, int nExits) {
		int stage = 0;
		
		//Inicialização
		auxPath = new LinkedList<Point>();
		footPrint = new LinkedList<Point>();
		paths = new ArrayList<LinkedList<Point>>(nExits);
		used = new boolean[source.length][];
		
		//Fila e Matriz de antecedência
		LinkedList<Point> queue = new LinkedList<Point>();
		Point[][] parent = new Point[source.length][];
		
		//Adiciona a entrada
		used[entrance.x][entrance.y] = true;
		queue.add(entrance);
		footPrint.add(new Point(entrance.x, entrance.y));
		
		//Enquanto existirem vértices a serem visitados...
		while (!queue.isEmpty()) {
			//Salva o nó atual e o tira da fila 
			Point now = queue.pop();
			
			//Caso seja uma saída, gera o caminho para ela
			if (source[now.x][now.y] == 3) {
				paths.add(new LinkedList<Point>());
				extractFromParent(paths.get(stage++), parent, now.x, now.y);
			}
			
			//Adiciona à fila os pixels adjacentes a "now", os marca como visitados e define seu antecessor
			//Esquerda
			if (now.x > 0 && source[now.x - 1][now.y] != 1 && !used[now.x - 1][now.y]) {
				used[now.x - 1][now.y] = true;
				parent[now.x - 1][now.y] = now;
				queue.add(new Point(now.x - 1, now.y));
				footPrint.add(new Point(now.x - 1, now.y));
			}
			
			//Baixo
			if (now.y < source.length && source[now.x][now.y + 1] != 1 && !used[now.x][now.y + 1]) {
				used[now.x][now.y + 1] = true;
				parent[now.x][now.y + 1] = now;
				queue.add(new Point(now.x, now.y + 1));
				footPrint.add(new Point(now.x, now.y + 1));
			}
			
			//Direita
			if (now.x < source.length && source[now.x + 1][now.y] != 1 && !used[now.x + 1][now.y]) {
				used[now.x + 1][now.y] = true;
				parent[now.x + 1][now.y] = now;
				queue.add(new Point(now.x + 1, now.y));
				footPrint.add(new Point(now.x, now.y + 1));
			}
			
			//Cima
			if (now.y > 0 && source[now.x][now.y - 1] != 1 && !used[now.x][now.y - 1]) {
				used[now.x][now.y - 1] = true;
				parent[now.x][now.y - 1] = now;
				queue.add(new Point(now.x, now.y - 1));
				footPrint.add(new Point(now.x, now.y + 1));
			}
		}
		
		
		
		//Retorna o(s) caminho(s) encontrado(s) - ADICIONAR CASO SAÍDA NÃO ENCONTRADA(?)
		return paths;
	}
}
