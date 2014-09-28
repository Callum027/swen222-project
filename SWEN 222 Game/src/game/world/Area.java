package game.world;

import java.io.File;
import java.util.ArrayList;

import game.world.items.Item;
import game.world.tiles.Tile;

/**
 * An area of the Game world
 * Holds all the tiles that make up this area along with the items contained within the area 
 * @author Nick Tran
 *
 */
public class Area {
	
	private GameWorld world;
	private Tile[][] tiles; //the tiles that make up this area
	private ArrayList<Item> items; //the items located on this area
	private final int MAXIMUM_CAPACITY = 25; //just a number to initialize the array of Items and 2D array of Tiles
	
	/**
	 * The constructor for the area
	 * It takes a GameWorld object which is the GameWorld in which this area is contained in
	 * @param world the world that contains this area
	 * @param file the file that contains the grid of tiles
	 */
	public Area(GameWorld world, File file){
		this.world = world;
		initializeTiles(file); //reads the grid of tiles from the file
		items =  new ArrayList<Item>();
	}

	/**
	 * Getter method for the GameWorld
	 * @return the world that contains this area
	 */
	public GameWorld getWorld() {
		return world;
	}
	
	/**
	 * Adds an item to the list of items that the area holds
	 * Having an add method limits access to the ArrayList
	 * @param item the item to add to the ArrayList
	 * @return true if the add was successful 
	 */
	public boolean addItem(Item item){
		if (items.add(item)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Removes an item from the list of items that the area holds
	 * Having a remove method limits access to the ArrayList
	 * @param item the item to remove from the ArrayList
	 * @return true if the remove was successful 
	 */
	public boolean removeItem(Item item){
		if (items.remove(item)){
			return true;
		}
		
		return false;
	}
	
	/*
	/**
	 * Retrieves the items located within this area
	 * @return the items in this area
	 *
	public ArrayList<Item> getItems(){
		return items;
	}*/

	/**
	 * Retrieves the tiles that make up this area
	 * @return the tiles
	 */
	public Tile[][] getTiles(){
		return tiles;
	}
	
	/**
	 * Reads a grid of tiles from the file and adds it to the 2D array of tiles
	 * @param file the file to read the grid of tiles from
	 */
	public void initializeTiles(File file){
		this.tiles = new Tile[MAXIMUM_CAPACITY][MAXIMUM_CAPACITY];
		//read file
		//add tiles according to file
	}
	
	
}
