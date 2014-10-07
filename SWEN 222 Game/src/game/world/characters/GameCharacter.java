package game.world.characters;

import game.world.items.Item;
import game.world.items.MoveableItem;
import game.world.tiles.Tile;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Represents the various game characters within our game like Enemies, NPCs and the Player
 * @author Nick Tran
 *
 */
public abstract class GameCharacter {

	private MoveableItem[] items; 
	private final String name;
	private Point position;
	private Image[] images;
	private int direction;
	private int cats;
	private int ID;
	
	/**
	 * The Constructor
	 * @param x the x position
	 * @param y the y position
	 * @param name the name of the character
	 */
	public GameCharacter(Point position, String name) {
		this.name = name;
	}

	/**
	 * Moves the character to some postion
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 */
	public void moveToPosition(Point position){
		this.position = position;
	}
	
	public void dropItems(){
		
	}

	public String getName() {
		return name;
	}
}
