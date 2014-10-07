package game.world.items;

import java.awt.Image;

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
	public Equipment(int x, int y, int height, String name, Image image, int attack, int defence, int worth, int slot) {
		super(x, y, height, name, image, worth);
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

}
