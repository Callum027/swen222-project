package game.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import game.world.items.Item;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

/**
 * An area of the Game world
 * Holds all the tiles that make up this area along with the items contained within the area
 * @author Nick Tran
 *
 */
public class Area {
	private int areaID;
	private List<Item> items; // the items located in this area
	private Tile[][] tiles; //the tiles that make up this area
	private Tile[][][] walls;

	public Area(Tile[][] tiles, Tile[][][] walls, int areaID){
		this.tiles = tiles;
		items = new ArrayList<Item>();
		this.areaID = areaID;
		this.walls = walls;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile[][][] getWalls() {
		return walls;
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean addItem(Item item){
		return items.add(item);
	}

	public boolean removeItem(Item item){
		return items.remove(item);
	}

	private class AStar{

		private Queue<FringeNode> fringe;
		private Set<Position> visited;
		private Position goal;

		public AStar(Position goal){
			this.goal = goal;
			fringe = new PriorityQueue<FringeNode>(10, new AStarComparator<FringeNode>());
			visited = new HashSet<Position>();
		}

		public void runAlgorithm(){
			while(!fringe.isEmpty()){
				FringeNode current = fringe.poll();
				visited.add(current.getPosition());
			}
		}

		public Set<Position> getNeighbours(Position p){
			Set<Position> neighbours = new HashSet<Position>();

			return neighbours;
		}
	}

	private class FringeNode{

		// fields
		private Position position;
		private Position from;
		private int distance;
		private double heuristic;

		public FringeNode(Position position, Position from){
			this.position = position;
			this.from = from;
			distance = 0;
			heuristic = 0;
		}

		public Position getPosition(){
			return position;
		}

		public Position getFrom(){
			return from;
		}

		public int getDistance(){
			return distance;
		}

		public double getHeuristic(){
			return heuristic;
		}

		public double getEstimatedDistance(){
			return distance + heuristic;
		}
	}

	@SuppressWarnings("hiding")
	private class AStarComparator<FringeNode> implements Comparator<FringeNode>{

		@Override
		public int compare(FringeNode o1, FringeNode o2) {
			return 0;
		}

	}
}
