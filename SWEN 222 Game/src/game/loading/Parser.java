package game.loading;

import game.world.Area;
import game.world.GameWorld;
import game.world.tiles.Tile;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

/**
 * This Class holds all the methods for loading a world from an xml file (will
 * make comments look swanky later).
 * 
 * @author Tsun
 *
 */
public class Parser {
	// probably obsolete but will leave in for now in case i need it later
	public GameWorld parseWorld(Scanner scan) {
		try {
			scan.useDelimiter("\\s*(?=<)|(?<=>)\\s*");
			if (!scan.hasNext())
				throw new ParserError("Empty xml file.");
			// shouldn't get here if there is no next.
			GameWorld world = new GameWorld();
			if (!scan.next().equals("World"))
				throw new ParserError("Invalid declaration, should be a World.");
			while (scan.hasNext()) {
				String temp = scan.next();
				if (temp.equals("World"))
					break;
				// world.addArea(parseArea(scan));
			}
			return world;
		} catch (ParserError pError) {
			System.out.println("Parser Error: " + pError.getMessage());
		}
		return null;
	}

	/**
	 * Parses in an area from a file, later will be extended to account for
	 * different kinds of tiles atm just creates an area from a 2d int array.
	 * 
	 * @param f
	 * @return
	 */
	@SuppressWarnings("resource")
	public static Area parseArea(String fileName, Map<Integer, Tile> tileMap) {
		// am doubling up on checks atm, will fix later if i remove parseWorld.
		// fields for determining where in the 2d array a tile should go.
		int xMax;
		int yMax;
		try {
			Scanner scan = new Scanner(new File(fileName));
			// check Scanner isn't empty
			if (!scan.hasNext())
				throw new ParserError("Empty xml file.");
			// open Area declaration
			if (!scan.next().equals("<Area>"))
				throw new ParserError("Invalid declaration, should be an Area.");
			// check there is a dimension declaration
			if (!scan.hasNext())
				throw new ParserError("No Dimension Declaration");
			if (!scan.next().equals("<Dimension>"))
				throw new ParserError(
						"Invalid declaration, should be a Dimension.");
			// find the width/x for the area
			if (!scan.hasNextInt())
				throw new ParserError("No x value for the tile arraylist.");
			xMax = scan.nextInt();
			// find the height/y for the area
			if (!scan.hasNextInt())
				throw new ParserError("No y value for the tile arraylist.");
			yMax = scan.nextInt();
			// close the dimension
			if (!scan.hasNext())
				throw new ParserError("No Dimension closing Declaration");
			if (!scan.next().equals("</Dimension>"))
				throw new ParserError("No closing declaration for Dimension.");
			// parse in the tiles, for now am using a 2d int array
			// Area area = new Area(parseTiles(scan));
			// temp array for holding tiles
			Tile[][] tiles = new Tile[xMax][yMax];
			for (int y = 0; y < yMax; y++) {
				for (int x = 0; x < xMax; x++) {
					if (!scan.hasNextInt())
						throw new ParserError("No tiles in file?!");
					tiles[x][y] = tileMap.get(scan.nextInt());
				}
			}
			// close Area declaration
			if (!scan.hasNext())
				throw new ParserError("No Area closing declaration.");
			if (!scan.next().equals("</Area>"))
				throw new ParserError(
						"Invalid declaration, should be an Area close.");
			scan.close();
			return new Area(tiles);
		} catch (ParserError pError) {
			System.out.println("Parser Error: " + pError.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}