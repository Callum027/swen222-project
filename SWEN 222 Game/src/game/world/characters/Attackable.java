package game.world.characters;

import game.world.Position;

/**
 * an interface that defines a character as an attackable object
 * defines a character that can attack and can be attacked
 * @author Nick Tran
 *
 */
public interface Attackable {

	/**
	 * handles the logic when a character attacks a target
	 */
	public void attack(Attackable target);

	public int getHealth();

	/**
	 * calculates the damage the character deals to another character
	 */
	public int calculateDamage();

	public Position getPosition();

	public int calculateDistance(Attackable target, Attackable attacker);

	public int getAttack();

	public void setHealth(int health);

	public boolean getIsDead();

	public void setIsDead(boolean deadOrAlive);

}
