package game.world.tiles;

import java.awt.Image;
import java.awt.Polygon;

/**
 * A FloorTile represents a floor tile in the game. It is used
 * for rendering the game world.
 *
 * @author David Sheridan
 *
 */
public class FloorTile extends Tile{

	// fields
	public static final int WIDTH = 63;
	public static final int HEIGHT = 32;

	/**
	 * Constructor:
	 * Constructs a FloorTile which corresponds to the
	 * specified image.
	 *
	 * @param image
	 * 			--- image associated with this floor tile
	 */
	public FloorTile(Image image){
		super(image);
	}

	/**
	 * Returns a bounding box in the form of a Polygon for this
	 * Tile. The specified x, y coordinates are in relation to
	 * the top left x, y coordinates where the image for this Tile
	 * will be drawn.
	 *
	 * @param x
	 * 		--- top left x of Tile image
	 * @param y
	 * 		--- top left y of Tile image
	 * @return
	 * 		--- bounding box of Tile
	 */
	public Polygon getBoundingBox(int x, int y){
		int[] xPoints = new int[]{x, x + (WIDTH / 2), x + WIDTH, x + (WIDTH / 2)};
		int[] yPoints = new int[]{y + (HEIGHT / 2), y, y + (HEIGHT / 2), y + HEIGHT};
		return new Polygon(xPoints, yPoints, xPoints.length);
	}
}

