package game.world;

import java.util.EventListener;

public interface GameStateListener extends EventListener {
	/**
	 * Invoked when a change in game state occurs.
	 * 
	 * @param gs GameState object with changes
	 */
	public void gameStateChanged(GameState gs);
}
