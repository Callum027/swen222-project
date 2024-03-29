package game.exceptions;

public class AreaIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public AreaIDNotFoundException(byte id) {
		super(false, "area ID not found: " + id);
	}
}