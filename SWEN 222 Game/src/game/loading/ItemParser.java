package game.loading;

import game.world.Area;
import game.world.GameWorld;
import game.world.Position;
import game.world.items.Consumables;
import game.world.items.Container;
import game.world.items.Door;
import game.world.items.Equipment;
import game.world.items.Furniture;
import game.world.items.Item;
import game.world.items.MoveableItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * class called by main to parse items into areas.
 *
 * @author allenchri4
 *
 */
public class ItemParser {
	/**
	 * Parses items into the area from a xml file containing a list of items
	 *
	 * @param filename
	 * @param area
	 * @param world
	 * @throws ParserError
	 * @throws FileNotFoundException
	 */
	public static void parseItemList(String filename, Area area, GameWorld world)
			throws ParserError, FileNotFoundException {
		Scanner scan = new Scanner(new File(filename));

		if (!gobble(scan, "<ItemList>")) {
			throw new ParserError("Parsing Items: Expecting <ItemList>, got "
					+ scan.next());
		}
		while (!gobble(scan, "</ItemList>")) {
			if (!gobble(scan, "<Item>")) {
				throw new ParserError("Parsing Items: Expecting <Item>, got "
						+ scan.next());
			}
			area.addItem(parseItem(scan, world));
			if (!gobble(scan, "</Item>")) {
				throw new ParserError("Parsing Items: Expecting </Item>, got "
						+ scan.next());
			}
		}
		scan.close();
	}

	/**
	 * parses in all the fields that the items all have, then calls the
	 * appropriate parser for each different item type
	 *
	 * @param scan
	 * @param world
	 * @return
	 * @throws ParserError
	 */
	private static Item parseItem(Scanner scan, GameWorld world)
			throws ParserError {

		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		int height = parseInt(scan, "Height");
		Position pos = new Position(x, y);
		int ID = world.getNextItemID();
		String name = parseString(scan, "Name");
		if (gobble(scan, "<Container>")) {
			return parseContainer(scan, pos, height, name, ID, world);
		} else if (gobble(scan, "<Consumable>")) {
			return parseConsumable(scan, pos, height, name, ID);
		} else if (gobble(scan, "<Equipment>")) {
			return parseEquipment(scan, pos, height, name, ID);
		} else if (gobble(scan, "<Furniture>")) {
			return parseFurniture(scan, pos, height, name, ID, world);
		} else if (gobble(scan, "<MoveableItem>")) {
			return parseMoveableItem(scan, pos, height, name, ID);
		} else if (gobble(scan, "<Door>")) {
			return parseDoor(scan, pos, height, name, ID);
		} else {
			throw new ParserError(
					"Parsing Item: Expecting a valid item type, got "
							+ scan.next());
		}
	}

	/**
	 * parser for consumables, creates a consumable using the fields parsed
	 * earlier and parses in the fields unique to consumables
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @return
	 * @throws ParserError
	 */
	private static Item parseConsumable(Scanner scan, Position pos, int height,
			String name, int ID) throws ParserError {
		int worth = parseInt(scan, "Worth");
		if (!gobble(scan, "<BuffPercentage>")) {
			throw new ParserError(
					"Parsing Consumable: Expecting <BuffPercentage>, got "
							+ scan.next());
		}
		if (!scan.hasNextFloat()) {
			throw new ParserError(
					"Parsing Consumable: Expecting Float, got Nothing");
		}
		Float buff = scan.nextFloat();
		if (!gobble(scan, "</BuffPercentage>")) {
			throw new ParserError(
					"Parsing Consumable: Expecting </BuffPercentage>, got "
							+ scan.next());
		}
		return new Consumables(pos, height, ID, name, worth, buff);
	}

	/**
	 * * parser for MoveableItems, creates a consumable using the fields parsed
	 * earlier and parses in the fields unique to MoveableItems
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @return
	 */
	private static MoveableItem parseMoveableItem(Scanner scan, Position pos,
			int height, String name, int ID) {
		try {
			int worth = parseInt(scan, "Worth");
			if (!gobble(scan, "</MoveableItem>")) {
				throw new ParserError(
						"Parsing MoveableItem: Expecting </MoveableItem>, got "
								+ scan.next());
			}
			return new MoveableItem(pos, height, ID, name, worth);
		} catch (ParserError e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Parser for items that are stored within other items.
	 *
	 * @param scan
	 * @param world
	 * @return
	 * @throws ParserError
	 */
	private static MoveableItem parseStoredItem(Scanner scan, GameWorld world)
			throws ParserError {
		int height = parseInt(scan, "Height");
		int ID = world.getNextItemID();
		String name = parseString(scan, "Name");
		int worth = parseInt(scan, "Worth");
		MoveableItem temp = new MoveableItem(new Position(-1, -1), height,
				ID + 1, name, worth);
		world.addItem(temp);
		return temp;
	}

	/**
	 * Parser for furniture, uses the fields parsed in earlier and parses in
	 * fields unique to furniture.
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @param world
	 * @return
	 * @throws ParserError
	 */
	private static Furniture parseFurniture(Scanner scan, Position pos,
			int height, String name, int ID, GameWorld world)
			throws ParserError {
		if (gobble(scan, "<StoredItem>")) {
			MoveableItem storedItem = parseStoredItem(scan, world);
			return new Furniture(pos, height, ID, name, storedItem);
		} else if (gobble(scan, "</Furniture>")) {
			return new Furniture(pos, height, ID, name, null);
		} else {
			throw new ParserError(
					"Parsing Furniture: Expecting </Furniture>, got "
							+ scan.next());
		}
	}

	/**
	 * Parser for Equipment, uses the fields parsed in earlier and parses in
	 * fields unique to Equipment.
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @return
	 * @throws ParserError
	 */
	private static Item parseEquipment(Scanner scan, Position pos, int height,
			String name, int ID) throws ParserError {
		int attack = parseInt(scan, "Attack");
		int defence = parseInt(scan, "Defence");
		int worth = parseInt(scan, "Worth");
		int slot = parseInt(scan, "Slot");
		if (!gobble(scan, "</Equipment>")) {
			throw new ParserError(
					"Parsing Equipment: Expecting </Equipment>, got "
							+ scan.next());
		}
		return new Equipment(pos, height, ID, name, attack, defence, worth,
				slot);

	}

	/**
	 * Parser for Container, uses the fields parsed in earlier and parses in
	 * fields unique to Container.
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @param world
	 * @return
	 * @throws ParserError
	 */
	private static Item parseContainer(Scanner scan, Position pos, int height,
			String name, int ID, GameWorld world) throws ParserError {
		int cats = parseInt(scan, "Cats");
		Container tempContainer = new Container(pos, height, ID, name, cats);
		List<MoveableItem> loot = new ArrayList<MoveableItem>();
		while (!gobble(scan, "</Container>")) {
			MoveableItem temp = parseStoredItem(scan, world);
			loot.add(temp);
		}
		tempContainer.setLoot(loot);
		return tempContainer;
	}

	/**
	 * Parser for doors, uses the fields parsed in earlier and parses in fields
	 * unique to doors.
	 *
	 * @param scan
	 * @param pos
	 * @param height
	 * @param name
	 * @param ID
	 * @return
	 * @throws ParserError
	 */
	private static Item parseDoor(Scanner scan, Position pos, int height,
			String name, int ID) throws ParserError {
		int areaID = parseInt(scan, "AreaID");
		int x = parseInt(scan, "XPos");
		int y = parseInt(scan, "YPos");
		Position transport = new Position(x, y);
		boolean keyRequired = parseBoolean(scan, "KeyRequired");

		if (!gobble(scan, "</Door>")) {
			throw new ParserError("Parsing Door: Expecting </Door>, got"
					+ scan.next());
		}
		return new Door(pos, height, ID, name, null, areaID, transport,
				keyRequired);
	}

	/**
	 * helper method for ItemParser, parses in ints using the type and returns
	 * the parsed in int
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
	 * helper for itemParser, parses in strings using their type
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
	 * helper for ItemParser, parses in booleans using their type
	 *
	 * @param scan
	 * @param type
	 * @return
	 * @throws ParserError
	 */
	private static boolean parseBoolean(Scanner scan, String type)
			throws ParserError {
		if (!gobble(scan, "<" + type + ">")) {
			throw new ParserError("Parsing Boolean: Expecting <" + type
					+ ">, got " + scan.next());
		}
		boolean value = scan.nextBoolean();

		if (!gobble(scan, "</" + type + ">")) {
			throw new ParserError("Parsing Boolean: Expecting </" + type
					+ ">, got " + scan.next());
		}
		return value;
	}

	/**
	 * gobble helper method, checks if the next String in scan is equal to s and
	 * consumes it if it is and returns the result
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
