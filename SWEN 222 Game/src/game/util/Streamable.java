package game.util;

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
	 * Write the streamable object to the output stream, and return
	 * true if it succeeded sending all bytes.
	 * 
	 * @param os Output stream
	 * @return True if succeeded, false otherwise
	 */
	public void write(OutputStream os) throws IOException;
}
