package game.loading;

import game.world.GameWorld;
import game.world.characters.Enemy;
import game.world.characters.Player;

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
						"Parsing Characters: Expecting <CharList>, got "
								+ scan.next());
			}
			while (!gobble(scan, "</CharList>")) {
				if (!gobble(scan, "<Character>")) {
					throw new ParserError(
							"Parsing Characters: Expecting <Character>, got "
									+ scan.next());
				}
				parseCharacterType(scan, world);

				if (!gobble(scan, "</Character>")) {
					throw new ParserError(
							"Parsing Items: Expecting </Character>, got "
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
			}
			else if(gobble(scan, "<Enemy>")){
				parseEnemy(scan, world);
				return;
			}
			else if(gobble(scan, "<Merchant>")){
				parseMerchant(scan, world);
			}
			else{
				throw new ParserError("Parsing CharacterType: Invalid Character type.");
			}
		} catch (ParserError e) {
			e.printStackTrace();
		}

	}

	private static void parseMerchant(Scanner scan, GameWorld world) {
		// TODO Auto-generated method stub
		
	}

	private static Enemy parseEnemy(Scanner scan, GameWorld world) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void parsePlayer(Scanner scan, GameWorld world) {
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
