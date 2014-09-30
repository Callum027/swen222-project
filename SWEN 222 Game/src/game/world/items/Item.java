package game.world.items;

public abstract class Item {

	private int x;
	private int y;
	private String name;
	private int height;

	public Item(int x, int y, int height, String name){
		this.setHeight(height);
		this.setX(x);
		this.setY(y);
		this.setName(name);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
