package game.loading;

import game.world.GameWorld;
import game.world.Position;
import game.world.characters.Enemy;
import game.world.characters.Player;
import game.world.characters.classes.GameClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CharacterParser {

	public static void ParseCharacter(String fileName, GameWorld world) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			if (!gobble(scan, "<CharList>")) {

				throw new ParserError(
						"Parsing Character: Expecting <CharList>, got "
								+ scan.next());
			}
			while (!gobble(scan, "</CharList>")) {
				if (!gobble(scan, "<Character>")) {
					throw new ParserError(
							"Parsing Character: Expecting <Character>, got "
									+ scan.next());
				}
				parseCharacterType(scan, world);

				if (!gobble(scan, "</Character>")) {
					throw new ParserError(
							"Parsing Character: Expecting </Character>, got "
									+ scan.next());
				}
			}
		} catch (ParserError e) {
			e.printStackTrace();
		}
	}

	private static void parseCharacterType(Scanner scan, GameWorld world) {
		try {

			if (gobble(scan, "<Player>")) {
				parsePlayer(scan, world);
				return;
			} else if (gobble(scan, "<Enemy>")) {
				parseEnemy(scan, world);
				return;
			} else if (gobble(scan, "<Merchant>")) {
				parseMerchant(scan, world);
			} else {
				throw new ParserError(
						"Parsing CharacterType: Expecting a valid character type, got "
								+ scan.next());
			}
		} catch (ParserError e) {
			e.printStackTrace();
		}

	}

	private static void parseMerchant(Scanner scan, GameWorld world) {
		// TODO Auto-generated method stub

	}

	private static void parseEnemy(Scanner scan, GameWorld world) {
		try {
			int AreaID = parseInt(scan, "AreaID");
			int x = parseInt(scan, "XPos");
			int y = parseInt(scan, "YPos");
			Position pos = new Position(x, y);
			String name = parseString(scan, "Name");
			int id = parseInt(scan, "ID");
			Enemy enemy  = new Enemy(pos, name, id,
					GameClass.playerClass.WARRIOR);
			if (!gobble(scan, "</Enemy>")) {
				throw new ParserError(
						"Parsing Enemy: Expecting </Enemy>, got "
								+ scan.next());
			}
			world.getArea(AreaID).addEnemy(enemy);
			
		} catch (ParserError e) {
			e.printStackTrace();
		}
	}

	private static void parsePlayer(Scanner scan, GameWorld world) {
		try {
			int x = parseInt(scan, "XPos");
			int y = parseInt(scan, "YPos");
			Position pos = new Position(x, y);
			String name = parseString(scan, "Name");
			int id = parseInt(scan, "ID");
			Player player = new Player(pos, name, id,
					GameClass.playerClass.WARRIOR);
			if (!gobble(scan, "</Player>")) {
				throw new ParserError(
						"Parsing Player: Expecting </Player>, got "
								+ scan.next());
			}
			world.addPlayer(player);
		} catch (ParserError e) {
			e.printStackTrace();
		}
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
