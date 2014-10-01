package game.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import game.world.items.Item;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

/**
 * An area of the Game world
 * Holds all the tiles that make up this area along with the items contained within the area
 * @author Nick Tran
 *
 */
public class Area {

	private Item[][] items; //the items located on this area
	private GameWorld world;
	private Tile[][] tiles; //the tiles that make up this area
	private WallTile[][] northWall;
	private WallTile[][] eastWall;
	private WallTile[][] southWall;
	private WallTile[][] westWall;
	private final int MAXIMUM_CAPACITY = 25; //just a number to initialize the array of Items and 2D array of Tiles
	//private int width;
	//private int height;
	//private int id; //the unique identifier

	/**
	 * The constructor for the area
	 * It takes a GameWorld object which is the GameWorld in which this area is contained in
	 * @param world the world that contains this area
	 * @param file the file that contains the grid of tiles
	 */
	public Area(GameWorld world, File file){
		this.world = world;
		initializeTiles(file); //reads the grid of tiles from the file
		items =  new Item[MAXIMUM_CAPACITY][MAXIMUM_CAPACITY];
	}

	public Area(Tile[][] tiles){
		this.tiles = tiles;
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
	public boolean addItem(Item item, int x, int y){
		if (items[x][y] == null){
			this.items[x][y] = item;
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
	public boolean removeItem(Item item, int x, int y){
		if (items[x][y] != null){
			items[x][y] = null;
			return true;
		}
		return false;
	}

	public Item[][] getItems(){
		return items;
	}

	public void setItems(Item[][] items){
		this.items = items;
	}

	/**
	 * Retrieves the tiles that make up this area
	 * @return the tiles
	 */
	public Tile[][] getTiles(){
		return tiles;
	}

	public void setUpFloorTiles(File file){
		try{
			Scanner scan = new Scanner(file);
			// first line contains width/height of area
			String[] dimensions = scan.nextLine().split(", ");
			int width = Integer.parseInt(dimensions[0]);
			int height = Integer.parseInt(dimensions[1]);
			tiles = new FloorTile[height][width];
			for(int i = 0; i < height; i++){
				String[] line = scan.nextLine().split(", ");
				for(int j = 0; j < line.length; j++){

				}
			}
			scan.close();
		}catch(IOException e){System.out.println(e);}
	}

	public void setUpWallTiles(File file){

	}

	public void setUpItems(File file){

	}

	/**
	 * Reads a grid of tiles from the file and adds it to the 2D array of tiles
	 * @param file the file to read the grid of tiles from
	 */
	public void initializeTiles(File file){
		setUpFloorTiles(file);
	}

	public static Area parseArea(String[] data, Map<Integer, Tile> tileMap){
		// first line contains width/height of area
		String[] dimensions = data[0].split(", ");
		int width = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		Tile[][] tiles = new FloorTile[height][width];
		for(int i = 1; i < height; i++){
			String[] line = data[1].split(", ");
			for(int j = 0; j < line.length; j++){
				tiles[i][j] = tileMap.get(line[j]);
			}
		}
		return new Area(tiles);
	}
}
