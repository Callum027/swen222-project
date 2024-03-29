package game.world.events;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.characters.Player;
import game.world.items.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Events that represent interacting with objects in the game world.
 *
 * @author Callum
 *
 */
public class InteractEvent extends GameEvent {
	
	private final Player player;
	private final Item item;
	
	public InteractEvent(Player player, Item item){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		
		if (item == null){
			throw new IllegalArgumentException("item is null");
		}
		
		this.player = player;
		this.item = item;
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
	public static InteractEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		Item item = Item.read(is);
		
		return new InteractEvent(player, item);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		item.write(os);
	}

	public Player getPlayer() {
		return player;
	}

	public Item getItem() {
		return item;
	}
}
