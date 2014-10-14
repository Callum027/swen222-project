package game.world;

import game.world.characters.Enemy;
import game.world.characters.GameCharacter;
import game.world.characters.Player;
import game.world.events.MoveEvent;

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
	}

}
