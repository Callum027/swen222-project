package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.GameEvent.Type;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.items.Consumables;
import game.world.items.Equipment;
import game.world.items.MoveableItem;

/**
 * The event that represents the player buying an item from a merchant
 * @author Nick Tran
 *
 */
public class BuyEvent extends GameEvent{
	
	private static Player player;
	private static Merchant merchant;
	private static MoveableItem item;
	
	@Override
	public Type getType() {
		return GameEvent.Type.BUY;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public Merchant getMerchant(){
		return merchant;
	}
	
	public MoveableItem getItem(){
		return item;
	}
	
	public BuyEvent(Player player, Merchant merchant, MoveableItem item){
		if (player == null){
			throw new IllegalArgumentException("player is null");
		}
		if (merchant == null){
			throw new IllegalArgumentException("merchant is null");
		}
		if (item == null){
			throw new IllegalArgumentException("item is null");
		}
		
		BuyEvent.player = player;
		BuyEvent.merchant = merchant;
		BuyEvent.item = item;
	}
	
	/**
	 * Read an BuyEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return BuyEvent
	 * @throws IOException
	 */
	public static BuyEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		Merchant merchant = Merchant.read(is);
		MoveableItem item = MoveableItem.read(is);
		return new BuyEvent(player, merchant, item);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		merchant.write(os);
		item.write(os);
	}

}
