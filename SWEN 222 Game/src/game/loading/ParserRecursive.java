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
			if(!gobble(scan, "<Area>")){
				error("Missing Area Declaration.");
			}
			Area newArea = new Area();
			parseArea(newArea, scan, tileMap);
			scan.close();
			return newArea;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ParserError e) {
			System.out.println("Parser Error: "+e.getMessage());
		}
		}
		return null;
	}

	private static Area parseArea(Area newArea, Scanner scan, Map<Integer, Tile> tileMap) throws ParserError {
		if(gobble(scan, "</Area>")){
			return newArea;
		}
		if(gobble(scan, "<ID>")){
			int id = parseInt(scan);
			newArea.setAreaID(id);
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
