package game.world;

import game.world.characters.Player;
import game.world.events.MoveEvent;
import game.world.tiles.Tile;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class to represent the Game World and stores the areas that make up this "world"
 * It also handles all the game events being broadcasted from the client
 * @author Nick Tran
 *
 */
public class GameWorld implements GameEventListener{

	private Map<Integer,Area> areas; //a mapping from unique identifiers to their respective area
	private Map<Integer,Player> players; //a mapping from unique identifiers to their respectiove players

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
		areas.put(area.getAreaID(), area);
	}

	/**
	 * adds a player with it's id to the map of players
	 * @param player the player we are adding to the map
	 */
	public void addPlayers(Player player){
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
	 * retrieves the players contained within the world using their id
	 * @param id the id that is used to get the player with this id
	 * @return the player with the given id
	 */
	public Player getPlayer(int id){
		return players.get(id);
	}

	/**
	 * Handles the incoming game events
	 */
	@Override
	public void gameEventOccurred(GameEvent ge) {
		if (ge instanceof MoveEvent){
			MoveEvent move = (MoveEvent) ge;
			Player player = move.getPlayer();
			player.moveToPosition(move.getPosition());
		}
	}

}
