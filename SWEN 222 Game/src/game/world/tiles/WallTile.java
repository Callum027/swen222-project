package game.world.tiles;

import game.world.BoundingBox;
import game.world.Position;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * A WallTile represents a wall tile in the game. It is used
 * for rendering the game world.
 *
 * @author David Sheridan
 *
 */
public class WallTile extends Tile{

	// fields
	public static final int WIDTH = 32;
	public static final int HEIGHT = 45;

	/**
	 * Constructor:
	 * Constructs a floor tile which corresponds to the
	 * specified image.
	 *
	 * @param image
	 * 			--- image associated with this floor tile
	 */
	public WallTile(String filename){
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
	public BoundingBox getBoundingBox(int x, int y, Point p){
		return null;
	}

	@Override
	public BoundingBox getBoundingBox(int x, int y, Position p) {
		// TODO Auto-generated method stub
		return null;
	}
}
