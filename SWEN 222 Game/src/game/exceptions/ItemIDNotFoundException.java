package game.exceptions;

public class ItemIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public ItemIDNotFoundException(int id) {
		super(false, "item ID not found: " + id);
	}
}
