package game.world.movement;

import java.util.Random;

import game.exceptions.GameException;
import game.world.Area;
import game.world.GameEventBroadcaster;
import game.world.Position;
import game.world.characters.Enemy;
import game.world.events.MoveEvent;

public class RandomMovement implements MovementStrategy {

	public void execute(GameEventBroadcaster geb, Enemy enemy, Area area){
		Random random = new Random();
		Position current = enemy.getPosition();
		Position p = null;
		int move = random.nextInt(4);
		// move north
		if(move == 0){
			p = new Position(current.getX(), current.getY() - 1);
		}
		// move east
		else if(move == 1){
			p = new Position(current.getX() + 1, current.getY());
		}
		// move south
		else if(move == 2){
			p = new Position(current.getX(), current.getY() + 1);
		}
		// move west
		else if(move == 3){
			p = new Position(current.getX() - 1, current.getY());
		}

		// check if position is moveable, if so commit to movement
		if(p != null && area.isMoveablePosition(p)){
			MoveEvent m = new MoveEvent(p, enemy);
			try {
				geb.broadcastGameEvent(m);
			} catch (GameException e) {
			}
		}
	}
}
