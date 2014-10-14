package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.Streamable;

public class HelloPacket implements Streamable {

	/**
	 * Read a HelloPacket from this input stream.
	 * 
	 * @param is Input stream
	 * @return HelloPacket
	 * @throws IOException
	 */
	public static HelloPacket read(InputStream is) throws IOException {
		return new HelloPacket();
	}

	public void write(OutputStream os) throws IOException {

	}

}
