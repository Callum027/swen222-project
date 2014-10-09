import game.exceptions.GameException;


public class InvalidGameEventException extends GameException {
	public InvalidGameEventException(byte b)
	{
		super("invalid game event type: " + b);
	}
}
