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

public class CharacterParser {

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

	private static void parsePlayer(Scanner scan, Area area, GameWorld world)
			throws ParserError {
		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		Position pos = new Position(x, y);
		String name = parseString(scan, "Name");
		int id = parseInt(scan, "ID");
		Player player = new Player(pos, name, id, GameClass.CharacterClass.WARRIOR);
		if (!gobble(scan, "</Player>")) {
			throw new ParserError("Parsing Player: Expecting </Player>, got "
					+ scan.next());
		}
		area.addPlayer(player);
		world.addPlayer(player);
	}

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

	private static boolean gobble(Scanner scan, String s) {
		if (scan.hasNext(s)) {
			scan.next();
			return true;
		}
		return false;
	}
}
