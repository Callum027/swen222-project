package game.world.tiles;

import java.awt.Image;

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
}

