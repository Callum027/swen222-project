package game.exceptions;

public class PlayerIDNotFoundException extends GameException {

	public PlayerIDNotFoundException(byte id)
	{
		super("player ID not found: " + id);
	}
}
