package game.world.tiles;

import game.Main;
import game.world.BoundingBox;
import game.world.Drawable;
import game.world.Position;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;

/**
 * A Tile represents an abstract tile in the game. Tiles are
 * used to render the game world.
 *
 * @author David Sheridan
 *
 */
public abstract class Tile implements Drawable{

	// fields
	private final String[] directions = new String[]{"North", "East", "South", "West"};
	private Image[] image;

	/**
	 * Constructor:
	 * Constructs a tile which corresponds to the
	 * specified image.
	 *
	 * @param image
	 * 			--- image associated with this tile
	 */
	public Tile(String filename){
		this.image = new Image[directions.length];
		for(int i = 0; i < directions.length; i++){
			image[i] = Main.getImage(filename+"_"+directions[i]+".png");
			//image[i] = Main.getImage(filename+".png");
		}

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
	public abstract BoundingBox getBoundingBox(int x, int y, Position p);

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
	public void draw(Graphics g, int x, int y, int direction){
		g.drawImage(image[direction], x, y, null);
	}
}
