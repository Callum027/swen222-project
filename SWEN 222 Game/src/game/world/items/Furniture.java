package game.world.items;

import game.world.characters.PlayableCharacter;

public class Furniture extends Item{

	private MoveableItem item;

	public Furniture(int x, int y, int height, String name, MoveableItem item) {
		super(x, y, height, name);
		this.setItem(item);
	}

	public String interact(PlayableCharacter player){
		return "This is a piece of furniture";
	}

	public MoveableItem getItem() {
		return item;
	}

	public void setItem(MoveableItem item) {
		this.item = item;
	}
}
