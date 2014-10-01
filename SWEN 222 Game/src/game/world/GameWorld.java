package game.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
