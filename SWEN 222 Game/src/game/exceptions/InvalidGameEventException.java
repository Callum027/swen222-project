package game.exceptions;
import game.exceptions.GameException;
import game.world.GameEvent;


public class InvalidGameEventException extends GameException {
	private static final long serialVersionUID = 1L;

	public InvalidGameEventException(byte b) {
		super(false, "invalid game event type: " + b);
	}
	
	public InvalidGameEventException(GameEvent.Type t) {
		super(false, "invalid game event type: " + t);
	}
}
