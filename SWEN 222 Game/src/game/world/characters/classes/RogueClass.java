package game.world.characters.classes;

public class RogueClass implements GameClass{
	
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
}
