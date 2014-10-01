package game.world.events;

import game.world.GameEvent;

/**
 * Events that represent characters moving, somewhere in the world.
 * 
 * @author Callum
 *
 */
public class MoveEvent implements GameEvent {

	public Type getGameEventType() {
		return Type.MOVE;
	}

}
