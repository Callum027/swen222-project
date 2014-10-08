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
	private List<Item> itemsList; // the items located in this area
	private Tile[][] tiles; //the tiles that make up this area
	private WallTile[][][] walls;

	public Area(Tile[][] tiles){
		this.tiles = tiles;
		itemsList = new ArrayList<Item>();
	}

}
