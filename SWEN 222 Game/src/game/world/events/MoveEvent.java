package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.NetIO;
import game.world.GameEvent;
import game.world.characters.PlayableCharacter;

/**
 * Events that represent moving characters in the game world.
 *
 * @author Callum
 *
 */
public class MoveEvent extends GameEvent {

	private final PlayableCharacter player;
	private final int x;
	private final int y;

	public MoveEvent(int x, int y, PlayableCharacter player){
		if (player == null)
			throw new IllegalArgumentException("player is null");
		
		this.x = x;
		this.y = y;
		this.player = player;
	}

	public GameEvent.Type getType() {
		return GameEvent.Type.MOVE;
	}

	/**
	 * Read an MoveEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return MoveEvent
	 * @throws IOException
	 */
	public static MoveEvent read(InputStream is) throws IOException {
		int x = NetIO.readInt(is);
		int y = NetIO.readInt(is);
		PlayableCharacter player = PlayableCharacter.read(is);
		return new MoveEvent(x, y, player);
	}

	public void write(OutputStream os) throws IOException {
		// Write the type header of this event to the output stream.
		getType().write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public PlayableCharacter getPlayer(){
		return player;
	}
}
