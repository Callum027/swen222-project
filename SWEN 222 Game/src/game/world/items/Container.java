package game.world.items;

public class Container extends Item{

	private int cats;
	private Equipment[] loot;

	public Container(int x, int y, int height, String name) {
		super(x, y, height, name);
	}

	public int getCats() {
		return cats;
	}

	public void setCats(int cats) {
		this.cats = cats;
	}

	public Equipment[] getLoot() {
		return loot;
	}

	public void setLoot(Equipment[] loot) {
		this.loot = loot;
	}

}
