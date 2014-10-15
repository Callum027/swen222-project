package game.exceptions;
import game.world.characters.GameCharacter;


public class UnsupportedGameCharacterException extends GameException {
	private static final long serialVersionUID = 1L;

	public UnsupportedGameCharacterException(GameCharacter.Type t) {
		super(false, "unsupported game character type: " + t);
	}
}
