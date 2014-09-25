package game.world.tiles;

import java.awt.Graphics;
import java.awt.Image;

/**
 * A Tile represents an abstract tile in the game. Tiles are
 * used to render the game world.
 *
 * @author David Sheridan
 *
 */
public abstract class Tile {

	// fields
	private Image image;

	/**
	 * Constructor:
	 * Constructs a tile which corresponds to the
	 * specified image.
	 *
	 * @param image
	 * 			--- image associated with this tile
	 */
	public Tile(Image image){
		this.image = image;
	}

	/**
	 * Draws this tile to the specified graphics pane,
	 * at the x and y position given. The x and y position
	 * correspond to the top left point of the image.
	 *
	 * @param g
	 * 			--- the graphics object
	 * @param x
	 * 			--- x position to draw tile
	 * @param y
	 * 			--- y position to draw tile
	 */
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
}
