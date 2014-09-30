package game.world.characters.classes;

/**
 * The magic-user class that deals with magical items. It's a medium range class that deal high magic damage.
 * The class uses staves and wears robes.
 * This is one of the behavior strategies for the GameClass interface
 * @author Nick Tran
 *
 */
public class MageClass implements GameClass{

	//These are the stat bonuses for the Player. They're added on-top of the Player's stats
	private final int strength = 10;
	private final int dexterity = 15;
	private final int intelligence = 25;

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
