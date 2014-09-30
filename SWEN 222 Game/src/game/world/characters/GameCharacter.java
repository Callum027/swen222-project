package game.world.characters;

import game.world.items.Item;
import game.world.tiles.Tile;

import java.util.ArrayList;

/**
 * Represents the various game characters within our game like Enemies, NPCs and the Player
 * @author Nick Tran
 *
 */
public abstract class GameCharacter {

	private final String name;
	private int x;
	private int y;

	/**
	 * The Constructor
	 * @param x the x position
	 * @param y the y position
	 * @param name the name of the character
	 */
	public GameCharacter(int x, int y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	/**
	 * MOves the character to some postion
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 */
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}
}
