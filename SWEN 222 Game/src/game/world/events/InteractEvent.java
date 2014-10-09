package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.world.GameEvent;

/**
 * Events that represent interacting with objects in the game world.
 *
 * @author Callum
 *
 */
public class InteractEvent extends GameEvent {
	public GameEvent.Type getType() {
		return GameEvent.Type.INTERACT;
	}

	/**
	 * Read an InteractEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return InteractEvent
	 * @throws IOException
	 */
	public static InteractEvent read(InputStream is) throws IOException {
		return new InteractEvent();
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
	}
}
