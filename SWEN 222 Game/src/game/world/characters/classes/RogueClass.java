package game.world.characters.classes;

import game.world.characters.Attackable;

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

	private final Attackable thisCharacter;

	public RogueClass(Attackable thisCharacter){
		this.thisCharacter = thisCharacter;
	}

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
	public void attack(Attackable target) {
		if (calculateDistance(target, thisCharacter) != 1){
			throw new IllegalArgumentException("Target is not in range");
		}
		target.setHealth(target.getHealth() - thisCharacter.calculateDamage());
		if (target.getHealth()<=0){
			target.setIsDead(true);
		}
	}

	@Override
	public int calculateDamage() {
		return thisCharacter.getAttack();
	}

	public Attackable getThisCharacter() {
		return thisCharacter;
	}

	/**
	 * Rogues hit 4 squares around the,
	 */
	@Override
	public int calculateDistance(Attackable target, Attackable attacker) {
		int attackerPosX = attacker.getPosition().getX();
		int attackerPosY = attacker.getPosition().getY();
		int targetPosX = target.getPosition().getX();
		int targetPosY = target.getPosition().getY();
		if ((targetPosX <= attackerPosX+4 && targetPosY == attackerPosY) || (targetPosX <= attackerPosX-4 && targetPosY == attackerPosY)
				|| (targetPosX == attackerPosX && targetPosY <= attackerPosY+4) || (targetPosX == attackerPosX && targetPosY <= attackerPosY-4)
				|| (targetPosX <= attackerPosX+4 && targetPosY <= attackerPosY-4) || (targetPosX <= attackerPosX+4 && targetPosY <= attackerPosY+4)
				|| (targetPosX <= attackerPosX-4 && targetPosY <= attackerPosY-4) || (targetPosX <= attackerPosX-4 && targetPosY <= attackerPosY+4)){
			return 1;
		}
		return -1;
	}
}
