package game.world.items;

import java.io.IOException;
import java.io.InputStream;

import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.world.Position;
import game.world.characters.Player;


/**
 * The class for the items that are consumed by the player
 * @author Nick
 */
public class Consumables extends MoveableItem{

	private final float buffPercentage; //the amount of points to increase by

	private static final int MAX_HEALTH = 1000; //the maximum amount of health a player can have 
	
	/**
	 * The Constructor
	 * @param buffPercentage the percentage the player's health gets increased by
	 */
	public Consumables (Position position, int height, int id, String name, int worth, float buffPercentage){
		super(position, height, id, name, worth);
		this.buffPercentage = buffPercentage;
	}
	
	/**
	 * Increase the player's health
	 * @param player this is the player that's consuming this item
	 */
	public void buff(Player player){
		int buffAmount = (int)(player.getHealth()*(buffPercentage/100.0f)); //the amount we're increasing the player's health by
		if (player.getHealth()+buffAmount > MAX_HEALTH){ //the increase must NEVER increase above the player's max health
			player.setHealth(MAX_HEALTH);
		}
		player.setHealth(player.getHealth()+buffAmount);
	}
	
	/**
	 * Reads a consumables from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a Consumables, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the consumables with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Consumables read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof Consumables)
			return (Consumables)i;
		else
			throw new InvalidItemException(Consumables.class, i);
	}
}
