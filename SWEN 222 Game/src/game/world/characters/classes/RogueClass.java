package game.world.characters.classes;

/**
 * The range class that deals with sneaky tactics. It's a long range class that deal high projectile damage.
 * The class uses Bows and wears leather armour.
 * This is one of the behavior strategies for the GameClass interface
 * @author trannich
 *
 */
public class RogueClass implements GameClass{

	//These are the stat bonuses for the Player. They're added on-top of the Player's stats
	private final int strength = 15;
	private final int dexterity = 25;
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
	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}
	@Override
	public void calculateDamage() {
		// TODO Auto-generated method stub

	}
}
