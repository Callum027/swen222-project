package game.world.characters.npc;

import java.util.ArrayList;
import game.world.characters.NonPlayableCharacter;
import game.world.characters.PlayableCharacter;
import game.world.items.Equipment;
import game.world.items.Item;

/**
 * A class for our Merchant NPC
 * Sells equipment to our player
 * @author Nick Tran 
 *
 */
public class Merchant extends NonPlayableCharacter{
	
	private ArrayList<Equipment> wares; //the items the merchant has for sale
	private int cats; //the currency system in our game
	
	/**
	 * The Constructor
	 * Calls the super constructor and initializes the cats to 0
	 * @param x the x position of the Merchant
	 * @param y the y position of the Merchant
	 * @param name the name of this character
	 */
	public Merchant(int x, int y, String name){
		super(x, y, name);
		this.setCats(0);
	}
	
	/**
	 * Sells an item to the player
	 * @param item the item to sell to the player
	 * @param player the player that's buying the item
	 */
	public void sellWares(Equipment item, PlayableCharacter player){
		player.setCats(player.getCats()-item.getWorth()); //deducts money from the player
		player.getInventory().add(item);
		getWares().remove(item);
		this.setCats(this.cats+item.getWorth()); //increase the Merchant's money
	}

	/**
	 * Getter method for cats, which is the Merchant's money
	 * @return the cats (money)
	 */
	public int getCats() {
		return cats;
	}

	/**
	 * Setter method for cats, which is the Merchant's money
	 * @param cats the amount to set the current cats as
	 */
	public void setCats(int cats) {
		this.cats = cats;
	}

	/**
	 * Retrieves the list of items the merchant has for sale
	 * @return the ArrayList of Equipment the Merchant has for sale
	 */
	private ArrayList<Equipment> getWares() {
		return wares;
	}

	/**
	 * Sets the ArrayList of items that is available for the Merchant to sell
	 * @param wares the ArrayList of Equipment objects to set as the available items that the Merchant has for sale
	 */
	private void setWares(ArrayList<Equipment> wares) {
		this.wares = wares;
	}
}
