package game.world.events;

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
