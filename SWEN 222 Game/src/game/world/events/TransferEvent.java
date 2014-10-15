package game.world.events;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.characters.GameCharacter;
import game.world.characters.Player;
import game.world.items.MoveableItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferEvent extends GameEvent {

	private MoveableItem item;
	private int playerID;

	public TransferEvent(MoveableItem item, int playerID){
		if (item == null)
			throw new IllegalArgumentException("item is null");

		this.item = item;
		this.playerID = playerID;

	}

	@Override
	public Type getType() {
		return Type.TRANSFER;
	}

	public MoveableItem getItem(){
		return item;
	}

	public int getPlayerID(){
		return playerID;
	}

	/**
	 * Read an TransferEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return TransferEvent
	 * @throws IOException
	 */
	public static TransferEvent read(InputStream is) throws IOException, GameException {
		MoveableItem item = MoveableItem.read(is);
		int playerID = NetIO.readInt(is);

		return new TransferEvent(item, playerID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		item.write(os);
		NetIO.writeInt(os, playerID);
	}

}
