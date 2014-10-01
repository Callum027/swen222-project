package game.world.items;

import java.awt.Image;

/**
 * Used to differentiate between items that you can move and pick-up from items that you can't
 * @author Nick Tran
 *
 */
public class MovableItem extends Item{

	/**
	 * The Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the height (size) of the item
	 * @param name the anme of the item
	 */
	public MovableItem(int x, int y, int height, String name, Image image) {
		super(x, y, height, name, image);
	}

}
