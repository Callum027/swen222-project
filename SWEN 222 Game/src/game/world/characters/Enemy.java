package game.world.characters;

import game.world.Position;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.MageClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;

/**
 * The enemies within the game. When they die, they drop items and cats
 * @author Nick Tran
 *
 */
public class Enemy extends GameCharacter implements Attackable{

	private int health;
	private int attack;
	private int defence;
	private GameClass gameClass; //either Warrior, Mage or Rogue

	public Enemy(Position position, String name, int uid, GameClass.playerClass playerClass){
		super(position, name, uid);
		assignClass(playerClass); //gives the player a class (behaviour)
	}

	public void assignClass(GameClass.playerClass playerClass){
		switch (playerClass){
			case WARRIOR:
				gameClass = new WarriorClass();
				break;
			case ROGUE:
				gameClass = new RogueClass();
				break;
			case MAGE:
				gameClass = new MageClass();
				break;
		}
	}

	@Override
	public void attack() {
		gameClass.attack();
	}

	@Override
	public void calculateDamage() {
		gameClass.calculateDamage();
	}
}
