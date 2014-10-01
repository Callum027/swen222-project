package game.world;

import game.world.tiles.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class to represent the Game World and stores the areas that make up this "world"
 * @author Nick Tran
 *
 */
public class GameWorld {

	private List<Area> areas;

	public GameWorld(){
		areas = new ArrayList<Area>();
	}

	/**
	 * adds areas to the world
	 * @param file the file that stores the grid of tiles
	 * @return true is the add was successful
	 */
	public boolean addArea(File file){
		if (areas.add(new Area(this, file))){
			return true;
		}
		return false;
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

	/**
	 * Gives the GameWorld a new ArrayList of areas
	 * @param areas the new ARrayList of areas
	 */
	public void setAreas(List<Area> areas){
		this.areas = areas;
	}

}
