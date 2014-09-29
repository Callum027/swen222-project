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

	private final String name;
	private final int x;
	private final int y;
	
	public GameCharacter(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name; 
	}
}
