package game.world;

import game.world.characters.Player;
import game.world.events.MoveEvent;
import game.world.tiles.Tile;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class to represent the Game World and stores the areas that make up this "world"
 * @author Nick Tran
 *
 */
public class GameWorld implements GameEventListener{

	private List<Area> areas;
	private List<Player> players;

	public GameWorld(){
		areas = new ArrayList<Area>();
		players = new ArrayList<Player>();
	}

	/**
	 * adds areas to the world
	 * @param file the file that stores the grid of tiles
	 * @return true is the add was successful
	 */
	public boolean addArea(File file){
		return areas.add(new Area(this, file));
	}

	public boolean addPlayers(Player player){
		return players.add(player);
	}

	/**
	 * Adds the specified area to the world.
	 *
	 * @param area - the area to be added
	 * @return true if the add was successful
	 */
	public boolean addArea(Area area){
		return areas.add(area);
	}

	/**
	 * Retrieves the areas that make up the game world
	 * @return the ArrayList of areas
	 */
	public List<Area> getAreas(){
		return areas;
	}

	public Player getPlayer(int id){
		return players.get(id);
	}

	/**
	 * Gives the GameWorld a new ArrayList of areas
	 * @param areas the new ARrayList of areas
	 */
	public void setAreas(List<Area> areas){
		this.areas = areas;
	}

	@Override
	public void gameEventOccurred(GameEvent ge) {
		if (ge instanceof MoveEvent){
			MoveEvent move = (MoveEvent) ge;
			Player player = move.getPlayer();
			player.moveToPosition(new Point(move.getX(), move.getY()));
		}
	}

}
