package game.loading;

import game.Main;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ItemParser {
	public static void parseItemList(String filename, Area area, GameWorld world)
			throws ParserError {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (!gobble(scan, "<ItemList>")) {
				throw new ParserError(
						"Parsing Items: Expecting <ItemList>, got "
								+ scan.next());
			}
			while (!gobble(scan, "</ItemList>")) {
				if (!gobble(scan, "<Item>")) {
					throw new ParserError(
							"Parsing Items: Expecting <Item>, got "
									+ scan.next());
				}
				area.addItem(parseItem(scan, world));
				if (!gobble(scan, "</Item>")) {
					throw new ParserError(
							"Parsing Items: Expecting </Item>, got "
									+ scan.next());
				}
			}
			scan.close();
		} catch (ParserError error) {
			error.printStackTrace();
			throw error;
		}
	}

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

	private static MoveableItem parseStoredItem(Scanner scan, GameWorld world)
			throws ParserError {
		int height = parseInt(scan, "Height");
		int ID = world.getNextItemID();
		String name = parseString(scan, "Name");
		int worth = parseInt(scan, "Worth");

		return new MoveableItem(new Position(-1, -1), height, ID, name, worth);
	}

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

	private static Item parseContainer(Scanner scan, Position pos, int height,
			String name, int ID, GameWorld world) throws ParserError {
		int cats = parseInt(scan, "Cats");
		Container tempContainer = new Container(pos, height, ID, name, cats);
		List<MoveableItem> loot = new ArrayList<MoveableItem>();
		while (!gobble(scan, "</Container>")) {
			loot.add(parseStoredItem(scan, world));
		}
		tempContainer.setLoot(loot);
		return tempContainer;
	}

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

	private static boolean gobble(Scanner scan, String s) {
		if (scan.hasNext(s)) {
			scan.next();
			return true;
		}
		return false;
	}
}
