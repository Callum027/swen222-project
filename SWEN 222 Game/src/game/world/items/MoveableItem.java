package game.world.items;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import game.Main;
import game.exceptions.GameException;
import game.exceptions.ItemIDNotFoundException;
import game.exceptions.PlayerIDNotFoundException;
import game.net.NetIO;
import game.world.Area;
import game.world.Position;
import game.world.characters.Enemy;
import game.world.characters.Player;

/**
 * Used to differentiate between items that you can move and pick-up from items
 * that you can't
 *
 * @author Nick Tran
 *
 */
public class MoveableItem extends Item {

	private int worth; // how much you can buy the weapon for

	/**
	 * The Constructor
	 *
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param height
	 *            the height (size) of the item
	 * @param name
	 *            the name of the item
	 */
	public MoveableItem(Position position, int height, int ID, String name, int worth) {
		super(position, height, ID, name);
		this.worth = worth;
	}

	public String toString() {
		return super.getName() + "\nWorth: " + worth;
	}

	/**
	 * Gets the value of the item
	 *
	 * @return the value of the item
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * Sets the value of the item
	 *
	 * @param worth
	 *            the new value of the item
	 */
	public void setWorth(int worth) {
		this.worth = worth;
	}

	public void interact(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)super.getID());
	}
	
	/**
	 * reads a moveable item from the inputstream
	 * @param is the inputstream
	 * @return the moveable item with the given id that is received form the inputstream
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

		/*
		 * iterates over all the players and returns the moveable item with the given id
		 */
		for (Entry<Integer,Player> entry : Main.getGameWorld().getPlayers().entrySet()){
			if (moveableItem != null){
				return moveableItem;
			}
			moveableItem = (MoveableItem) entry.getValue().getItem(id);
		}
		
		/*
		 * iterates over all the enemies and returns the moveable item with the given id
		 */
		for (Entry<Integer,Area> entry : Main.getGameWorld().getAreas().entrySet()){
			for (Entry<Integer,Enemy> entry2 : entry.getValue().getEnemies().entrySet()){
				if (moveableItem != null){
					return moveableItem;
				}
				moveableItem = (MoveableItem) entry2.getValue().getItem(id);
			}
		}
		
		if (moveableItem == null)
			throw new ItemIDNotFoundException(id);

		return moveableItem;
	}
}
