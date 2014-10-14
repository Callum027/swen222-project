package game.world;

import game.world.characters.GameCharacter;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.events.BuyEvent;
import game.world.events.DropItemEvent;
import game.world.events.EquipEvent;
import game.world.events.InteractEvent;
import game.world.events.MoveEvent;
import game.world.events.PickUpEvent;
import game.world.events.TransportEvent;
import game.world.items.Consumables;
import game.world.items.Equipment;
import game.world.items.Item;
import game.world.items.MoveableItem;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to represent the Game World and stores the areas that make up this "world"
 * It also handles all the game events being broadcasted from the client
 * @author Nick Tran
 *
 */
public class GameWorld implements GameEventListener{

	private Map<Integer,Area> areas; //a mapping from unique identifiers to their respective area
	private Map<Integer,Player> players; //a mapping from unique identifiers to their respective players

	/**
	 * The constructor:
	 * Initializes the areas and the player maps
	 */
	public GameWorld(){
		areas = new HashMap<Integer,Area>();
		players = new HashMap<Integer,Player>();
	}

	/**
	 * adds an area along with it's id to the map
	 * @param area the area to add to the map
	 */
	public void addArea(Area area){
		areas.put(area.getID(), area);
	}
	
	/**
	 * Sets areas to be the specifed areaMap
	 * @param areaMap
	 */
	public void addAreas(Map<Integer, Area> areaMap){
		this.areas = areaMap;
	}

	/**
	 * adds a player with it's id to the map of players
	 * @param player the player we are adding to the map
	 */
	public void addPlayer(Player player){
		players.put(player.getId(), player);
	}

	/**
	 * retrieves the areas contained within the world
	 * @return a map of the areas
	 */
	public Map<Integer,Area> getAreas(){
		return areas;
	}

	/**
	 * retrieves the players contained within the world
	 * @return a map of the players
	 */
	public Map<Integer,Player> getPlayers(){
		return players;
	}

	/**
	 * retrieves the players contained within the world using their id
	 * @param id the id that is used to get the player with this id
	 * @return the player with the given id
	 */
	public Player getPlayer(int id){
		return players.get(id);
	}

	/**
	 * retrieves the areas contained within the world using their id
	 * @param id the id that is used to get the area with this id
	 * @return the area with the given id
	 */
	public Area getArea(int id){
		return areas.get(id);
	}

	/**
	 * Handles the incoming game events
	 */
	@Override
	public void gameEventOccurred(GameEvent ge) {
		if (ge instanceof MoveEvent){
			MoveEvent move = (MoveEvent) ge;
			GameCharacter gameCharacter = move.getGameCharacter();
			gameCharacter.moveToPosition(move.getPosition());
		}
		if(ge instanceof DropItemEvent){
			DropItemEvent drop = (DropItemEvent) ge;
			Item item = drop.getItem();
			Position p = drop.getPosition();
			int areaID = drop.getAreaID();
			Area area = areas.get(areaID);
			item.setPosition(p);
			area.addItem(item);
		}
		if (ge instanceof InteractEvent){
			InteractEvent interact = (InteractEvent) ge;
			Item item = interact.getItem();
			Player player = interact.getPlayer();
			item.interact(player);
		}
		if(ge instanceof TransportEvent){
			TransportEvent transport = (TransportEvent) ge;
			Player player = transport.getPlayer();
			int areaID = transport.getAreaID();
			if(areas.containsKey(areaID)){
				areas.get(areaID).addPlayer(player);
			}
		}
		if(ge instanceof PickUpEvent){
			PickUpEvent pickUp = (PickUpEvent) ge;
			Player player = pickUp.getPlayer();
			MoveableItem item = pickUp.getItem();
			int areaID = pickUp.getAreaID();
			Area area = areas.get(areaID);
			player.addItem(item);
			area.removeItem(item);
		}
		if (ge instanceof BuyEvent){
			BuyEvent buy = (BuyEvent) ge;
			Player player = buy.getPlayer();
			Merchant merchant = buy.getMerchant();
			MoveableItem item = buy.getItem();
			
			/*
			 * Only consumable items and equippable items are sellable
			 */
			if (!(item instanceof Equipment) || !(item instanceof Consumables)){
				throw new IllegalArgumentException("This type of item is not sellable!");
			}
			
			merchant.sellWares(item, player);
		}
		if (ge instanceof EquipEvent){
			EquipEvent equip = (EquipEvent) ge;
			Player player = equip.getPlayer();
			Equipment item = equip.getItem();
			if (item.getSlot() == 0){
				player.getEquipped().equipHead(item);
			}
		}
	}

}
