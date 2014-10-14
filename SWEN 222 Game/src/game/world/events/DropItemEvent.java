package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.world.Area;
import game.world.GameEvent;
import game.world.items.Equipment;
import game.world.items.Item;
import game.world.items.MoveableItem;

public class DropItemEvent extends GameEvent{

	private static Item item;
	private static Area area;

	public DropItemEvent(Item item, Area area){
		if(item == null){
			throw new IllegalArgumentException("item is null");
		}
		if(area == null){
			throw new IllegalArgumentException("area is null");
		}

		DropItemEvent.item = item;
		DropItemEvent.area = area;
	}

	public Item getItem(){
		return item;
	}

	public Area getArea(){
		return area;
	}

	@Override
	public Type getType() {
		return GameEvent.Type.DROP_ITEM;
	}

	/**
	 * Read an MoveEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return MoveEvent
	 * @throws IOException
	 */
	public static DropItemEvent read(InputStream is) throws IOException, GameException {
		if (item instanceof MoveableItem){
			MoveableItem move = MoveableItem.read(is);
			return new DropItemEvent(move, area);
		}

		if (item instanceof Equipment){
			Equipment equipment = (Equipment) Equipment.read(is);
			return new DropItemEvent(equipment, area);
		}

		return new DropItemEvent(item, area);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		item.write(os);
		area.write(os);
	}

}
