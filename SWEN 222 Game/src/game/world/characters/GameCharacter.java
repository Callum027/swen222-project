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
	private int cats; //the amount of money/points the player has
	private final int id;

	/**
	 * The Constructor
	 * @param x the x position
	 * @param y the y position
	 * @param name the name of the character
	 */
	public GameCharacter(Point position, String name, int id, MoveableItem[] items) {
		this.name = name;
		this.setCats(0);
		this.setDirection(0);
		this.id = id;
	}

	/**
	 * Moves the character to some postion
	 * @param x the new x coordinate
	 * @param y the new y coordinate
	 */
	public void moveToPosition(Point position){
		this.setPosition(position);
	}

	private void setPosition(Point newPosition) {
		this.position = newPosition;
	}

	public void dropItems(){

	}

	public String getName() {
		return name;
	}

	public MoveableItem[] getItems() {
		return items;
	}

	public Point getPosition() {
		return position;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getCats() {
		return cats;
	}

	public void setCats(int cats) {
		this.cats = cats;
	}

	public int getId() {
		return id;
	}
}
