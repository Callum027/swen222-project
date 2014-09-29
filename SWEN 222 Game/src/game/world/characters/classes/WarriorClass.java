package game.world.characters.classes;

import game.world.characters.PlayableCharacter;

public class WarriorClass implements GameClass{
	
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
