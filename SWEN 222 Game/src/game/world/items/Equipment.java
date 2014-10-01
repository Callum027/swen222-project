package game.world.items;

import java.awt.Image;

/**
 * A class to represent the equipment (Weapons and Armour)
 * @author Nick Tran
 *
 */
public class Equipment extends MovableItem{

	private int worth; //how much you can buy the weapon for
	private int attack; //the attack increase that the equipment gives
	private int defence; //the defence increase that the equipment gives

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
	public Equipment(int x, int y, int height, String name, Image image, int attack, int defence, int worth) {
		super(x, y, height, name, image);
		this.attack = attack;
		this.defence = defence;
		this.worth = worth;
	}

	/**
	 * Gets the value of the item
	 * @return the value of the item
	 */
	public int getWorth() {
		return worth;
	}

	/**
	 * Sets the value of the item
	 * @param worth the new value of the item
	 */
	public void setWorth(int worth) {
		this.worth = worth;
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

}
