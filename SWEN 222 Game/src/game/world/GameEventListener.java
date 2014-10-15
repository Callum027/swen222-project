package game.world;

import game.exceptions.GameException;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
	/**
	 * Invoked when a game event occurs.
	 * 
	 * @param ge GameEvent representing occured event
	 */
	public void gameEventOccurred(GameEvent ge) throws GameException;
}
