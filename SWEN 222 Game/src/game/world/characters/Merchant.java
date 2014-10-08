package game.world.characters;

import java.awt.Point;
import java.util.ArrayList;

import game.world.items.Equipment;
import game.world.items.Item;
import game.world.items.MoveableItem;

/**
 * A class for all the non-playable characters aside from the enemies and bosses of the game
 * @author Nick Tran
 *
 */
public class Merchant extends GameCharacter{

	public Merchant(Point position, String name, int id, MoveableItem[] wares){
		super(position, name, id, wares);
	}
	
	public void sellWares(Equipment item, Player player){
		player.setCats(player.getCats()-item.getWorth()); //deducts money from the player
		player.getInventory()[player.getCount()] = item;
		getWares().remove(item);
		this.setCats(this.getCats()+item.getWorth()); //increase the Merchant's money
	}
}
