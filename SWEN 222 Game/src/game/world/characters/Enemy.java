package game.world.characters;

import game.world.items.Equipment;

/**
 * The enemies within the game. When they die, they drop items and cats
 * @author Nick Tran
 *
 */
public class Enemy extends GameCharacter{

	private int cats; //the amount of cats they drop when they die
	private int health;
	private int attack;
	private Equipment[] drops; //the items that the enemy drops when it dies

	/**
	 * The constructor
	 * @param x the x position of the enemy
	 * @param y the y position of the enemy
	 * @param name the name of the enemy
	 * @param drops the items the enemy drops when they die
	 * @param attack the amount of damage the enemy deals with each strike
	 * @param health the amount of health the enemy has
	 * @param cats the amount of cats the enemy drops when it dies
	 */
	public Enemy(int x, int y, String name, Equipment[] drops, int attack, int health, int cats){
		super(x, y, name);
		this.drops = drops;
		this.attack = attack;
		this.health = health;
		this.cats = cats;
	}
}
