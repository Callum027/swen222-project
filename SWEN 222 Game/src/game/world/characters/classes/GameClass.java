package game.world.characters.classes;

/**
 * The strategy interface for the game classes
 * @author Nick Tran
 *
 */
public interface GameClass {

	/**
	 * makes it easier to assign the classes within the context classes
	 */
	public enum playerClass{
		WARRIOR, MAGE, ROGUE
	}

	/**
	 * Retrieves the classes base strength
	 * @return the strength of the class
	 */
	public int getStrength();

	/**
	 * Retrieves the classes base dexterity
	 * @return the dexterity of the class
	 */
	public int getDexterity();

	/**
	 * Retrieves the classes base intelligence
	 * @return the intelligence of the class
	 */
	public int getIntelligence();

	/**
	 * handles the logic when a character attacks a target
	 */
	public void attack();

	/**
	 * calculates the damage the character deals to another character
	 */
	public void calculateDamage();
}
