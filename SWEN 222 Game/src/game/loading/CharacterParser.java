package game.loading;

import game.world.Area;
import game.world.GameWorld;
import game.world.Position;
import game.world.characters.Enemy;
import game.world.characters.Merchant;
import game.world.characters.Player;
import game.world.characters.classes.GameClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Class used by main to parse in characters, e.g. monsters.
 *
 * @author allenchri4
 *
 */
public class CharacterParser {
	/**
	 * parses in a list of Characters and adds them to an area.
	 *
	 * @param fileName
	 * @param area
	 * @param world
	 * @throws ParserError
	 */
	public static void parseCharacters(String fileName, Area area,
			GameWorld world) throws ParserError {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (!gobble(scan, "<CharList>")) {

				throw new ParserError(
						"Parsing Characters: Expecting <CharList>, got "
								+ scan.next());
			}
			while (!gobble(scan, "</CharList>")) {
				if (!gobble(scan, "<Character>")) {
					throw new ParserError(
							"Parsing Characters: Expecting <Character>, got "
									+ scan.next());
				}
				parseCharacterType(scan, area, world);

				if (!gobble(scan, "</Character>")) {
					throw new ParserError(
							"Parsing Characters: Expecting </Character>, got "
									+ scan.next());
				}
			}
		} catch (ParserError e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * figures out the character type we wish to be parsed and calls the
	 * appropriate method.
	 *
	 * @param scan
	 * @param area
	 * @param world
	 * @throws ParserError
	 */
	private static void parseCharacterType(Scanner scan, Area area,
			GameWorld world) throws ParserError {
		if (gobble(scan, "<Player>")) {
			parsePlayer(scan, area, world);
			return;
		} else if (gobble(scan, "<Enemy>")) {
			parseEnemy(scan, area);
			return;
		} else if (gobble(scan, "<Merchant>")) {
			parseMerchant(scan, area);
		} else {
			throw new ParserError(
					"Parsing CharacterType: Expecting a valid character type, got "
							+ scan.next());
		}
	}

	/**
	 * parser method for merchants, creates a merchant character and adds it to
	 * the map.
	 *
	 * @param scan
	 * @param area
	 * @throws ParserError
	 */
	private static void parseMerchant(Scanner scan, Area area)
			throws ParserError {
		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		Position pos = new Position(x, y);
		String name = parseString(scan, "Name");
		int id = parseInt(scan, "ID");
		Merchant merchant = new Merchant(pos, name, id);
		area.addMerchant(merchant);

	}

	/**
	 * parser method for enemies, creates a enemy character and adds it to the
	 * area.
	 *
	 * @param scan
	 * @param area
	 * @throws ParserError
	 */
	private static void parseEnemy(Scanner scan, Area area) throws ParserError {
		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		Position pos = new Position(x, y);
		String name = parseString(scan, "Name");
		int id = parseInt(scan, "ID");
		Enemy enemy = new Enemy(pos, name, id, GameClass.CharacterClass.WARRIOR);
		if (!gobble(scan, "</Enemy>")) {
			throw new ParserError("Parsing Enemy: Expecting </Enemy>, got "
					+ scan.next());
		}
		area.addEnemy(enemy);
	}

	/**
	 * parser method for players, creates a player from xml file and adds it to
	 * the gameworld, shouldnt really be used
	 *
	 * @param scan
	 * @param area
	 * @param world
	 * @throws ParserError
	 */
	private static void parsePlayer(Scanner scan, Area area, GameWorld world)
			throws ParserError {
		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		Position pos = new Position(x, y);
		String name = parseString(scan, "Name");
		int id = parseInt(scan, "ID");
		Player player = new Player(pos, name, id,
				GameClass.CharacterClass.WARRIOR);
		if (!gobble(scan, "</Player>")) {
			throw new ParserError("Parsing Player: Expecting </Player>, got "
					+ scan.next());
		}
		area.addPlayer(player);
		world.addPlayer(player);
	}

	/**
	 * Helper method for CharacterParser, takes a type and checks there is an
	 * open and close for this type and returns a normal string
	 *
	 * @param scan
	 * @param type
	 * @return
	 * @throws ParserError
	 */
	private static String parseString(Scanner scan, String type)
			throws ParserError {
		if (!gobble(scan, "<" + type + ">")) {
			throw new ParserError("Parsing String: Expecting <" + type
					+ ">, got " + scan.next());
		}
		String value = scan.next();
		if (!gobble(scan, "</" + type + ">")) {
			throw new ParserError("Parsing String: Expecting </" + type
					+ ">, got " + scan.next());
		}
		return value;
	}

	/**
	 * parses in ints by consuming the type open and close then returns it
	 *
	 * @param scan
	 * @param type
	 * @return
	 * @throws ParserError
	 */
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
	 * helper method for CharacterParser, checks if the next string in scan
	 * matches s, and returns the result
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
