package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.Position;
import game.world.characters.Player;

/**
 * Events that represent interacting with objects in the game world.
 *
 * @author Callum
 *
 */
public class InteractEvent extends GameEvent {
	
	private final Player player;
	
	public InteractEvent(Player player){
		this.player = player;
	}
	
	public GameEvent.Type getType() {
		return GameEvent.Type.INTERACT;
	}

	/**
	 * Read an InteractEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return InteractEvent
	 * @throws IOException
	 */
	//public static InteractEvent read(InputStream is) throws IOException, GameException {
		//Interactable interactable = Interactable.read(is);
		//Player player = Player.read(is);
		//return new InteractEvent(player, interactable);
	//}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
	}
}
