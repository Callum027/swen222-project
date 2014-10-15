package game.world;

import game.world.characters.Enemy;
import game.world.characters.GameCharacter;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.events.BuyEvent;
import game.world.events.ConsumeEvent;
import game.world.events.DropItemEvent;
import game.world.events.EquipEvent;
import game.world.events.InteractEvent;
import game.world.events.InventoryEvent;
import game.world.events.MoveEvent;
import game.world.events.PickUpEvent;
import game.world.events.TransferEvent;
import game.world.events.TransportEvent;
import game.world.events.CombatEvent;
import game.world.items.Consumables;
import game.world.items.Container;
import game.world.items.Equipment;
import game.world.items.Item;
import game.world.items.MoveableItem;

import java.util.HashMap;
import java.util.List;
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

		// Add the item into the hash map, but only if there isn't
		// an item there with the same ID already.
		if (!players.containsKey(id))
		{
			// If the ID of the new player equals the current next ID,
			// increment the ID until it is unique again.
			if (id == nextPlayerID)
				while (players.containsKey(++nextPlayerID));

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
	
	public boolean removePlayer(int playerID) {
		players.remove(playerID);
		return true;
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

		// Add the item into the hash map, but only if there isn't
		// an item there with the same ID already.
		if (!items.containsKey(id))
		{
			// If the ID of the new item equals the current next ID,
			// increment the ID until it is unique again.
			if (id == nextItemID)
				while (items.containsKey(++nextItemID));

			items.put(i.getID(), i);
			return true;
		};

		return false;
	}

	/**
	 * Remove an item from the game world.
	 *
	 * @param i Item ID to remove
	 * @return true if removed
	 */
	public boolean removeItem(Item i){
		items.remove(i.getID());
		return true;
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
		if (ge instanceof CombatEvent){
			combatEvent(ge);
		}
		if (ge instanceof TransferEvent){
			transferEvent(ge);
		}
		//if(ge instanceof InventoryEvent){
			//inventoryEvent(ge);
		//}
	}


	/*
	 * delegated the event handling to the methods below
	 */

	public void moveEvent(GameEvent ge){
		MoveEvent move = (MoveEvent) ge;
		GameCharacter gameCharacter = move.getGameCharacter();
		gameCharacter.moveToPosition(move.getPosition()); //moves a charcter to a potsition
	}

	public void interactEvent(GameEvent ge){
		InteractEvent interact = (InteractEvent) ge;
		Item item = interact.getItem();
		Player player = interact.getPlayer();
		item.interact(player); //player interacts with an object
	}

	public void dropItemEvent(GameEvent ge){
		DropItemEvent drop = (DropItemEvent) ge;
		Item item = drop.getItem();
		Position p = drop.getPosition();
		int areaID = drop.getAreaID();
		Area area = areas.get(areaID);
		item.setPosition(p); //drops the item  at the current position
		area.addItem(item); //the area owns the item now
	}

	public void pickUpEvent(GameEvent ge){
		PickUpEvent pickUp = (PickUpEvent) ge;
		Player player = pickUp.getPlayer();
		MoveableItem item = pickUp.getItem();
		int areaID = pickUp.getAreaID();
		Area area = areas.get(areaID);
		player.addItem(item); //added item into the player's inventory
		area.removeItem(item); //removes an item from the area, the item gets picked-up
	}

	public void transportEvent(GameEvent ge){
		TransportEvent transport = (TransportEvent) ge;
		Player player = transport.getPlayer();
		int areaID = transport.getAreaID();
		if(areas.containsKey(areaID)){
			areas.get(areaID).addPlayer(player); //player travels to a new area
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

		merchant.sellWares(item, player); //sells an item to the player
	}

	public void equipEvent(GameEvent ge){
		EquipEvent equip = (EquipEvent) ge;
		Player player = equip.getPlayer();
		Equipment item = equip.getItem();
		if (item.getSlot() == 0){
			player.getEquipped().equipHead(item);
			player.getItems().remove(item);
		}
		if (item.getSlot() == 1){
			player.getEquipped().equipMainHand(item);
			player.getItems().remove(item);
		}
		if (item.getSlot() == 2){
			player.getEquipped().equipOffHand(item);
			player.getItems().remove(item);
		}
		if (item.getSlot() == 3){
			player.getEquipped().equipBody(item);
			player.getItems().remove(item);
		}
		if (item.getSlot() == 4){
			player.getEquipped().equipBoots(item);
			player.getItems().remove(item);
		}
	}

	public void consumeEvent(GameEvent ge){
		ConsumeEvent consume = (ConsumeEvent) ge;
		Player player = consume.getPlayer();
		Consumables item = consume.getItem();
		player.getItems().remove(item);
		item.buff(player); //increases the player's health
	}

	public void combatEvent(GameEvent ge){
		CombatEvent combat = (CombatEvent) ge;
		Player player = combat.getPlayer();
		Enemy enemy = combat.getEnemy();
		player.attack(enemy); //player attacks an enemy
		enemy.attack(player); //the enemy retaliates
	}

	public void transferEvent(GameEvent ge) {
		TransferEvent transfer = (TransferEvent) ge;
		int playerID = transfer.getPlayerID();
		Player player = players.get(playerID);
		MoveableItem item = transfer.getItem();
		//Container cont = transfer.getCont();
		//List<MoveableItem> loot
	}
}
