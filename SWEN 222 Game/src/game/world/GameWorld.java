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
 * @author Nick Tran
 *
 */
public class GameWorld implements GameEventListener{

	private Map<Integer,Area> areas;
	private Map<Integer,Player> players;

	public GameWorld(){
		areas = new HashMap<Integer,Area>();
		players = new HashMap<Integer,Player>();
	}

	public void addArea(Area area){
		areas.put(area.getAreaID(), area);
	}

	public void addPlayers(Player player){
		players.put(player.getId(), player);
	}

	public Map<Integer,Area> getAreas(){
		return areas;
	}

	public Player getPlayer(int id){
		return players.get(id);
	}

	public void setAreas(Map<Integer, Area> areas){
		this.areas = areas;
	}

	@Override
	public void gameEventOccurred(GameEvent ge) {
		if (ge instanceof MoveEvent){
			MoveEvent move = (MoveEvent) ge;
			Player player = move.getPlayer();
			player.moveToPosition(move.getPosition());
		}
	}

}
