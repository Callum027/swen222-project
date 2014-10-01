package game.world.events;

import game.world.GameEvent;

/**
 * Events that represent with interacting with objects in the game world.
 * 
 * @author Callum
 *
 */
public class InteractEvent implements GameEvent {

	public Type getGameEventType() {
		return Type.INTERACT;
	}

}
