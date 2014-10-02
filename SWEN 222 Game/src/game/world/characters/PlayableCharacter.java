package game.world.characters;

import game.Main;
import game.net.NetIO;
import game.net.Streamable;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;
import game.world.items.Equipment;
import game.world.items.EquippedItems;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Represents the character that the player controls. THis is the context class for our strategy pattern
 * @author Nick Tran
 *
 */
public class PlayableCharacter extends GameCharacter implements Streamable{

	private EquippedItems equipped; //the items that is currently equipped to the player
	private Equipment[] inventory;
	private GameClass gameClass; //either Warrior, Mage or Rogue
	private String playerClass;
	private int count;
	private int cats = 100; //the amount of money/points the player has
	private int uid;

	/**
	 * The constructor: invokes the assignClass method and gives the player a class
	 * @param x the x position of the player
	 * @param y the y position of the player
	 * @param name the name of the player
	 * @param playerClass the class of the player
	 */
	public PlayableCharacter(int x, int y, String name, String playerClass, int uid){
		super(x, y, name);
		assignClass(playerClass); //gives the player a class (behaviour)
		this.playerClass = playerClass;
		this.uid = uid;
	}

	/**
	 * The player is assigned a class based on the String that is given.
	 * @param playerClass the String that was given
	 */
	public void assignClass(String playerClass){
		switch (playerClass){
			case "Warrior":
				setGameClass(new WarriorClass());
				break;
			case "Rogue":
				setGameClass(new RogueClass());
				break;
			case "Mage":
				setGameClass(new RogueClass());
				break;
		}
	}

	/**
	 * Retrieves the class of the player
	 * @return the player's class
	 */
	public GameClass getGameClass() {
		return gameClass;
	}

	/**
	 * Sets the player's class as the given class
	 * @param gameClass the given class
	 */
	public void setGameClass(GameClass gameClass) {
		this.gameClass = gameClass;
	}

	/**
	 * Retrieves the cats which is the score AND the currency of the game
	 * @return the amount of cats the player has
	 */
	public int getCats() {
		return cats;
	}

	/**
	 * Sets the cats as the given number
	 * @param cats the number to set the cats at
	 */
	public void setCats(int cats) {
		this.cats = cats;
	}

	/**
	 * Retrieves the items in the Player's inventory
	 * @return the player's inventory
	 */
	public Equipment[] getInventory() {
		return inventory;
	}

	/**
	 * Sets the inventory
	 * @param inventory the new inventory
	 */
	public void setInventory(Equipment[] inventory) {
		this.inventory = inventory;
	}

	/**
	 * I don't even remember what this was for
	 * @return uhhhhh
	 */
	public int getCount() {
		return count;
	}

	/**
	 * I don't even remember what this was for
	 * @param count uhhhhh
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Retrieves the currently equipped items
	 * @return the Items the player has equipped
	 */
	public EquippedItems getEquipped() {
		return equipped;
	}

	/**
	 * Sets the equipped items of the player. Possibly unused code?
	 * @param equipped the items to equip to the player
	 */
	public void setEquipped(EquippedItems equipped) {
		this.equipped = equipped;
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)uid);

	}

	public static PlayableCharacter read(InputStream is) throws IOException {
		byte id = NetIO.readByte(is);
		return Main.getGameWorld().getPlayer(id);
	}
}
