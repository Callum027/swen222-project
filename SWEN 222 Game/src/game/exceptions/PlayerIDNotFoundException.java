package game.exceptions;

public class PlayerIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public PlayerIDNotFoundException(byte id)
	{
		super(false, "player ID not found: " + id);
	}
}
