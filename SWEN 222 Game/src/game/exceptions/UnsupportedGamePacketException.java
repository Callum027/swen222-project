package game.exceptions;
import game.net.GamePacket;


public class UnsupportedGamePacketException extends GameException {
	private static final long serialVersionUID = 1L;

	public UnsupportedGamePacketException(byte b)
	{
		super("game packet type ID is unsupported by this version: " + b);
	}
	
	public UnsupportedGamePacketException(GamePacket.Type t) {
		super("game packet type is unsupported by this version: " + t);
	}
}
