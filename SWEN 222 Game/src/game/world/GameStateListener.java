package game.world;

import java.util.EventListener;

public interface GameStateListener extends EventListener {
	public void gameStateChanged(GameState gs);
}
