package game.world;

import game.world.items.Item;
import game.world.tiles.Tile;

/**
 * An area of the Game world 
 * @author Nick Tran
 *
 */
public class Area {
	
	private GameWorld world;
	private Tile[][] tiles; //the tiles that make up this area
	private Item[] items; //the items located on this area
	
	/**
	 * The constructor for the area
	 * It takes a GameWorld object which is the GameWorld in which this area is contained in
	 * @param world the world that contains this area
	 */
	public Area(GameWorld world){
		this.world = world;
	}

}
