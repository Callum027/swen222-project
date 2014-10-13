package game.exceptions;

public class ItemIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public ItemIDNotFoundException(byte id) {
		super("item ID not found: " + id);
	}
}
