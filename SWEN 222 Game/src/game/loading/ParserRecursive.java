package game.loading;

import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import game.world.Area;
import game.world.tiles.Tile;

public class ParserRecursive {
private static String next;
private static int height = -1;
private static int width = -1;
private static int id = -1;
private static Tile[][] tiles;

	public static Area parse(String fileName, Map<Integer, Tile> tileMap) {
		if(fileName != null){
		try {
			Scanner scan = new Scanner(new File(fileName));
			if(!scan.hasNext()){
				error("The Area File is empty.");
			}
			next = scan.next();
			if(!gobble(scan, "<Area>")){
				error("Missing Area Declaration.");
			}
			parseArea(scan, tileMap);
			scan.close();
			return new Area(tiles);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserError e) {
			System.out.println("Parser Error: "+e.getMessage());
		}
		}
		return null;
	}

	private static void parseArea(Scanner scan, Map<Integer, Tile> tileMap) throws ParserError {
		if(gobble(scan, "</Area>")){
			return;
		}
		if(gobble(scan, "<ID>")){
			int i = parseInt(scan);
			if(!gobble(scan, "</ID>")){
				error("Missing ID close Declaration.");
			}
			id = i;
			parseArea(scan, tileMap);
		}
		if(gobble(scan, "<Height>")){
			int h = parseInt(scan);
			if(!gobble(scan, "</Height>")){
				error("Missing Height close Declaration.");
			}
			height = h;
			parseArea(scan, tileMap);
		}
		if(gobble(scan, "<Width>")){
			int w = parseInt(scan);
			if(!gobble(scan, "</Width>")){
				error("Missing Width close Declaration.");
			}
			width = w;
			parseArea(scan, tileMap);
		}
		if(gobble(scan, "<FloorLayout>")){
			tiles = parseFloor(scan, tileMap);
			if(!gobble(scan, "</FloorLayout>")){
				error("Missing Floor Layout close.");
			}
			parseArea(scan, tileMap);
		}
	}

	private static Tile[][] parseFloor(Scanner scan, Map<Integer, Tile> tileMap) throws ParserError {
		if(height < 0 || width < 0) error("Invalid Height or Width of area.");
		Tile[][] tiles = new Tile[width][height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				tiles[x][y] =
			}
		}
		return null;

	}

	private static int parseInt(Scanner scan) throws ParserError {
		if(!gobble(scan, "<Int>")){
			error("Missing Int declaration.");
		}
		if(!scan.hasNextInt()){
			error("missing int value.");
		}
		int id =  scan.nextInt();
		if(!gobble(scan, "</Int>")){
			error("Missing Int close declaration.");
		}
		return id;
	}

	private static boolean gobble(Scanner scan, String s) throws ParserError {
		if(!scan.hasNext()){
			error("Missing Declaration");
		}
		if(next.equals(s)){
			next = scan.next();
			return true;
		}
		return false;

	}

	private static void error(String message) throws ParserError {
		throw new ParserError(message);
	}
}
