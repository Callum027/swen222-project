package game.world.items;

import game.Main;
import game.net.Streamable;
import game.world.BoundingBox;
import game.world.Drawable;
import game.world.Position;
import game.world.characters.Player;
import game.world.tiles.FloorTile;

import java.awt.Graphics;
import java.awt.Image;

/**
 * A class for all the items including furniture, chests (containers) and
 * moveable items (anything you can pick up)
 *
 * @author Nick Tran
 *
 */
public abstract class Item implements Drawable, Streamable{

	private Position position;
	private final String name;
	private final int height; // how much room the item takes up on the area
	private Image[] images;
	private String description;
	private final int ID;

	/**
	 * The constructor
	 *
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param height
	 *            the size of the item
	 * @param name
	 *            the name of the item
	 */
	public Item(Position position, int height, int ID, String name) {
		this.position = position;
		this.height = height;
		this.name = name;
		this.ID = ID;
		images = new Image[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = Main.getImage(name + ".png");
		}
	}

	/**
	 * Retrieves the height (size) of the item
	 *
	 * @return the height of the item
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Retrieves the name of the item
	 *
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * The method called when the player interacts with this item
	 * @param player the player interacting
	 */
	public abstract void interact(Player player);

	/**
	 * Draw this item on the specified graphics object
	 *
	 * @param g
	 *            - graphics object
	 * @param x
	 *            - x position to draw to
	 * @param y
	 *            - y position to draw to
	 */
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[0], x, y, null);
	}

	public BoundingBox getBoundingBox(int x, int y, Position p) {
		int itemY = y;//y - (height * FloorTile.HEIGHT);
		int dy = FloorTile.HEIGHT / 2;
		int[] xPoints = new int[] { x, x + (FloorTile.WIDTH / 2),
				x + FloorTile.WIDTH, x + FloorTile.WIDTH,
				x + (FloorTile.WIDTH / 2), x };
		int[] yPoints = new int[] { itemY + dy, itemY, itemY + dy,
				itemY + ((height + 1) * FloorTile.HEIGHT) - dy,
				itemY + ((height + 1) * FloorTile.HEIGHT),
				itemY + ((height + 1) * FloorTile.HEIGHT) - dy };
		return new BoundingBox(xPoints, yPoints, xPoints.length, p);
	}

	/**
	 * grabs the position of this item
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * changes the position of the item
	 * @param position the position we're changing to
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * gets the description of the item
	 * @return the description describing the item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * changes the description of the item
	 * @param description the description we're changing to
	 */
	public void setDescription(String description){
		this.description = description;
	}

	public int getID() {
		return ID;
	}
}
