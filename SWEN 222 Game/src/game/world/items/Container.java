package game.world.items;

import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.world.Position;
import game.world.characters.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A container is an item that contains movable items and cats.
 * Containers can be opened by the player to grab the items and the cats
 * @author Nick Tran
 *
 */
public class Container extends Item{

	private int cats; //the cats contained in the container
	private List<MoveableItem> loot; //the items in the container

	/**
	 * The Constructor:
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the height/size of the container
	 * @param name the name of the container
	 */
	public Container(Position position, int height, int id, String name, int cats) {
		super(position, height, id, name);
		this.cats=cats;
	}

	/**
	 * Get the amount of cats in the container
	 * @return the amount of cats
	 */
	public int getCats() {
		return cats;
	}

	/**
	 * Set the amount of cats in the container
	 * @param cats the new value of cats
	 */
	public void setCats(int cats) {
		this.cats = cats;
	}

	/**
	 * Gets the Array of items that are contained in this container
	 * @return the Array of items
	 */
	public List<MoveableItem> getLoot() {
		return loot;
	}

	/**
	 * Sets the items in the container
	 * @param loot the items to place into the container
	 */
	public void setLoot(List<MoveableItem> loot) {
		this.loot = loot;
	}

	public void interact(Player player) {
		player.setCats(player.getCats() + this.cats);
		this.cats = 0;
	}

	/**
	 * Reads a consumables from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a Consumables, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the consumables with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Container read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof Container)
			return (Container)i;
		else
			throw new InvalidItemException(Container.class, i);
	}
}
