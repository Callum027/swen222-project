package game.exceptions;


public class InvalidCharacterClassException extends GameException {
	private static final long serialVersionUID = 1L;

	public InvalidCharacterClassException(byte b) {
		super(false, "invalid character class ID: " + b);
	}
}
