package game.world.items;

import game.Main;
import game.exceptions.GameException;
import game.exceptions.ItemIDNotFoundException;
import game.net.NetIO;
import game.world.Area;
import game.world.Position;
import game.world.characters.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;

/**
 * A container is an item that contains movable items and cats.
 * Containers can be opened by the player to grab the items and the cats
 * @author Nick Tran
 *
 */
public class Container extends Item{

	private int cats; //the cats contained in the container
	private List<MoveableItem> loot; //the items in the container

	/**
	 * The Constructor:
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the height/size of the container
	 * @param name the name of the container
	 */
	public Container(Position position, int height, int id, String name, int cats) {
		super(position, height, id, name);
		this.cats=cats;
	}

	/**
	 * Get the amount of cats in the container
	 * @return the amount of cats
	 */
	public int getCats() {
		return cats;
	}

	/**
	 * Set the amount of cats in the container
	 * @param cats the new value of cats
	 */
	public void setCats(int cats) {
		this.cats = cats;
	}

	/**
	 * Gets the Array of items that are contained in this container
	 * @return the Array of items
	 */
	public List<MoveableItem> getLoot() {
		return loot;
	}

	/**
	 * Sets the items in the container
	 * @param loot the items to place into the container
	 */
	public void setLoot(List<MoveableItem> loot) {
		this.loot = loot;
	}

	@Override
	public void interact(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)super.getID());
	}
	
	/**
	 * reads a player from the inputstream
	 * @param is the inputstream
	 * @return the player with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static MoveableItem read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		MoveableItem moveableItem = null;

		/*
		 * iterates over all the areas and returns the moveable item with the given id
		 */
		for (Entry<Integer,Area> entry : Main.getGameWorld().getAreas().entrySet()){
			if (moveableItem != null){
				return moveableItem;
			}
			moveableItem = (MoveableItem) entry.getValue().getItem(id);
		}

		if (moveableItem == null)
			throw new ItemIDNotFoundException(id);

		return moveableItem;
	}
}
