package game.world.events;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.characters.Player;
import game.world.items.Consumables;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Event that represents a player consuming an item
 * @author Nick Tran
 *
 */
public class ConsumeEvent extends GameEvent{
	
	private static Player player;
	private static Consumables item;
	
	public ConsumeEvent(Player player, Consumables item){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		if (item == null){
			throw new IllegalArgumentException("item is null");
		}
		
		ConsumeEvent.player = player;
		ConsumeEvent.item = item;
	}
	
	@Override
	public Type getType() {
		return GameEvent.Type.CONSUME;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Consumables getItem(){
		return item;
	}
	
	/**
	 * Read a ConsumeEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return ConsumeEvent
	 * @throws IOException
	 */
	public static ConsumeEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		Consumables item = Consumables.read(is);
		return new ConsumeEvent(player, item);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		item.write(os);
	}

}
