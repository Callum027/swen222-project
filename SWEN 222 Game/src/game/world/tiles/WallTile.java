package game.world.tiles;

import java.awt.Image;

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
	public WallTile(Image image){
		super(image);
	}
}
