package game.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.exceptions.InvalidGameEventException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.events.BuyEvent;
import game.world.events.CombatEvent;
import game.world.events.ConsumeEvent;
import game.world.events.DropItemEvent;
import game.world.events.EquipEvent;
import game.world.events.InteractEvent;
import game.world.events.MoveEvent;

/**
 * Represent game events using a standard interface, so they can be
 * easily communicated between nodes in a network.
 *
 * @author Callum
 *
 */
public abstract class GameEvent implements Streamable {
	/**
	 * Get the GameEvent.Type associated with this GameEvent.
	 * @return game event type
	 */
	public abstract GameEvent.Type getType();

	/**
	 * Read a game event from the input stream.
	 *
	 * @param is Input stream
	 * @return Game event
	 */
	public static GameEvent read(InputStream is) throws IOException, GameException {
		// Get the packet type from the stream.
		GameEvent.Type t = GameEvent.Type.read(is);

		switch (t)
		{
			case MOVE:
				return MoveEvent.read(is);
			case INTERACT:
				return InteractEvent.read(is);
			case DROP_ITEM:
				return DropItemEvent.read(is);
			case BUY:
				return BuyEvent.read(is);
			case EQUIP:
				return EquipEvent.read(is);
			case CONSUME:
				return ConsumeEvent.read(is);
			case COMBAT:
				return CombatEvent.read(is);
			default:
				throw new InvalidGameEventException(t);
		}
	}

	/**
	 * Write the GameEvent header to the output stream. Any class which extends GameEvent
	 * should override this method and call the superclass method at the start.
	 *
	 * @param os Output stream
	 * @throws IOException
	 */
	public void write(OutputStream os) throws IOException {
		// Write the type header to the output stream.
		getType().write(os);
	}

	/**
	 * A Type enumeration system to uniquely identify GameEvents,
	 * using unique IDs.
	 *
	 * @author Callum
	 *
	 */
	public enum Type implements Streamable {
		// All of the known possible game events.
		INTERACT(0),
		DROP_ITEM(1),
		PICK_UP(2),
		TRANSPORT(3),
		BUY(4),
		EQUIP(5),
		CONSUME(6),
		MOVE(7),
		COMBAT(8),
		TRANSFER(9);


		// The unique ID of the event.
		private final byte id;

		/*
		 * construct a GameEvent.Type object.
		 * @param i ID number
		 */
		private Type(int i) {
			id = (byte)i;
		}

		/**
		 * Get the type's unique ID.
		 *
		 * @return type ID
		 */
		public byte getID() {
			return id;
		}

		/**
		 * Return the GameEvent.Type version of a given ID.
		 *
		 * @param id ID to convert
		 * @return GameEvent.Type version
		 */
		public static Type getTypeFromID(byte id) throws GameException {
			for (Type t: values())
				if (t.getID() == id)
					return t;

			throw new InvalidGameEventException(id);
		}

		/**
		 * Read a GameEvent.Type from the input stream. Returns null if it fails.
		 *
		 * @param is InputStream
		 * @return GameEvent.Type
		 */
		public static Type read(InputStream is) throws IOException, GameException {
			return getTypeFromID(NetIO.readByte(is));
		}

		public void write(OutputStream os) throws IOException {
			NetIO.writeByte(os, id);
		}
	}
}
