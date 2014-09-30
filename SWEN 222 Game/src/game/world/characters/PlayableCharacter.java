package game.world.characters;

import game.world.characters.classes.GameClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;
import game.world.items.Equipment;

import java.util.ArrayList;

public class PlayableCharacter extends GameCharacter{

	private Equipment[] inventory;
	private GameClass gameClass;
	private int count;
	private int cats;

	public PlayableCharacter(int x, int y, String name, String playerClass){
		super(x, y, name);
		assignClass(playerClass);

	}

	public void assignClass(String playerClass){
		switch (playerClass){
			case "Warrior":
				setGameClass(new WarriorClass());
				break;
			case "Rogue":
				setGameClass(new RogueClass());
				break;
			case "Mage":
				setGameClass(new RogueClass());
				break;
		}
	}

	public GameClass getGameClass() {
		return gameClass;
	}

	public void setGameClass(GameClass gameClass) {
		this.gameClass = gameClass;
	}

	public int getCats() {
		return cats;
	}

	public void setCats(int cats) {
		this.cats = cats;
	}

	public Equipment[] getInventory() {
		return inventory;
	}

	public void setInventory(Equipment[] inventory) {
		this.inventory = inventory;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
