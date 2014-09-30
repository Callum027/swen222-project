package game.world.items;

public class Equipment extends MoveableItem{

	private int worth;
	private int attack;
	private int defence;

	public Equipment(int x, int y, int height, String name) {
		super(x, y, height, name);
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}

}
