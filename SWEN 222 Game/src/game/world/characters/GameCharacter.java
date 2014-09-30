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
	private int x;
	private int y;
	
	public GameCharacter(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name; 
	}
	
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}
}
