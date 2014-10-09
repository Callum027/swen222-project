package game.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import game.world.items.Item;
import game.world.items.MoveableItem;
import game.world.tiles.Tile;

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
	
	public boolean isMoveablePosition(Position p){
		// if position is outside the bounds of the area return false
		if(p.getX() < 0 || p.getX() >= tiles[0].length || p.getY() < 0 || p.getY() >= tiles.length){
			return false;
		}
		// if position is occupied by an item return false
		for(Item item : items){
			if(item.getPosition().equals(p) && !(item instanceof MoveableItem)){
				return false;
			}
		}
		return true;
	}
	
	public Queue<Position> findPath(Position start, Position goal){
		AStar aStar = new AStar(start, goal);
		return aStar.runAlgorithm();
	}
	
	/**
	 * A version of the A* search algorithm to find the shortest path between a start and goal position.
	 * This is implemented by the Area class to find the path between where the player currently is and
	 * the position that they wish to move to.
	 * 
	 * @author David Sheridan
	 *
	 */
	private class AStar{
		
		// fields
		private Queue<FringeNode> fringe;
		private Set<Position> visited;
		private Position goal;
		
		/**
		 * Constructor:
		 * Constructs an instance of the A* algorithm, which takes the start and goal positions.
		 * 
		 * @param start
		 * 				--- the start position
		 * @param goal
		 * 				--- the goal position
		 */
		public AStar(Position start, Position goal){
			this.goal = goal;
			fringe = new PriorityQueue<FringeNode>(10, new FringeNodeComparator<FringeNode>());
			fringe.add(new FringeNode(start, goal, null, 0));
			visited = new HashSet<Position>();
		}
		
		/**
		 * Runs the A* algorithm to find the shortest path between the start and goal positions.
		 * If there is a path found between the start and goal position a queue of positions
		 * that make up the path will be returned, otherwise if a path cannot be found null
		 * will be returned instead.
		 * 
		 * @return
		 * 			--- the path from the start to the goal position
		 */
		public Queue<Position> runAlgorithm(){
			while(!fringe.isEmpty()){
				FringeNode current = fringe.poll();
				visited.add(current.getPosition());
				
				// check if the goal has been reached
				if(current.getPosition().equals(goal)){
					return getPath(current);
				}
				
				// add neighbours to the fringe
				for(Position p : getNeighbours(current.getPosition())){
					if(!visited.contains(p)){
						fringe.add(new FringeNode(p, goal, current, current.getDistance() + 1));
					}
				}
			}
			// at this point the fringe is empty and no path was found
			return null;
		}
		
		/**
		 * Returns a set of the neighbouring positions from the specified position.
		 * will only add a neighbouring position if it is able to be moved to.
		 * 
		 * @param p
		 * 			--- the point to find neighbours for
		 * @return
		 * 			--- a set of neighbouring positions
		 */
		private Set<Position> getNeighbours(Position p){
			Set<Position> neighbours = new HashSet<Position>();
			
			return neighbours;
		}
		
		/**
		 * Returns the path from the specified node to the start.
		 * 
		 * @param node
		 * 			--- the end node
		 * @return
		 * 			--- 
		 */
		private Queue<Position> getPath(FringeNode node){
			Queue<Position> path = new LinkedList<Position>();
			path.offer(node.getPosition());
			FringeNode current = node.getFrom();
			while(current.getFrom() != null){
				path.offer(current.getPosition());
				current = current.getFrom();
			}
			return path;
		}
	}
	
	/**
	 * FringeNode is a node used of the fringe of the A* algorithm implementation. It holds the
	 * position that it represents along with the goal position, the FringeNode that preceded it,
	 * the distance traveled so far and the estimated distance to the goal. 
	 * 
	 * @author David Sheridan
	 *
	 */
	private class FringeNode{

		// fields
		private Position position;
		private Position goal;
		private FringeNode from;
		private int distance;
		private double heuristic;
		
		/**
		 * Constructor:
		 * Constructs an instance of a FringeNode.
		 * 
		 * @param position
		 * 				--- the position the FringeNode represents
		 * @param goal
		 * 				--- the goal position
		 * @param from
		 * 				--- the FringeNode that preceded this one
		 * @param distance
		 * 				--- the distance traveled so far
		 */
		public FringeNode(Position position, Position goal, FringeNode from, int distance){
			this.position = position;
			this.goal = goal;
			this.from = from;
			this.distance = distance;
			heuristic = calculateHeuristic();
		}
		
		/**
		 * Returns the position of this FringeNode.
		 * @return
		 * 			--- position of the fringe node
		 */
		public Position getPosition(){
			return position;
		}
		
		/**
		 * Returns the FringeNode that preceded this FringeNode.
		 * 
		 * @return
		 * 			--- previous fringe node
		 */
		public FringeNode getFrom(){
			return from;
		}
		
		/**
		 * Returns the distance traveled from the start position to the
		 * position of this FringeNode.
		 * 
		 * @return
		 * 			--- distance travelled so far
		 */
		public int getDistance(){
			return distance;
		}
		
		/**
		 * Returns the estimated remaining distance from the position of this FringeNode
		 * to the goal position. The heuristic utilizes the Pythagoras Theorem
		 * to work out the straight line distance between the two positions.
		 * 
		 * @return
		 * 			--- estimated remaining distance to goal
		 */
		private double calculateHeuristic(){
			int width = Math.abs(position.getX() - goal.getX());
			int height = Math.abs(position.getY() - goal.getY());
			return Math.sqrt((width*width) + (height*height));
		}
		
		/**
		 * Returns the estimated distance, which is equal to the actual distance
		 * traveled so far in addition to the estimated distance remaining.
		 * 
		 * @return
		 * 			--- estimated distance to goal from start
		 */
		public double getEstimatedDistance(){
			return distance + heuristic;
		}
		
	}
	
	/**
	 * A FringeNodeComparator is used to determine the priority of a collection of FringeNodes.
	 * Priority is determined by the estimated distance to the goal.
	 * 
	 * @author David Sheridan
	 *
	 * @param <FringeNode>
	 */
	@SuppressWarnings("hiding")
	private class FringeNodeComparator<FringeNode> implements Comparator<FringeNode>{
		
		/**
		 * Compares two FringeNodes to determine which has the greater priority.
		 * Returns an integer value which determines their ordering.
		 * 
		 *   - a value of -1 means o1 has greater priority
		 *   - a value of 1 means o2 has greater priority
		 *   - a value of 0 means o1 and o2 have equal priority
		 */
		@Override
		public int compare(FringeNode o1, FringeNode o2) {
			double d1 = ((game.world.Area.FringeNode)o1).getEstimatedDistance();
			double d2 = ((game.world.Area.FringeNode)o2).getEstimatedDistance();
			
			if(d1 < d2){
				return -1;
			}
			else if(d1 > d2){
				return 1;
			}
			return 0;
		}

	}
}
