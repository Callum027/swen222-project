package game.world.items;

import game.world.Position;
import game.world.characters.Player;

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
	private boolean keyRequired;
	
	/**
	 * Constructions an instance of a Door
	 *
	 * Parameters unique to Door:
	 * @param areaID
	 * 			--- the area ID for the Area this Door transports to
	 * @param keyRequired
	 * 			--- is a key required to open this door?
	 */
	public Door(Position position, int height, int id,  String name, MoveableItem item, int areaID, boolean keyRequired){
		super(position, height, id, name, null);
		this.areaID = areaID;
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
		
	}
}
