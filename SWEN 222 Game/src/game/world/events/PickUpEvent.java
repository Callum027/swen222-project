package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.characters.Player;
import game.world.items.Container;
import game.world.items.Furniture;
import game.world.items.Item;
import game.world.items.MoveableItem;

/**
 * Events that represent the player picking up items 
 * @author Nick Tran
 *
 */
public class PickUpEvent extends GameEvent{
	
	private static Player player;
	private static MoveableItem item;
	private static int areaID;

	public PickUpEvent(Player player, MoveableItem item, int areaID){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		if (item == null){
			throw new IllegalArgumentException("item is null");
		}
		
		PickUpEvent.player = player;
		PickUpEvent.item = item;
		PickUpEvent.areaID = areaID;
	}

	@Override
	public Type getType() {
		return GameEvent.Type.PICK_UP;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public MoveableItem getItem(){
		return item;
	}

	public int getAreaID(){
		return areaID;
	}
	
	/**
	 * Read a PickUpEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return PickUpEvent
	 * @throws IOException
	 */
	public static PickUpEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		
		MoveableItem item = MoveableItem.read(is);
		return new PickUpEvent(player, item, areaID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		item.write(os);
		NetIO.writeInt(os, areaID);
	}

}
