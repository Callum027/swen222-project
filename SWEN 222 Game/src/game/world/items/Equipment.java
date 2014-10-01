package game.world.items;

public class Equipment extends MoveableItem{

	private int worth;
	private int attack;
	private int defence;

	public Equipment(int x, int y, int height, String name, int attack, int defence, int worth) {
		super(x, y, height, name);
		this.attack = attack;
		this.defence = defence;
		this.worth = worth;
	}

	public int getWorth() {
		return worth;
	}

	public void setWorth(int worth) {
		this.worth = worth;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

}
