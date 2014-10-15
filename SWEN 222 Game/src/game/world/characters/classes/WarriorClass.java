package game.world.characters.classes;

import game.exceptions.TargetOutOfRangeException;
import game.world.characters.Attackable;
import game.world.characters.Player;

/**
 * The melee class that deals with brute force and strength. It's a close-range class that deal high melee damage.
 * The class uses either 1H Swords or 2H Swords and wears Heavy Plate armour.
 * This is one of the behavior strategies for the GameClass interface
 * @author trannich
 *
 */
public class WarriorClass implements GameClass{

	//These are the stat bonuses for the Player and Enemy. They are added on-top of the Player's/Enemy's stats
	private final int strength = 25;
	private final int dexterity = 15;
	private final int intelligence = 10;

	private final Attackable thisCharacter;

	public WarriorClass(Attackable thisCharacter){
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

	@Override
	public int calculateDistance(Attackable target, Attackable attacker) {
		int attackerPosX = attacker.getPosition().getX();
		int attackerPosY = attacker.getPosition().getY();
		int targetPosX = target.getPosition().getX();
		int targetPosY = target.getPosition().getY();
		if ((targetPosX == attackerPosX+1 && targetPosY == attackerPosY) || (targetPosX == attackerPosX-1 && targetPosY == attackerPosY)
				|| (targetPosX == attackerPosX && targetPosY == attackerPosY+1) || (targetPosX == attackerPosX && targetPosY == attackerPosY-1)
				|| (targetPosX == attackerPosX+1 && targetPosY == attackerPosY-1) || (targetPosX == attackerPosX+1 && targetPosY == attackerPosY+1)
				|| (targetPosX == attackerPosX-1 && targetPosY == attackerPosY-1) || (targetPosX == attackerPosX-1 && targetPosY == attackerPosY+1)){
			return 1;
		}
		return -1;
	}
}
