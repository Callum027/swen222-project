package game.world.tiles;

import game.world.BoundingBox;
import game.world.Position;

import java.awt.Point;
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
	public FloorTile(String filename){
		super(filename);
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
	public BoundingBox getBoundingBox(int x, int y, Position p){
		int[] xPoints = new int[]{x, x + (WIDTH / 2), x + WIDTH, x + (WIDTH / 2)};
		int[] yPoints = new int[]{y + (HEIGHT / 2), y, y + (HEIGHT / 2), y + HEIGHT};
		return new BoundingBox(xPoints, yPoints, xPoints.length, p);
	}
}

