package game.world.characters;

import game.world.items.Item;
import game.world.tiles.Tile;

import java.util.ArrayList;

/**
 * 
 * @author Nick Tran
 *
 */
public abstract class GameCharacter {
	
	private int x;
	private int y;
	private ArrayList<Item> items;
	
	private enum Direction{
		NORTH, NORTH_EAST, EAST, SOUTH_EAST,
		SOUTH, SOUTH_WEST, WEST, NORTH_WEST
	}

	public GameCharacter(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Tile moveTo(Tile tile){
		this.x = tile.x;
		return tile;
	}
}
