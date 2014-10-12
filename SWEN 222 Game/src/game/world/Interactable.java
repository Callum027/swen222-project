package game.world;

import game.world.characters.Player;

/**
 * An interactable interface used by the InteractEvent Event Broadcaster
 * An interface is required since the player can interact with both the Merchant and Items
 * @author Nick
 *
 */
public interface Interactable {
	
	/**
	 * The em,thod that gets called when a player interacts with stuff
	 * @param player the player that is interacting with stuff
	 */
	public void interact(Player player);

}
