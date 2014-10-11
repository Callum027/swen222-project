package game.world;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

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
	
	// fields
	private final int areaID;
	private List<Item> items; // the items located in this area
	private Tile[][] tiles; //the tiles that make up this area
	private Tile[][][] walls;
	
	/**
	 * Constructor:
	 * Constructs an instance of an Area. takes a 2D array of Tiles which
	 * represent the floor layout, a 3D array of tiles which represents the wall
	 * layout and a unique identifying integer.
	 * 
	 * @param tiles
	 * 			--- floor layout
	 * @param walls
	 * 			--- wall layout
	 * 			walls[0] = NORTH
	 * 			walls[1] = EAST
	 * 			walls[2] = SOUTH
	 * 			walls[3] = WEST
	 * @param areaID
	 * 			--- unique identifier
	 */
	public Area(Tile[][] tiles, Tile[][][] walls, int areaID){
		this.tiles = tiles;
		items = new ArrayList<Item>();
		this.areaID = areaID;
		this.walls = walls;
	}
	
	/**
	 * Returns the unique identifier for this Area.
	 * @return
	 */
	public int getAreaID() {
		return areaID;
	}

	
	/**
	 * Returns the 2D array of tiles which represent the
	 * floor layout of this Area.
	 * 
	 * @return
	 * 		--- floor layout
	 */
	public Tile[][] getTiles() {
		return tiles;
	}
	
	/**
	 * Returns the 2D array of tiles which represents the wall
	 * specified by the given direction. The direction must be a
	 * value between 0 and 3.
	 * 
	 * @param direction
	 * 			--- the position of the wall
	 * @return
	 * 			--- wall layout
	 */
	public Tile[][] getWall(int direction) {
		if(direction < 0 && direction >= walls.length){
			return null;
		}
		return walls[0];
	}
	
	/**
	 * Returns  a shallow clone of the list of items 
	 * that are currently in this area.
	 * 
	 * @return
	 * 		--- list of items
	 */
	public List<Item> getItems() {
		return new ArrayList<Item>(items);
	}
	
	/**
	 * Adds the specified item to the list of items currently
	 * in this area.
	 * 
	 * @param item
	 * 		--- the item to add
	 * @return
	 * 		--- returns true if the item was added, false otherwise
	 */
	public boolean addItem(Item item){
		return items.add(item);
	}
	
	/**
	 * Removes the specified item from the list of items currently in
	 * this area.
	 * 
	 * @param item
	 * 		--- the item to be removed
	 * @return
	 * 		---	returns true if the item was removed, false otherwise
	 */
	public boolean removeItem(Item item){
		return items.remove(item);
	}
	
	/**
	 * Returns true if the specified position is a moveable position.
	 * A moveable position is defined as a position within the bounds
	 * of the area that does not have a non moveable item on it.
	 * 
	 * @param p
	 * 		--- position to check
	 * @return
	 * 		--- true if the position is moveable, false otherwise
	 */
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
	
	/**
	 * Finds the path from the specified start position to the goal. Uses a version
	 * of the A* algorithm to do this. Returns the path between the two positions
	 * if there is one found. Returns null otherwise.
	 * @param start
	 * 			--- the start position
	 * @param goal
	 * 			--- the goal position
	 * @return
	 * 			--- the path if found, null otherwise
	 */
	public Stack<Position> findPath(Position start, Position goal){
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
		public Stack<Position> runAlgorithm(){
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
			neighbours.add(new Position(p.getX() - 1, p.getY()));
			neighbours.add(new Position(p.getX() + 1, p.getY()));
			neighbours.add(new Position(p.getX(), p.getY() - 1));
			neighbours.add(new Position(p.getX(), p.getY() + 1));
			Set<Position> moveable = new HashSet<Position>();
			for(Position pos : neighbours){
				if(isMoveablePosition(pos)){
					moveable.add(pos);
				}
			}
			return moveable;
		}

		/**
		 * Returns the path from the specified node to the start.
		 *
		 * @param node
		 * 			--- the end node
		 * @return
		 * 			---
		 */
		private Stack<Position> getPath(FringeNode node){
			Stack<Position> path = new Stack<Position>();
			if(node == null){
				return path;
			}
			path.push(node.getPosition());
			FringeNode current = node.getFrom();
			while(current != null){
				path.push(current.getPosition());
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
