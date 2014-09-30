package game.world.characters.classes;

/**
 * The strategy interface for the game classes
 * @author Nick Tran
 *
 */
public interface GameClass {

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

}
