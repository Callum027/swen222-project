package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.net.Streamable;

public class ErrPacket implements Streamable {
	private boolean resendPacket;
	
	public ErrPacket(boolean rp) {
		resendPacket = rp;
	}
	
	public boolean shouldResendPacket() {
		return resendPacket;
	}
	
	/**
	 * Read an ErrPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return AckPacket
	 * @throws IOException
	 */
	public static ErrPacket read(InputStream is) throws IOException, GameException {
		return new ErrPacket(NetIO.readBoolean(is));
	}

	public void write(OutputStream os) throws IOException {
		NetIO.writeBoolean(os, resendPacket);
	}

}
