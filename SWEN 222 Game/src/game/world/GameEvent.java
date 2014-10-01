package game.world;

import game.util.ByteArrayConvertible;

/**
 * Represent game events using a standard interface, so they can be
 * easily communicated between nodes in a network.
 * 
 * @author Callum
 *
 */
public abstract class GameEvent implements ByteArrayConvertible {
	
	/**
	 * Convert a byte array to a GameEvent object.
	 * 
	 * @param bytes Byte array to convert
	 * @return GameEvent version of byte array
	 */
	public GameEvent fromByteArray(byte[] bytes)
	{
		return null;
	}
	
	/**
	 * Get the GameEvent.Type associated with this GameEvent.
	 * @return game event type
	 */
	public abstract GameEvent.Type getGameEventType();

	/**
	 * A Type enumeration system to uniquely identify GameEvents,
	 * using unique IDs.
	 * 
	 * @author Callum
	 *
	 */
	public enum Type {
		// All of the known possible game events.
		MOVE(0),
		INTERACT(1);
		
		// The unique ID of the event.
		private final int id;
		
		/*
		 * construct a GameEvent.Type object.
		 * @param i ID number
		 */
		private Type(int i) {
			id = i;
		}
		
		/**
		 * Get the type's unique ID.
		 * 
		 * @return type ID
		 */
		public int getID() {
			return id;
		}
	}
}
