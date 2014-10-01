package game.world.items;

/**
 * Used to differentiate between items that you can move and pick-up from items that you can't
 * @author Nick Tran
 *
 */
public class MoveableItem extends Item{

	/**
	 * The Constructor
	 * @param x the x coordiante
	 * @param y the y coordiante
	 * @param height the height (size) of the item
	 * @param name the anme of the item
	 */
	public MoveableItem(int x, int y, int height, String name) {
		super(x, y, height, name);
	}

}
