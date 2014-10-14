package game.world.items;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import game.Main;
import game.exceptions.GameException;
import game.exceptions.ItemIDNotFoundException;
import game.net.NetIO;
import game.world.Area;
import game.world.Position;
import game.world.characters.Player;

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
	 * reads a furniture from the inputstream
	 * @param is the inputstream
	 * @return the furniture with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Furniture read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		Furniture furniture = null;

		/*
		 * iterates over all the areas and returns the furniture item with the given id
		 */
		for (Entry<Integer,Area> entry : Main.getGameWorld().getAreas().entrySet()){
			if (furniture != null){
				return furniture;
			}
			furniture = (Furniture) entry.getValue().getItem(id);
		}

		if (furniture == null)
			throw new ItemIDNotFoundException(id);

		return furniture;
	}
}
