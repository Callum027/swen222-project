package game.world;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	private int areaID;
	private List<Item> items; // the items located in this area
	private Tile[][] tiles; //the tiles that make up this area
	private Tile[][][] walls;

	public Area(Tile[][] tiles, Tile[][][] walls, int areaID){
		this.tiles = tiles;
		items = new ArrayList<Item>();
		this.areaID = areaID;
		this.walls = walls;
	}

	public int getAreaID() {
		return areaID;
	}

	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public Tile[][][] getWalls() {
		return walls;
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean addItem(Item item){
		return items.add(item);
	}

	public boolean removeItem(Item item){
		return items.remove(item);
	}
}
