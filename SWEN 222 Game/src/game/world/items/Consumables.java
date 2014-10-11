package game.world.items;

import game.world.characters.Player;


/**
 * The class for the items that are consumed by the player
 * @author Nick
 */
public class Consumables {

	private final float buffPercentage; //the amount of points to increase by

	private static final int MAX_HEALTH = 1000; //the maximum amount of health a player can have 
	
	/**
	 * The Constructor
	 * @param buffPercentage the percentage the player's health gets increased by
	 */
	public Consumables (float buffPercentage){
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
}
