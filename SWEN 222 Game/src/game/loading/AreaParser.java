package game.loading;

import game.world.Area;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Static class to get Areas from an XML file, will be called by main to get an
 * Area that can be added to the GameWorld.
 *
 * @author Chris Allen
 *
 */
public class AreaParser {
	// field to hold the map of tiles to int. should probably be an xml file,
	// but seems easier with limited time
	private static final String[] TILES_FILE = new String[] {
			"1, FloorTile, floor_tile3", "2, FloorTile, Carpet_Centre" };

	/**
	 * returns a map of areas with their id, should be called with a valid
	 * filename
	 *
	 * @param filename
	 * @return
	 * @throws ParserError
	 * @throws FileNotFoundException
	 */
	public static Map<Integer, Area> parseAreas(String filename)
			throws ParserError, FileNotFoundException {
		// create the tilepMap, areaMap and scanner
		Map<Integer, Tile> tileMap = createTileMap(TILES_FILE);
		Map<Integer, Area> areaMap = new HashMap<Integer, Area>();
		Scanner scan = null;
		scan = new Scanner(new File(filename));
		try {
			if (!gobble(scan, "<Areas>")) {
				throw new ParserError("Parsing Areas: Expecting <Areas>, got "
						+ scan.next());
			}
			// loop until the <Areas> declaration is closed
			while (!gobble(scan, "</Areas>")) {
				Area area = parseArea(scan, tileMap);
				areaMap.put(area.getID(), area);
			}
		} catch (ParserError error) {
			throw error;
		}

		scan.close();
		return areaMap;
	}

	/**
	 * Parse Area method, takes a filename and a map of tiles and returns an
	 * area
	 *
	 * @param filename
	 *            - filepath towards the xml file we wish to be parsed in.
	 * @param tileMap
	 *            - map of tiles to be used to convert int values to their
	 *            corresponding tiles.
	 * @return Area parsed in from file.
	 * @throws ParserError
	 */
	public static Area parseArea(String filename) throws ParserError,
			IOException {
		// create the scanner and TileMap
		Scanner scan = new Scanner(new File(filename));
		Map<Integer, Tile> tileMap = createTileMap(TILES_FILE);
		if (!gobble(scan, "<Area>")) {
			throw new ParserError("Parsing Area: Expecting <Area>, got "
					+ scan.next());
		}
		// parse all the ints
		int ID = parseInt(scan, "ID");
		int width = parseInt(scan, "Width");
		int height = parseInt(scan, "Height");
		// parse floor tiles
		Tile[][] floor = parse2DTileArray(scan, "Floor", width, height, tileMap);
		// create a 3d tile array to hold the 4 wall tile 2d arrays
		Tile[][][] walls = new Tile[4][][];
		// parse the walls in
		walls[0] = parse2DTileArray(scan, "NorthWall", width, 3, tileMap);
		walls[1] = parse2DTileArray(scan, "EastWall", height, 3, tileMap);
		walls[2] = parse2DTileArray(scan, "SouthWall", width, 3, tileMap);
		walls[3] = parse2DTileArray(scan, "WestWall", height, 3, tileMap);

		if (!gobble(scan, "</Area>")) {
			throw new ParserError("Parsing Area: Expecting </Area>, got "
					+ scan.next());
		}
		scan.close();
		return new Area(floor, walls, ID);
	}

	/**
	 * Parse Area method, takes a Scanner and a map of tiles and returns an area
	 *
	 * @param scan
	 *            - Scanner which is currently scanning areas file.
	 * @param tileMap
	 *            - map of tiles to be used to convert int values to their
	 *            corresponding tiles.
	 * @return Area parsed in from file.
	 * @throws ParserError
	 */
	public static Area parseArea(Scanner scan, Map<Integer, Tile> tileMap)
			throws ParserError {
		if (!gobble(scan, "<Area>")) {
			throw new ParserError("Parsing Area: Expecting <Area>, got "
					+ scan.next());
		}

		int ID = parseInt(scan, "ID");
		int width = parseInt(scan, "Width");
		int height = parseInt(scan, "Height");
		Tile[][] floor = parse2DTileArray(scan, "Floor", width, height, tileMap);
		Tile[][][] walls = new Tile[4][][];
		walls[0] = parse2DTileArray(scan, "NorthWall", width, 3, tileMap);
		walls[1] = parse2DTileArray(scan, "EastWall", height, 3, tileMap);
		walls[2] = parse2DTileArray(scan, "SouthWall", width, 3, tileMap);
		walls[3] = parse2DTileArray(scan, "WestWall", height, 3, tileMap);

		if (!gobble(scan, "</Area>")) {
			throw new ParserError("Parsing Area: Expecting </Area>, got "
					+ scan.next());
		}
		return new Area(floor, walls, ID);
	}

	private static int parseInt(Scanner scan, String type) throws ParserError {
		if (!gobble(scan, "<" + type + ">")) {
			throw new ParserError("Parsing Int: Expecting <" + type + ">, got "
					+ scan.next());
		}
		int value = scan.nextInt();

		if (!gobble(scan, "</" + type + ">")) {
			throw new ParserError("Parsing Int: Expecting </" + type
					+ ">, got " + scan.next());
		}
		return value;
	}

	/**
	 * Parses a 2d array of integers from an xml file and returns a 2d array of
	 * tiles
	 *
	 * @param scan
	 * @param type
	 * @param width
	 * @param height
	 * @param tileMap
	 * @return
	 * @throws ParserError
	 */
	private static Tile[][] parse2DTileArray(Scanner scan, String type,
			int width, int height, Map<Integer, Tile> tileMap)
			throws ParserError {
		if (!gobble(scan, "<" + type + ">")) {
			throw new ParserError("Parsing 2D Tile Array: Expecting <" + type
					+ ">, got " + scan.next());
		}
		Tile[][] tiles = new Tile[height][width];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				int tile = scan.nextInt();
				tiles[i][j] = tileMap.get(tile);
			}
		}

		if (!gobble(scan, "</" + type + ">")) {
			throw new ParserError("Parsing 2D Tile Array: Expecting </" + type
					+ ">, got " + scan.next());
		}

		return tiles;
	}

	/**
	 * Creates a tile map which maps a unique integer value to a unique tile, to
	 * be used in rendering the game world.
	 *
	 * The file format is: ID, TileType, Image_Name.png
	 *
	 * Where commas separate the values. ID is the integer value which maps to
	 * the tile, the TileType is either "Floor" or "Wall" and Image_Name.png is
	 * the file name of the image representing the tile.
	 *
	 * @param file
	 *            --- contains data about the tiles
	 * @return --- mapping of integers to tiles
	 */
	public static Map<Integer, Tile> createTileMap(String[] data) {
		Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
		for (int i = 0; i < data.length; i++) {
			String[] line = data[i].split(", ");
			int id = Integer.parseInt(line[0]);
			// Image image = getImage(line[2]);
			Tile tile = null;
			if (line[1].equals("FloorTile")) {
				tile = new FloorTile(line[2]);
			} else if (line[1].equals("WallTile")) {
				tile = new WallTile(line[2]);
			}
			tileMap.put(id, tile);
		}
		return tileMap;
	}

	/**
	 * helper method for parses, checks if the next string matches s, and
	 * returns the result.
	 *
	 * @param scan
	 * @param s
	 * @return
	 */
	private static boolean gobble(Scanner scan, String s) {
		if (scan.hasNext(s)) {
			scan.next();
			return true;
		}
		return false;
	}
}
