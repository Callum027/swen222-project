package game.world;

import game.world.characters.GameCharacter;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.events.BuyEvent;
import game.world.events.ConsumeEvent;
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

	private Map<Integer, Area> areas; // A mapping from unique identifiers to their respective area

	private Map<Integer, Player> players; // A mapping from unique identifiers to their respective players
	private int nextPlayerID = 0; // The next unique player ID that can be used
	
	private Map<Integer, Item> items; // A mapping from unique identifiers to their respective items
	private int nextItemID = 0; // The next unique item ID that can be used

	/**
	 * The constructor:
	 * Initializes the areas and the player maps
	 */
	public GameWorld(){
		areas = new HashMap<Integer,Area>();
		players = new HashMap<Integer,Player>();
		items = new HashMap<Integer, Item>();
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
	 * Get the next unique player ID
	 * @return item ID
	 */
	public int getNextPlayerID(){
		return nextPlayerID;
	}
	
	/**
	 * Add a new player to the game world.
	 * @param i
	 */
	public boolean addPlayer(Player p){
		int id = p.getId();

		// If the ID of the new player equals the current next ID,
		// increment the ID until it is unique again.
		if (id == nextPlayerID)
			while (!players.containsKey(++nextPlayerID));

		// Add the item into the hash map, but only if there isn't
		// an item there with the same ID already.
		if (!players.containsKey(id))
		{
			players.put(id, p);
			return true;
		};

		return false;
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
	 * Add an item to the game world.
	 * @param i
	 */
	public boolean addItem(Item i){
		int id = i.getID();

		// If the ID of the new item equals the current next ID,
		// increment the ID until it is unique again.
		if (id == nextItemID)
			while (!items.containsKey(++nextItemID));

		// Add the item into the hash map, but only if there isn't
		// an item there with the same ID already.
		if (!items.containsKey(id))
		{
			items.put(i.getID(), i);
			return true;
		};

		return false;
	}

	/**
	 * Get the next unique ID
	 * @return item ID
	 */
	public int getNextItemID(){
		return nextItemID;
	}

	/**
	 * Returns the item associated with an ID.
	 *
	 * @param id Item ID
	 * @return Item
	 */
	public Item getItem(int id){
		return items.get(id);
	}

	/**
	 * Handles the incoming game events
	 */
	@Override
	public void gameEventOccurred(GameEvent ge) {
		if (ge instanceof MoveEvent){
			moveEvent(ge);
		}
		if(ge instanceof DropItemEvent){
			dropItemEvent(ge);
		}
		if (ge instanceof InteractEvent){
			interactEvent(ge);
		}
		if(ge instanceof TransportEvent){
			transportEvent(ge);
		}
		if(ge instanceof PickUpEvent){
			pickUpEvent(ge);
		}
		if (ge instanceof BuyEvent){
			buyEvent(ge);
		}
		if (ge instanceof EquipEvent){
			equipEvent(ge);
		}
		if (ge instanceof ConsumeEvent){
			consumeEvent(ge);
		}
	}

	public void moveEvent(GameEvent ge){
		MoveEvent move = (MoveEvent) ge;
		GameCharacter gameCharacter = move.getGameCharacter();
		gameCharacter.moveToPosition(move.getPosition());
	}

	public void interactEvent(GameEvent ge){
		InteractEvent interact = (InteractEvent) ge;
		Item item = interact.getItem();
		Player player = interact.getPlayer();
		item.interact(player);
	}

	public void dropItemEvent(GameEvent ge){
		DropItemEvent drop = (DropItemEvent) ge;
		Item item = drop.getItem();
		Position p = drop.getPosition();
		int areaID = drop.getAreaID();
		Area area = areas.get(areaID);
		item.setPosition(p);
		area.addItem(item);
	}

	public void pickUpEvent(GameEvent ge){
		PickUpEvent pickUp = (PickUpEvent) ge;
		Player player = pickUp.getPlayer();
		MoveableItem item = pickUp.getItem();
		int areaID = pickUp.getAreaID();
		Area area = areas.get(areaID);
		player.addItem(item);
		area.removeItem(item);
	}

	public void transportEvent(GameEvent ge){
		TransportEvent transport = (TransportEvent) ge;
		Player player = transport.getPlayer();
		int areaID = transport.getAreaID();
		if(areas.containsKey(areaID)){
			areas.get(areaID).addPlayer(player);
		}
	}

	public void buyEvent(GameEvent ge){
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

	public void equipEvent(GameEvent ge){
		EquipEvent equip = (EquipEvent) ge;
		Player player = equip.getPlayer();
		Equipment item = equip.getItem();
		if (item.getSlot() == 0){
			player.getEquipped().equipHead(item);
			player.getItems().remove(item);
		}
	}

	public void consumeEvent(GameEvent ge){
		ConsumeEvent consume = (ConsumeEvent) ge;
		Player player = consume.getPlayer();
		Consumables item = consume.getItem();
		player.getItems().remove(item);
		item.buff(player);
	}
}
