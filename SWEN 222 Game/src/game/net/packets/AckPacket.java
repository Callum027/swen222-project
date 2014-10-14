package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.Streamable;

public class AckPacket implements Streamable {

	/**
	 * Read a AckPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return AckPacket
	 * @throws IOException
	 */
	public static AckPacket read(InputStream is) throws IOException {
		return new AckPacket();
	}

	public void write(OutputStream os) throws IOException {

	}

}
