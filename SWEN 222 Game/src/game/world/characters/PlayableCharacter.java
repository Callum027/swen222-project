package game.world.characters;

import game.world.items.Equipment;

import java.util.ArrayList;

public class PlayableCharacter extends GameCharacter{
	
	//private GameClass class;
	private int cats;
	private ArrayList<Equipment> inventory;
	
	public PlayableCharacter(int x, int y, String name){
		super(x, y, name);
	}

	public int getCats() {
		return cats;
	}

	public void setCats(int cats) {
		this.cats = cats;
	}

	public ArrayList<Equipment> getInventory() {
		return inventory;
	}

	public void setInventory(ArrayList<Equipment> inventory) {
		this.inventory = inventory;
	}
}
