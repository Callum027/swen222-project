package game.world.items;

import game.world.Position;

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
	public MoveableItem(Position position, int height, String name, int worth) {
		super(position, height, name);
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

}
