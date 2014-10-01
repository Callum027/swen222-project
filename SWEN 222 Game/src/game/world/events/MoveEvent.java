package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.world.GameEvent;

/**
 * Events that represent moving characters in the game world.
 * 
 * @author Callum
 *
 */
public class MoveEvent extends GameEvent {
	public GameEvent.Type getType() {
		return GameEvent.Type.MOVE;
	}
	
	/**
	 * Read an MoveEvent from the input stream.
	 * 
	 * @param is Input stream
	 * @return MoveEvent
	 * @throws IOException
	 */
	public static MoveEvent read(InputStream is) throws IOException {
		return new MoveEvent();
	}
	
	public void write(OutputStream os) throws IOException {
		// Write the type header of this event to the output stream.
		getType().write(os);
		
		// Write the changes this event causes to the output stream.
	}
}
