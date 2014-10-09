package game.world.characters;

import game.world.Position;
import game.world.items.MoveableItem;

import java.awt.Image;
import java.util.ArrayList;

/**
 * Represents the various game characters within our game like Enemies, NPCs and the Player
 * @author Nick Tran
 *
 */
public abstract class GameCharacter {

	private ArrayList<MoveableItem> items;
	private final String name;
	private Position position;
	private Image[] images;
	private int direction;
	private int cats; //the amount of money/points the player has
	private final int id;
	private final int MAXIMUM_CAPACITY = 20;

	public GameCharacter(Position position, String name, int id) {
		this.name = name;
		this.setCats(0);
		this.setDirection(0);
		this.id = id;
		items = new ArrayList<MoveableItem>(MAXIMUM_CAPACITY);
	}

	public void moveToPosition(Position position){
		this.setPosition(position);
	}

	private void setPosition(Position newPosition) {
		this.position = newPosition;
	}

	public void dropItems(){

	}

	public boolean addItem(MoveableItem item){
		return items.add(item);
	}

	public boolean removeItem(MoveableItem item){
		return items.remove(item);
	}

	public String getName() {
		return name;
	}

	public ArrayList<MoveableItem> getItems() {
		return items;
	}

	public Position getPosition() {
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
