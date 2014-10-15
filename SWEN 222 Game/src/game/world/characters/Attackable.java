package game.world.characters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.exceptions.InvalidGameEventException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.Position;
import game.world.GameEvent.Type;

/**
 * an interface that defines a character as an attackable object
 * defines a character that can attack and can be attacked
 * @author Nick Tran
 *
 */
public interface Attackable extends Streamable{

	/**
	 * handles the logic when a character attacks a target
	 */
	public void attack(Attackable target);

	public int getHealth();

	/**
	 * calculates the damage the character deals to another character
	 */
	public int calculateDamage();

	public Position getPosition();

	public int calculateDistance(Attackable target, Attackable attacker);

	public int getAttack();

	public void setHealth(int health);

	public boolean getIsDead();

	public void setIsDead(boolean deadOrAlive);

	//public static Attackable read(InputStream is) throws IOException, GameException {
		//Attackable.AttackableCharacters ac = Attackable.AttackableCharacters.read(is);
		//switch (ac){
			//case PLAYER:

			//case ENEMY:

		//}
	//}

	public void write(OutputStream os) throws IOException;

	/**
	 * An Attackable Character enumeration system to uniquely identify characters that are attackable,
	 * using unique IDs.
	 *
	 * @author Nick Tran
	 *
	 */
	public enum AttackableCharacters implements Streamable {
		// All of the known possible types of attackable characters
		PLAYER(0),
		ENEMY(1);

		// The unique ID of the event.
		private final byte id;

		/*
		 * construct an Attackable.AttackableCharacters object.
		 * @param i ID number
		 */
		private AttackableCharacters(int i) {
			id = (byte)i;
		}

		/**
		 * Get the AttackableCharcters' unique ID.
		 *
		 * @return type ID
		 */
		public byte getID() {
			return id;
		}

		/**
		 * Return the Attackable.AttackableCharacters version of a given ID.
		 *
		 * @param id ID to convert
		 * @return Attackable.AttackableCharacters attackable character
		 */
		public static AttackableCharacters getAttackableCharactersFromID(byte id) throws GameException {
			for (AttackableCharacters t: values())
				if (t.getID() == id)
					return t;

			throw new InvalidGameEventException(id);
		}

		/**
		 * Read a Attackable.AttackableCharacters from the input stream. Returns null if it fails.
		 *
		 * @param is InputStream
		 * @return Attackable.AttackableCharacters
		 */
		public static Attackable.AttackableCharacters read(InputStream is) throws IOException, GameException {
			return getAttackableCharactersFromID(NetIO.readByte(is));
		}

		public void write(OutputStream os) throws IOException {
			NetIO.writeByte(os, id);
		}
	}

}
