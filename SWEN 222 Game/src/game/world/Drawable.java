package game.world;

import java.awt.Graphics;

/**
 * Interface that ensures that objects which are appearing on the
 * screen has a draw method to draw an instance of that object. This
 * also requires that they have a bounding box so that the user can
 * interact with the objects.
 *
 * @author David Sheridan
 *
 */
public interface Drawable {

	/**
	 * Draws an instance of the object to the specified
	 * graphics pane.
	 *
	 * @param g
	 * 			--- the graphics pane
	 * @param x
	 * 			--- top left x coordinate to draw object
	 * @param y
	 * 			--- top left y coordinate to draw object
	 * @param direction
	 * 			--- the current direction of the game view
	 */
	public void draw(Graphics g, int x, int y, int direction);

	/**
	 * Returns a bounding box that corresponds to the position
	 * of this object on the user interface.
	 *
	 * @param x
	 * 			--- top left x coordinate
	 * @param y
	 * 			--- top left y coordinate
	 * @param p
	 * 			--- Position of the object in its current Area
	 * @return
	 * 			--- BoundingBox of this object
	 */
	public BoundingBox getBoundingBox(int x, int y, Position p);
}
