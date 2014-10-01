package game.world;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
	public void gameEventOccurred(GameEvent ge);
}
