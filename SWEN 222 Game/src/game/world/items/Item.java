package game.world.items;

public abstract class Item {

	private int x;
	private int y;
	private int height;

	public Item(int x, int y, int height){
		this.height = height;
		this.x = x;
		this.y = y;
	}
}
