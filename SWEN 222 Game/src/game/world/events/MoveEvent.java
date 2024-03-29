package game.world.events;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.Position;
import game.world.characters.GameCharacter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Events that represent moving characters in the game world.
 *
 * @author Callum
 *
 */
public class MoveEvent extends GameEvent {

	private GameCharacter gameCharacter;
	private final Position position;

	/**
	 * Construct a new MoveEvent with the given position and player.
	 * @param position Position moved to
	 * @param player Played affected
	 */
	public MoveEvent(Position position, GameCharacter gameCharacter){
		if (position == null)
			throw new IllegalArgumentException("position is null");

		if (gameCharacter == null)
			throw new IllegalArgumentException("player is null");


		this.position = position;
		this.gameCharacter = gameCharacter;
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
	 * Get the affected game character.
	 *
	 * @return affected game character
	 */
	public GameCharacter getGameCharacter() {
		return gameCharacter;
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
		GameCharacter gameCharacter = GameCharacter.read(is);

		return new MoveEvent(position, gameCharacter);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		position.write(os);
		gameCharacter.write(os);
	}
}
