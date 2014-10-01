package game.world.items;

import java.awt.Graphics;
import java.awt.Image;

/**
 * A class for all the items including furniture, chests (containers) and moveable items (anything you can pick up)
 * @author Nick Tran
 *
 */
public abstract class Item {

	private int x;
	private int y;
	private String name;
	private int height; //how much room the item takes up on the area
	private Image image;

	/**
	 * THe constructor
	 * @param x the x position
	 * @param y the y position
	 * @param height the size of the item
	 * @param name the name of the item
	 */
	public Item(int x, int y, int height, String name, Image image){
		this.setHeight(height);
		this.setX(x);
		this.setY(y);
		this.setName(name);
		this.image = image;
	}

	/**
	 * Gets the x position of the item
	 * @return the x position
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x poisition of the item
	 * @param x the value to set the current x-value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y position of the item
	 * @return the y position
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y position of the item
	 * @param y the value to set the current y-value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Retrieves the height (size) of the item
	 * @return the height of the item
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the item
	 * @param height the new height value
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Retrieves the name of the item
	 * @return the name of the item
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the item
	 * @param name the name to give the item
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Draw this item on the specified graphics object
	 * 
	 * @param g - graphics object
	 * @param x - x position to draw to
	 * @param y - y position to draw to
	 */
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
}
