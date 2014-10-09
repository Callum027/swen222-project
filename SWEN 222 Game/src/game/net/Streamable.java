package game.net;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Give a standard interface for writing bytes to an output stream.
 * This gives the user direct control over what bytes are sent over
 * networks, for example, allowing maximum optimisation of packet size.
 *
 * @author Callum
 *
 */
public interface Streamable {
	/**
	 * Write the streamable object to the output stream.
	 *
	 * @param os Output stream
	 * @throws IOException
	 */
	public void write(OutputStream os) throws IOException;
}
