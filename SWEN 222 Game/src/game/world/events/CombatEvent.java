package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.characters.Enemy;
import game.world.characters.Player;
import game.world.items.Item;

/**
 * The event that handles the combat
 * @author Nick Tran
 *
 */
public class CombatEvent extends GameEvent{

	private final Player player;
	private final Enemy enemy;

	public CombatEvent (Player player, Enemy enemy){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		if (enemy == null){
			throw new IllegalArgumentException("enemy is null");
		}
		this.player = player;
		this.enemy = enemy;
	}

	public GameEvent.Type getType() {
		return GameEvent.Type.COMBAT;
	}

	public Player getPlayer(){
		return player;
	}

	public Enemy getEnemy(){
		return enemy;
	}

	/**
	 * Read a CombatEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return CombatEvent
	 * @throws IOException
	 */
	public static CombatEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		Enemy enemy = Enemy.read(is);

		return new CombatEvent(player, enemy);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		enemy.write(os);
	}


}
