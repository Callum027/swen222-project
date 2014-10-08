package game.world.characters.classes;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The strategy interface for the game classes
 * @author Nick Tran
 *
 */
public interface GameClass {

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

	public void attack();

	public void calculateDamage();

	public void write(OutputStream os) throws IOException;
}
