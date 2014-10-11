package game.exceptions;
import game.net.GamePacket;


public class InvalidGamePacketException extends GameException {
	private static final long serialVersionUID = 1L;

	public InvalidGamePacketException(byte b)
	{
		super("invalid game packet type: " + b);
	}
	
	public InvalidGamePacketException(GamePacket.Type t) {
		super("invalid game packet type: " + t);
	}
}
