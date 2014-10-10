package game.world.characters;

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
	public void attack();

	/**
	 * calculates the damage the character deals to another character
	 */
	public void calculateDamage();

}
