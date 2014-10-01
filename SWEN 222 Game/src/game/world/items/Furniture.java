package game.world.items;

public class Furniture extends Item{

	private MoveableItem item;

	public Furniture(int x, int y, int height, String name, MoveableItem item) {
		super(x, y, height, name);
		this.item = item;
	}

}
