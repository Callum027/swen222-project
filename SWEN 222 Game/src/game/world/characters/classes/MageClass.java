package game.world.characters.classes;

import game.world.characters.Attackable;

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

	private final Attackable thisCharacter;

	public MageClass(Attackable thisCharacter){
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
	 * Masge hits 2 squares around the,
	 */
	@Override
	public int calculateDistance(Attackable target, Attackable attacker) {
		int attackerPosX = attacker.getPosition().getX();
		int attackerPosY = attacker.getPosition().getY();
		int targetPosX = target.getPosition().getX();
		int targetPosY = target.getPosition().getY();
		if ((targetPosX <= attackerPosX+2 && targetPosY == attackerPosY) || (targetPosX <= attackerPosX-2 && targetPosY == attackerPosY)
				|| (targetPosX == attackerPosX && targetPosY <= attackerPosY+2) || (targetPosX == attackerPosX && targetPosY <= attackerPosY-2)
				|| (targetPosX <= attackerPosX+2 && targetPosY <= attackerPosY-2) || (targetPosX <= attackerPosX+2 && targetPosY <= attackerPosY+2)
				|| (targetPosX <= attackerPosX-2 && targetPosY <= attackerPosY-2) || (targetPosX <= attackerPosX-2 && targetPosY <= attackerPosY+2)){
			return 1;
		}
		return -1;
	}
}
