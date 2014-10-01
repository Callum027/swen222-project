package game.util;

import game.world.GameEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GamePacket implements Streamable {
	// The packet type, and payload in object form.
	private final GamePacket.Type type;
	private final Streamable payload;
	

	/**
	 * Initialise a GamePacket with a type and a streamable payload.
	 * 
	 * @param t Type
	 * @param s Payload
	 */
	public GamePacket(GamePacket.Type t, Streamable s) {
		if (t == null)
			throw new IllegalArgumentException("GamePacket.Type is null");
		
		if (s == null)
			throw new IllegalArgumentException("Streamable is null");
		
		type = t;
		payload = s;
	}
	
	/**
	 * Returns the type of game packet.
	 * 
	 * @return Game packet
	 */
	public GamePacket.Type getType()
	{
		return type;
	}
	
	/**
	 * Returns the payload object for this GamePacket.
	 * 
	 * @return Payload
	 */
	public Streamable getPayload()
	{
		return payload;
	}
	
	/**
	 * Read a game packet from the input stream. Returns null if failed.
	 * 
	 * @param is Input stream
	 * @return Game packet
	 */
	public static GamePacket read(InputStream is) throws IOException
	{
		Streamable s = null;
		GamePacket.Type t = null;
		
		// Get the packet type from the stream.
		if ((t = GamePacket.Type.read(is)) == null)
			return null;

		// Get a specific kind of payload from the input stream,
		// according to the packet type.
		switch (t)
		{
			case EVENT:
				s = GameEvent.read(is);
				break;
		}
		
		if (s == null)
			return null;
		
		return new GamePacket(t, s);
	}
	
	public void write(OutputStream os) throws IOException
	{
		// Yep, it's as simple as calling write() to the type and the payload.
		type.write(os);
		payload.write(os);
	}

	/**
	 * A Type enumeration system to uniquely identify types of GamePackets,
	 * using unique IDs.
	 * 
	 * @author Callum
	 *
	 */
	public enum Type implements Streamable {
		// All of the known possible game packet types.
		EVENT(0);
		
		// The unique ID of the event.
		private final byte id;
		
		/*
		 * construct a GamePacket.Type object.
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
		 * Return the GamePacket.Type version of a given ID.
		 * 
		 * @param id ID to convert
		 * @return GamePacket.Type version
		 */
		public static Type getTypeFromID(byte id) {
			for (Type t: values())		
				if (t.getID() == id)
					return t;
			
			return null;
		}

		/**
		 * Read a GamePacket.Type from the input stream. Returns null if it fails.
		 * 
		 * @param is InputStream
		 * @return GamePacket.Type
		 */
		public static Type read(InputStream is) throws IOException {
			return getTypeFromID((byte)is.read());
		}
		
		public void write(OutputStream os) throws IOException {
			os.write((int)id);
		}
	}
}
