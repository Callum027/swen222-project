package game.world;

/**
 * Represent game events using a standard interface, so they can be
 * easily communicated between nodes in a network.
 * 
 * @author Callum
 *
 */
public interface GameEvent {
	/**
	 * Get the GameEvent.Type associated with this GameEvent.
	 * @return game event type
	 */
	public GameEvent.Type getGameEventType();

	/**
	 * A Type enumeration system to uniquely identify GameEvents,
	 * using unique IDs.
	 * 
	 * @author Callum
	 *
	 */
	public enum Type {
		// All of the known possible game events.
		MOVE(1),
		INTERACT(2);
		
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
