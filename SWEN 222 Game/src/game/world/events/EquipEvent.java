package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.world.GameEvent;
import game.world.GameEvent.Type;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.items.Equipment;
import game.world.items.MoveableItem;

/**
 * Event that represents a player equipping an item
 * @author Nick Tran
 *
 */
public class EquipEvent extends GameEvent{
	
	private static Player player;
	private static Equipment item;
	
	public EquipEvent(Player player, Equipment item){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		if (item == null){
			throw new IllegalArgumentException("item is null");
		}
		
		EquipEvent.player = player;
		EquipEvent.item = item;
	}
	
	@Override
	public Type getType() {
		return GameEvent.Type.BUY;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Equipment getItem(){
		return item;
	}
	
	/**
	 * Read an EquipEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return EquipEvent
	 * @throws IOException
	 */
	public static EquipEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		Equipment item = (Equipment) Equipment.read(is);
		return new EquipEvent(player, item);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		item.write(os);
	}

}
