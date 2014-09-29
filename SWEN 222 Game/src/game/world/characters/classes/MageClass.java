package game.world.characters.classes;

public class MageClass implements GameClass{
	
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
