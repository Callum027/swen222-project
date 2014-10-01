package game.world;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class which adds support for broadcasting game events.
 * 
 * @author Callum
 *
 */
public abstract class GameEventBroadcaster {
	private Set<GameEventListener> gameEventListeners = new HashSet<GameEventListener>();
	
	/**
	 * Add a game event listener to this broadcaster.
	 * 
	 * @param gel Game event listener
	 */
	public void addGameEventListener(GameEventListener gel)
	{
		gameEventListeners.add(gel);
	}
	
	/**
	 * Broadcast the given game event to all listeners.
	 * @param ge Game event listener
	 */
	public void broadcastGameEvent(GameEvent ge)
	{
		for (GameEventListener gel: gameEventListeners)
			gel.gameEventOccurred(ge);
	}
}
