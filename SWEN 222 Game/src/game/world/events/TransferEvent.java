package game.world.events;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.items.Container;
import game.world.items.MoveableItem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransferEvent extends GameEvent {

	private MoveableItem item;
	private int playerID;
	private int containerID;

	public TransferEvent(MoveableItem item, int playerID, int containerID){
		if (item == null)
			throw new IllegalArgumentException("item is null");

		this.containerID=containerID;
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

	public int getCont(){
		return containerID;
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
		int containerID = NetIO.readInt(is);
		return new TransferEvent(item, playerID, containerID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		item.write(os);
		NetIO.writeInt(os, playerID);
		NetIO.writeInt(os, containerID);
	}

}
