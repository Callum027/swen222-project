package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.Position;
import game.world.characters.Player;

/**
 * Events that represent moving characters in the game world.
 *
 * @author Callum
 *
 */
public class MoveEvent extends GameEvent {

	private final Player player;
	private final Position position;

	/**
	 * Construct a new MoveEvent with the given position and player.
	 * @param position Position moved to
	 * @param player Played affected
	 */
	public MoveEvent(Position position, Player player){
		if (position == null)
			throw new IllegalArgumentException("position is null");

		if (player == null)
			throw new IllegalArgumentException("player is null");


		this.position = position;
		this.player = player;
	}

	public GameEvent.Type getType() {
		return GameEvent.Type.MOVE;
	}

	/**
	 * Get the position the player was moved to.
	 *
	 * @return new position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Get the affected player.
	 *
	 * @return affected player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Read an MoveEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return MoveEvent
	 * @throws IOException
	 */
	public static MoveEvent read(InputStream is) throws IOException, GameException {
		Position position = Position.read(is);
		Player player = Player.read(is);

		return new MoveEvent(position, player);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		position.write(os);
		player.write(os);
	}
}
