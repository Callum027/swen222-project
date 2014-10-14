package game.world.movement;

import game.world.Area;
import game.world.GameEventBroadcaster;
import game.world.characters.Enemy;

public interface MovementStrategy {
	
	public void execute(GameEventBroadcaster geb, Enemy enemy, Area area);
}
