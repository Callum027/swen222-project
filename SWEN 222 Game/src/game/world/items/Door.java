package game.world.items;

import game.exceptions.GameException;
import game.exceptions.InvalidItemException;
import game.world.Position;
import game.world.characters.Player;

import java.io.IOException;
import java.io.InputStream;

/**
 * A Door is a special extension of the Furniture class which is used
 * to transport players in the game between different Areas.
 *
 * @author David Sheridan
 *
 */
public class Door extends Furniture{

	// fields
	private int areaID;
	private Position transport;
	private boolean keyRequired;

	/**
	 * Constructions an instance of a Door
	 *
	 * Parameters unique to Door:
	 * @param areaID
	 * 			--- the area ID for the Area this Door transports to
	 * @param transport
	 * 			--- position player is to be transported to in the next area
	 * @param keyRequired
	 * 			--- is a key required to open this door?
	 */
	public Door(Position position, int height, int id,  String name, MoveableItem item, int areaID, Position transport, boolean keyRequired){
		super(position, height, id, name, null);
		this.areaID = areaID;
		this.transport = transport;
		this.keyRequired = keyRequired;
	}

	/**
	 * Returns the areaID that this door leads to.
	 *
	 * @return
	 * 		--- the area ID
	 */
	public int getAreaID(){
		return areaID;
	}

	/**
	 * Returnts the position that the player will be transported to
	 * in the area they are moving into.
	 *
	 * @return
	 * 		--- transport position
	 */
	public Position getTransportPosition(){
		return transport;
	}

	/**
	 * Returns true if a key is required to open this door,
	 * otherwise returns false.
	 *
	 * @return
	 * 		--- is key required to open door?
	 */
	public boolean isKeyRequired(){
		return keyRequired;
	}

	/**
	 * Allows the Player to transport to the Area associated with
	 * this Door. If the door requires a key and the Player does not
	 * have one then the Player will not be able to transport to the
	 * next Area.
	 */
	public void interact(Player player){
		// Go through the door.
	}
	
	/**
	 * Reads a door from the input stream.
	 * Differs from Item.read() by actually testing if the read item is
	 * a Door, and if not, throwing an exception.
	 * 
	 * @param is the inputstream
	 * @return the door with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Door read(InputStream is) throws IOException, GameException {
		Item i = Item.read(is);
		
		if (i instanceof Door)
			return (Door)i;
		else
			throw new InvalidItemException(Door.class, i);
	}
}
