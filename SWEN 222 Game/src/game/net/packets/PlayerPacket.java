package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.NetIO;
import game.net.Streamable;

public class PlayerPacket implements Streamable {
	private final int id;
	
	public PlayerPacket(int i) {
		id = i;
	}
	
	public int getID() {
		return id;
	}
	
	/**
	 * Read a PlayerPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return AckPacket
	 * @throws IOException
	 */
	public static PlayerPacket read(InputStream is) throws IOException {
		return new PlayerPacket(NetIO.readInt(is));
	}

	public void write(OutputStream os) throws IOException {
		NetIO.writeInt(os, id);
	}

}
