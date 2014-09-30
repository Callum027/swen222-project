package game.world.items;

public class Container extends Item{

	private int cats;
	private Equipment[] loot;

	public Container(int x, int y, int height, String name) {
		super(x, y, height, name);
	}

}
