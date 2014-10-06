package game.loading;

import java.util.Map;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import game.world.Area;
import game.world.tiles.Tile;

public class ParserRecursive {
private static String next;
	public static Area parse(String fileName, Map<Integer, Tile> tileMap) {
		if(fileName != null){
		try {
			Scanner scan = new Scanner(new File(fileName));
			if(!scan.hasNext()){
				error("The Area File is empty.");
			}
			next = scan.next();
			gobble(scan, "<Area>");
			Area newArea = parseArea(scan, tileMap);

			return newArea;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserError e) {
			System.out.println("Parser Error: "+e.getMessage());
		}
		}
		return null;
	}

	private static Area parseArea(Scanner scan, Map<Integer, Tile> tileMap) {

		return null;
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
