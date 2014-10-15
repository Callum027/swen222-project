package game.world.items;

import game.Main;
import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.net.NetIO;
import game.world.Position;
import game.world.characters.Player;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A furniture is an immovable item that's also not a container
 * @author Nick Tran
 *
 */
public class Furniture extends Item{
	
	private final String[] directions = new String[]{"North", "East", "South", "West"};
	private MoveableItem item; //you can get items by interacting with certain pieces of furniture
	private Image[] images;

	/**
	 * The Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the size of the furniture
	 * @param name the name of the furniture
	 * @param item the item you can get from interacting with the furniture
	 */
	public Furniture(Position position, int height, int id,  String name, MoveableItem item) {
		super(position, height, id, name);
		this.setItem(item);
		this.images = new Image[directions.length];
		for(int i = 0; i < directions.length; i++){
			images[i] = Main.getImage(name+"_"+directions[i]+".png");
		}
	}

	/**
	 * The method invoked when the player clicks on this piece of furniture
	 * @param player the player interacting with this piece of furniture
	 * @return the description of the furniture
	 */
	public void interact(Player player){

	}

	/**
	 * Retrieves the item that the furniture contains
	 * @return the item in the furniture
	 */
	public MoveableItem getItem() {
		return item;
	}

	/**
	 * Sets a new item to be given to the player by the furniture
	 * @param item the new item that's given to the player
	 */
	public void setItem(MoveableItem item) {
		this.item = item;
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)super.getID());
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
	@Override
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[direction], x, y, null);
	}
	
	/**
	 * Reads some furniture from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a Furniture, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the furniture with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Furniture read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof Furniture)
			return (Furniture)i;
		else
			throw new InvalidItemException(Furniture.class, i);
	}
}
