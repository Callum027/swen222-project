package game.util;

public class GamePacket {
	private final GamePacket.Type type;
	
	public GamePacket(GamePacket.Type t)
	{
		type = t;
	}
	
	public GamePacket.Type getType()
	{
		return type;
	}
	
	public static int byteArraySize() {
		// TODO Auto-generated method stub
		return 1;
	}

	public byte[] toByteArray() {
		byte[] bytes = new byte[1];
		bytes[0] = type.getID();
		
		return bytes;
	}

	public static GamePacket fromByteArray(byte[] bytes) {
		for (GamePacket.Type t: GamePacket.Type.values())
			if (t.getID() == bytes[0])
			return new GamePacket(t);
		
		return null;
	}

	/**
	 * A Type enumeration system to uniquely identify game packets,
	 * using unique 8-bit integers.
	 * 
	 * @author Callum
	 *
	 */
	public enum Type {
		// All of the known possible game packet types.
		EVENT(0);

		// The unique ID of the event.
		private final byte id;
		
		/**
		 * Construct a GamePacket.Type object.
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
	}
}
