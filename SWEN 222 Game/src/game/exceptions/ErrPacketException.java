package game.exceptions;

import game.net.packets.ErrPacket;

public class ErrPacketException extends GameException {
	private static final long serialVersionUID = 1L;

	private final ErrPacket errPacket;
	
	public ErrPacketException(ErrPacket ep, String s) {
		super(false, s);
		errPacket = ep;
	}
	
	public boolean shouldResendPacket() {
		return errPacket.shouldResendPacket();
	}
}