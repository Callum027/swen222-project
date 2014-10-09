package game.exceptions;
import game.exceptions.GameException;


public class InvalidGamePacketException extends GameException {
	public InvalidGamePacketException(byte b)
	{
		super("invalid game packet type: " + b);
	}
}
