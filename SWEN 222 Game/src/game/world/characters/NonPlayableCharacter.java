package game.world.characters;

import java.util.ArrayList;

import game.world.items.Equipment;
import game.world.items.Item;

/**
 * A class for all the non-playable characters aside from the enemies and bosses of the game
 * @author Nick Tran
 *
 */
public class NonPlayableCharacter extends GameCharacter{

	/**
	 * Constructor
	 * Invokes the super constructor
	 * @param x the x position of the Character
	 * @param y the y position of the Character
	 * @param name the name of the character
	 */
	public NonPlayableCharacter(int x, int y, String name){
		super(x, y, name);
	}
}
