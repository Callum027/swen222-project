package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.Streamable;

public class QuitPacket implements Streamable {

	/**
	 * Read a QuitPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return QuitPacket
	 * @throws IOException
	 */
	public static QuitPacket read(InputStream is) throws IOException {
		return new QuitPacket();
	}

	public void write(OutputStream os) throws IOException {

	}

}
