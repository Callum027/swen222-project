package game.world.items;

import java.io.IOException;
import java.io.InputStream;

import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.world.Position;

/**
 * A class to represent the equipment (Weapons and Armour)
 * @author Nick Tran
 *
 */
public class Equipment extends MoveableItem{


	private int attack; //the attack increase that the equipment gives
	private int defence; //the defence increase that the equipment gives
	private int slot;

	/**
	 * The Constructor:
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param height the height/size of the equipment
	 * @param name the name of the equipment
	 * @param attack the attack stat of the equipment
	 * @param defence the defence stat of the equipment
	 * @param worth how much you can buy the weapon for from the merchant
	 */
	public Equipment(Position position, int height, int id, String name, int attack, int defence, int worth, int slot) {
		super(position, height, id, name, worth);
		this.setSlot(slot);
		this.attack = attack;
		this.defence = defence;
	}



	/**
	 * Gets the attack stat of the equipment
	 * @return the attack stat of the equipment
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * Sets the attack stat of the equipment
	 * @param attack the new attack stat of the equipment
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * Gets the defence stat of the equipment
	 * @return the defence stat of the equipment
	 */
	public int getDefence() {
		return defence;
	}

	/**
	 * Sets the defence stat of the equipment
	 * @param defence the new defence stat of the equipment
	 */
	public void setDefence(int defence) {
		this.defence = defence;
	}

	public String toString(){
		return super.toString()+"\nAttack: "+attack+"\nDefence: "+defence;
	}



	public int getSlot() {
		return slot;
	}



	public void setSlot(int slot) {
		this.slot = slot;
	}

	/**
	 * Reads an equipment from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a Equipment, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the equipment with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Equipment read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof Equipment)
			return (Equipment)i;
		else
			throw new InvalidItemException(Equipment.class, i);
	}
}
