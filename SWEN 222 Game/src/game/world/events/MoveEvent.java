package game.world.events;

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
	
	public static InteractEvent read(InputStream is) {
		return new InteractEvent();
	}
	
	public boolean write(OutputStream os) {
		// Write the type header of this event to the output stream.
		if (!getType().write(os))
			return false;
		
		// Write the changes this event causes to the output stream.
		
		return true;
	}
}
