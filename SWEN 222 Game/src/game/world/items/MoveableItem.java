package game.world.items;

import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.world.Position;
import game.world.characters.Player;

import java.io.IOException;
import java.io.InputStream;

/**
 * Used to differentiate between items that you can move and pick-up from items
 * that you can't
 *
 * @author Nick Tran
 *
 */
public class MoveableItem extends Item {

	private int worth; // how much you can buy the weapon for

	/**
	 * The Constructor
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param height
	 *            the height (size) of the item
	 * @param name
	 *            the name of the item
	 */
	public MoveableItem(Position position, int height, int ID, String name, int worth) {
		super(position, height, ID, name);
		this.worth = worth;
	}

	public String toString() {
		return super.getName() + "\nWorth: " + worth;
	}

	/**
	 * Gets the value of the item
	 *
	 * @return the value of the item
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * Sets the value of the item
	 *
	 * @param worth
	 *            the new value of the item
	 */
	public void setWorth(int worth) {
		this.worth = worth;
	}

	public void interact(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Reads a moveable item from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a MoveableItem, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the moveable item with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static MoveableItem read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof MoveableItem)
			return (MoveableItem)i;
		else
			throw new InvalidItemException(MoveableItem.class, i);
	}
}
