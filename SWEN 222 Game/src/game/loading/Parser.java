package game.loading;

import game.world.Area;
import game.world.GameWorld;

import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
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
	private Area parseArea(File f) {
		// am doubling up on checks atm, will fix later if i remove parseWorld.
		// fields for determining where in the 2d array a tile should go.
		int xMax;
		int yMax;
		try {
			Scanner scan = new Scanner(f);
			scan.useDelimiter("\\s*(?=<)|(?<=>)\\s*");
			if (!scan.hasNext())
				throw new ParserError("Empty xml file.");
			if (!scan.next().equals("Area"))
				throw new ParserError("Invalid declaration, should be an Area.");
			if (!scan.hasNextInt())
				throw new ParserError("No x value for the tile arraylist.");
			xMax = scan.nextInt();
			if (!scan.hasNextInt())
				throw new ParserError("No y value for the tile arraylist.");
			yMax = scan.nextInt();
			if (!scan.hasNext())
				throw new ParserError("No tiles in file?!");
			int[][] tiles = new int[xMax][yMax];
			for (int y = 0; y < yMax; y++) {
				for (int x = 0; x < xMax; x++) {
					if (!scan.hasNextInt())
						throw new ParserError("No tiles in file?!");
					tiles[x][y] = scan.nextInt();
				}
			}
		} catch (ParserError pError) {
			System.out.println("Parser Error: " + pError.getMessage());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}