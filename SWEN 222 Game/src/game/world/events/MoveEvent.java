package game.world.events;

import game.world.GameEvent;

/**
 * Events that represent characters moving, somewhere in the world.
 * 
 * @author Callum
 *
 */
public class MoveEvent extends GameEvent {

	public Type getGameEventType() {
		return Type.MOVE;
	}

	@Override
	public byte[] toByteArray() {
		// TODO Auto-generated method stub
		return null;
	}

}
