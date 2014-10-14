package game.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import game.Main;
import game.exceptions.AreaIDNotFoundException;
import game.exceptions.GameException;
import game.exceptions.PlayerIDNotFoundException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.characters.Enemy;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.items.Item;
import game.world.items.MoveableItem;
import game.world.tiles.Tile;

/**
 * An area of the Game world
 * Holds all the tiles that make up this area along with the items contained within the area
 * @author Nick Tran
 *
 */
public class Area  implements Streamable{

	// fields
	private final int id;
	private Map<Integer,Merchant> merchants; //a mapping from a unique identifier to the respective merchant character
	private Map<Integer,Enemy> enemies; //a mapping from unique identifiers to their respective enemy character
	private Map<Integer,Item> items; //a mapping from unique identifiers to the items located in this area
	private Map<Integer, Player> players;
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
		items = new HashMap<Integer,Item>();
		enemies = new HashMap<Integer,Enemy>();
		players = new HashMap<Integer, Player>();
		this.id = areaID;
		this.walls = walls;
	}

	/**
	 * Returns the unique identifier for this Area.
	 * @return
	 */
	public int getID() {
		return id;
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
	 * Returns  a shallow clone of the map of items
	 * that are currently in this area.
	 *
	 * @return
	 * 		--- map of integers to items
	 */
	public Map<Integer,Item> getItems() {
		return new HashMap<Integer,Item>(items);
	}

	/**
	 * Adds the specified item to the map of items currently
	 * in this area.
	 *
	 * @param item
	 * 		--- the item to add
	 * @return
	 * 		--- returns the previous item associated with the ID or null if there was no previous mapping of the ID
	 */
	public Item addItem(Item item){
		return items.put(item.getID(), item);
	}

	/**
	 * Removes the specified item from the map of items currently in
	 * this area.
	 *
	 * @param item
	 * 		--- the item to be removed
	 * @return
	 * 		---	the previous item associated with the ID, or null if there was no mapping for the ID.
	 */
	public Item removeItem(Item item){
		return items.remove(item.getID());
	}

	/**
	 * retrieves the items contained within the world using their id
	 * @param id the id that is used to get the item with this id
	 * @return the item with the given id
	 */
	public Item getItem(int id){
		return items.get(id);
	}

	/**
	 * Returns  a shallow clone of the map of enemies
	 * that are currently in this area.
	 *
	 * @return
	 * 		--- map of integers to enemies
	 */
	public Map<Integer,Enemy> getEnemies() {
		return new HashMap<Integer,Enemy>(enemies);
	}

	/**
	 * Adds the specified enemy to the map of enemies currently
	 * in this area.
	 *
	 * @param enemy
	 * 		--- the enemy to add
	 * @return
	 * 		--- returns the previous enemy associated with the ID or null if there was no previous mapping of the ID
	 */
	public Enemy addEnemy(Enemy enemy){
		return enemies.put(enemy.getID(), enemy);
	}

	/**
	 * Removes the specified enemy from the map of enemies currently in
	 * this area.
	 *
	 * @param enemy
	 * 		--- the enemy to be removed
	 * @return
	 * 		---	the previous enemy associated with the ID, or null if there was no mapping for the ID.
	 */
	public Enemy removeEnemy(Enemy enemy){
		return enemies.remove(enemy.getID());
	}

	/**
	 * retrieves the enemies contained within the world using their id
	 * @param id the id that is used to get the enemy with this id
	 * @return the enemy with the given id
	 */
	public Enemy getEnemy(int id){
		return enemies.get(id);
	}

	/**
	 * Returns  a shallow clone of the map of a merchant
	 * that are currently in this area.
	 *
	 * @return
	 * 		--- map of integers to merchants
	 */
	public Map<Integer,Merchant> getMerchants() {
		return new HashMap<Integer,Merchant>(merchants);
	}
	
	/**
	 * Adds a player to this Area.
	 * 
	 * @param player
	 * 		--- player to add
	 */
	public void addPlayer(Player player){
		players.put(player.getId(), player);
	}
	
	/**
	 * Returns the player with the specified ID. if that
	 * player is not located in this area, return null.
	 * 
	 * @param playerID
	 * 		--- unique id of player
	 * @return
	 * 		--- player, or null
	 */
	public Player getPlayer(int playerID){
		if(players.containsKey(playerID)){
			return players.get(playerID);
		}
		return null;
	}
	
	/**
	 * Removes the player with the specified ID from this
	 * Area.
	 * 
	 * @param playerID
	 * 		--- id of player to remove
	 */
	public void removePlayer(int playerID){
		if(players.containsKey(playerID)){
			players.remove(playerID);
		}
	}

	/**
	 * Adds the specified merchant to the map of merchants currently
	 * in this area.
	 *
	 * @param merchant
	 * 		--- the merchant to add
	 * @return
	 * 		--- returns the previous merchant associated with the ID or null if there was no previous mapping of the ID
	 */
	public Merchant addMerchant(Merchant merchant){
		return merchants.put(merchant.getId(), merchant);
	}

	/**
	 * Removes the specified merchant from the map of merchants currently in
	 * this area.
	 *
	 * @param merchant
	 * 		--- the merchant to be removed
	 * @return
	 * 		---	the previous merchant associated with the ID, or null if there was no mapping for the ID.
	 */
	public Merchant removeMerchant(Merchant merchant){
		return merchants.remove(merchant.getId());
	}

	/**
	 * retrieves the merchants contained within the world using their id
	 * @param id the id that is used to get the merchant with this id
	 * @return the merchant with the given id
	 */
	public Merchant getMerchant(int id){
		return merchants.get(id);
	}
	
	/**
	 * Returns the Drawable object that is situated at the specified
	 * position. If there is not a Drawable object at that position
	 * then null is returned.
	 * @param p
	 * 		--- the position to check
	 * @return
	 * 		--- Drawable object, or null
	 */
	public Drawable getDrawableObject(Position p){
		for(Item i : items.values()){
			if(i.getPosition().equals(p)){
				return i;
			}
		}
		for(Player pl : players.values()){
			if(pl.getPosition().equals(p)){
				return pl;
			}
		}
		for(Enemy e : enemies.values()){
			if(e.getPosition().equals(p)){
				return e;
			}
		}
		for(Merchant m : merchants.values()){
			if(m.getPosition().equals(p)){
				return m;
			}
		}
		return null;
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
		for (Entry<Integer, Item> entry : items.entrySet()){
			if(entry.getValue().getPosition().equals(p) && !(entry.getValue() instanceof MoveableItem)){
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

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)getID());
	}

	/**
	 * reads an area from the inputstream
	 * @param is the inputstream
	 * @return the area with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Area read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		Area area = Main.getGameWorld().getArea(id);

		if (area == null)
			throw new AreaIDNotFoundException(id);

		return area;
	}

}
