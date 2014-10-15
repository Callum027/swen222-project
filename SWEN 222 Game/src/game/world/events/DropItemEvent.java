package game.world.events;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.Position;
import game.world.items.Item;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DropItemEvent extends GameEvent{

	private final Item item;
	private final Position position;
	private final int areaID;

	public DropItemEvent(Item item, Position position, int areaID){
		if(item == null){
			throw new IllegalArgumentException("item is null");
		}

		this.item = item;
		this.position = position;
		this.areaID = areaID;
	}

	public Item getItem(){
		return item;
	}

	public Position getPosition(){
		return position;
	}

	public int getAreaID(){
		return areaID;
	}

	@Override
	public Type getType() {
		return GameEvent.Type.DROP_ITEM;
	}

	/**
	 * Read an DropItemEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return DropItemEvent
	 * @throws IOException
	 */
	public static DropItemEvent read(InputStream is) throws IOException, GameException {
		Item item = Item.read(is);
		Position position = Position.read(is);
		int areaID = NetIO.readInt(is);

		return new DropItemEvent(item, position, areaID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		item.write(os);
		position.write(os);
		NetIO.writeInt(os, areaID);
	}

}
