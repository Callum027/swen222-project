package game.world.items;

public class Equipment extends MoveableItem{

	private int worth;

	public Equipment(int x, int y, int height) {
		super(x, y, height);
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}

}
