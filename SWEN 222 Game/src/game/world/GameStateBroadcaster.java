package game.world;

import java.util.HashSet;
import java.util.Set;

/**
 * An abstract class which adds support for broadcasting game state changes.
 * 
 * @author Callum
 *
 */
public class GameStateBroadcaster {
	private Set<GameStateListener> gameStateListeners = new HashSet<GameStateListener>();
	
	/**
	 * Add a game state listener to this broadcaster.
	 * 
	 * @param gsl Game state listener
	 */
	public void addGameEventListener(GameStateListener gsl)
	{
		gameStateListeners.add(gsl);
	}
	
	/**
	 * Broadcast the given game state change to all listeners.
	 * @param ge Game event listener
	 */
	public void broadcastGameState(GameState gs)
	{
		for (GameStateListener gsl: gameStateListeners)
			gsl.gameStateChanged(gs);
	}
}
