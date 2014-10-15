package game.world.characters.classes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.exceptions.InvalidCharacterClassException;
import game.exceptions.InvalidGamePacketException;
import game.net.NetIO;
import game.net.GamePacket.Type;
import game.world.characters.Attackable;

/**
 * The strategy interface for the game classes
 * @author Nick Tran
 *
 */
public interface GameClass {

	/**
	 * makes it easier to assign the classes within the context classes
	 */
	public enum CharacterClass{
		WARRIOR(0),
		MAGE(1),
		ROGUE(2);
		
		// The unique ID of the class.
		private final byte id;

		/*
		 * construct a GameClass.CharacterClass object.
		 * @param i ID number
		 */
		private CharacterClass(int i) {
			id = (byte)i;
		}

		/**
		 * Get the character class's unique ID.
		 *
		 * @return class ID
		 */
		public byte getID() {
			return id;
		}

		/**
		 * Return the GameClass.CharacterClass version of a given ID.
		 *
		 * @param id ID to convert
		 * @return GamePacket.Type version
		 */
		public static CharacterClass getCharacterClassFromID(byte id) throws GameException {
			for (CharacterClass cc: values())
				if (cc.getID() == id)
					return cc;

			throw new InvalidCharacterClassException(id);
		}

		/**
		 * Read a GamePacket.Type from the input stream. Returns null if it fails.
		 *
		 * @param is InputStream
		 * @return GamePacket.Type
		 */
		public static CharacterClass read(InputStream is) throws IOException, GameException {
			return getCharacterClassFromID(NetIO.readByte(is));
		}

		public void write(OutputStream os) throws IOException {
			NetIO.writeByte(os, id);
		}
	}

	/**
	 * Retrieves the classes base strength
	 * @return the strength of the class
	 */
	public int getStrength();

	/**
	 * Retrieves the classes base dexterity
	 * @return the dexterity of the class
	 */
	public int getDexterity();

	/**
	 * Retrieves the classes base intelligence
	 * @return the intelligence of the class
	 */
	public int getIntelligence();

	/**
	 * handles the logic when a character attacks a target
	 */
	public void attack(Attackable target);

	/**
	 * calculates the damage the character deals to another character
	 * @return
	 */
	public int calculateDamage();

	public int calculateDistance(Attackable target, Attackable attacker);
}
