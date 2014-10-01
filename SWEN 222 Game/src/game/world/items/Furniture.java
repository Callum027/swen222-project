package game.world.items;

import game.world.characters.PlayableCharacter;

/**
 * A furniture is an immovable item that's also not a container
 * @author Nick Tran
 *
 */
public class Furniture extends Item{

	private MovableItem item; //you can get items by interacting with certain pieces of furniture

	/**
	 * The Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the size of the furniture
	 * @param name the name of the furniture
	 * @param item the item you can get from interacting with the furniture
	 */
	public Furniture(int x, int y, int height, String name, MovableItem item) {
		super(x, y, height, name);
		this.setItem(item);
	}

	/**
	 * The method invoked when the player clicks on this piece of furniture
	 * @param player the player interacting with this piece of furniture
	 * @return the description of the furniture
	 */
	public String interact(PlayableCharacter player){
		return "This is a piece of furniture";
	}

	/**
	 * Retrieves the item that the furniture contains
	 * @return the item in the furniture
	 */
	public MovableItem getItem() {
		return item;
	}

	/**
	 * Sets a new item to be given to the player by the furniture
	 * @param item the new item that's given to the player
	 */
	public void setItem(MovableItem item) {
		this.item = item;
	}
}
