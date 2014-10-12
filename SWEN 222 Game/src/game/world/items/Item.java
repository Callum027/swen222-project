package game.world.items;

import game.Main;
import game.world.BoundingBox;
import game.world.Drawable;
import game.world.Interactable;
import game.world.Position;
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
public abstract class Item implements Drawable, Interactable{

	private Position position;
	private String name;
	private int height; // how much room the item takes up on the area
	private Image[] images;
	private String description;

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
	public Item(Position position, int height, String name) {
		this.setPosition(position);
		this.setHeight(height);
		this.setName(name);
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
	 * Sets the height of the item
	 *
	 * @param height
	 *            the new height value
	 */
	public void setHeight(int height) {
		this.height = height;
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
	 * sets the name of the item
	 *
	 * @param name
	 *            the name to give the item
	 */
	public void setName(String name) {
		this.name = name;
	}

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
		int itemY = y - (height * FloorTile.HEIGHT);
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
}
