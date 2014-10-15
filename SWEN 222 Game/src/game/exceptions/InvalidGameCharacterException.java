package game.exceptions;


public class InvalidGameCharacterException extends GameException {
	private static final long serialVersionUID = 1L;

	public InvalidGameCharacterException(byte b) {
		super(false, "invalid game character type: " + b);
	}
}
