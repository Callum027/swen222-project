package game.world.characters.classes;

import game.world.characters.PlayableCharacter;

/**
 * The melee class that deals with brute force and strength. It's a close-range class that deal high melee damage.
 * The class uses either 1H Swords or 2H Swords and wears Heavy Plate armour.
 * This is one of the behavior strategies for the GameClass interface
 * @author trannich
 *
 */
public class WarriorClass implements GameClass{

	//These are the stat bonuses for the Player. They're added on-top of the Player's stats
	private final int strength = 25;
	private final int dexterity = 15;
	private final int intelligence = 10;

	public int getStrength() {
		return strength;
	}
	public int getDexterity() {
		return dexterity;
	}
	public int getIntelligence() {
		return intelligence;
	}


}
