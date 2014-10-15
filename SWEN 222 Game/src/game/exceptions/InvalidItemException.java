package game.exceptions;

import game.exceptions.GameException;
import game.world.items.Item;

public class InvalidItemException extends GameException {
	private static final long serialVersionUID = 1L;

	public InvalidItemException(Class<? extends Item> c, Item i) {
		super(false, "expected item type " + c + " but got " + i);
	}
}