package game.loading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.world.Area;
import game.world.Position;
import game.world.items.Consumables;
import game.world.items.Container;
import game.world.items.Equipment;
import game.world.items.Furniture;
import game.world.items.Item;
import game.world.items.MoveableItem;
import game.world.tiles.Tile;

public class GameParser {

	public static Area parseArea(String filename, Map<Integer, Tile> tileMap) {
		Scanner scan = null;
		try {
			scan = new Scanner(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			if (!gobble(scan, "<Area>")) {
				throw new ParserError("Parsing Area: Expecting <Area>, got "
						+ scan.next());
			}

			int ID = parseInt(scan, "ID");
			int width = parseInt(scan, "Width");
			int height = parseInt(scan, "Height");
			Tile[][] floor = parse2DTileArray(scan, "Floor", width, height,
					tileMap);
			Tile[][][] walls = new Tile[4][][];
			walls[0] = parse2DTileArray(scan, "NorthWall", width, 3, tileMap);
			walls[1] = parse2DTileArray(scan, "EastWall", height, 3, tileMap);
			walls[2] = parse2DTileArray(scan, "SouthWall", width, 3, tileMap);
			walls[3] = parse2DTileArray(scan, "WestWall", height, 3, tileMap);

			if (!gobble(scan, "</Area>")) {
				throw new ParserError("Parsing Area: Expecting </Area>, got "
						+ scan.next());
			}
			scan.close();
			return new Area(floor, walls, ID);
		} catch (ParserError error) {
			error.printStackTrace();
		}
		return null;
	}

	public static void parseItemList(String filename, Area area) {
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
				area.addItem(parseItem(scan));
			}
		} catch (ParserError error) {
			error.printStackTrace();
		}
	}

	private static Item parseItem(Scanner scan) {
		try {
			int x = parseInt(scan, "XPos");
			int y = parseInt(scan, "YPos");
			int height = parseInt(scan, "Height");
			Position pos = new Position(x, y);
			int ID = parseInt(scan, "ID");
			if (!gobble(scan, "<Name>")) {
				throw new ParserError(
						"Parsing ItemType: Expecting <Name>, got "
								+ scan.next());
			}
			if (!scan.hasNext()) {
				throw new ParserError(
						"Parsing ItemType: Expected String, got nothing.");
			}
			String name = scan.next();
			if (!gobble(scan, "</Name>")) {
				throw new ParserError(
						"Parsing ItemType: Expecting </Name>, got "
								+ scan.next());
			}

			if (gobble(scan, "<Container>")) {
				return parseContainer(scan, pos, height, name, ID);
			}
			if (gobble(scan, "<Consumable>")) {
				return parseConsumable(scan, pos, height, name, ID);
			}
			if (gobble(scan, "<Equipment>")) {
				return parseEquipment(scan, pos, height, name, ID);
			}
			if (gobble(scan, "<Furniture>")) {
				return parseFurniture(scan, pos, height, name, ID);
			}
			if (gobble(scan, "<MoveableItem>")) {
				return parseMoveableItem(scan, pos, height, name, ID);
			}
		} catch (ParserError error) {
			error.printStackTrace();
		}
		return null;
	}

	private static Item parseConsumable(Scanner scan, Position pos, int height,
			String name, int ID) {
		try {
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
			return new Consumables(pos, height,ID ,name,  worth, buff);
		} catch (ParserError e) {
			e.printStackTrace();
		}

		return null;
	}

	private static MoveableItem parseMoveableItem(Scanner scan, Position pos,
			int height, String name, int ID) {
		try {
			int worth = parseInt(scan, "Worth");
			return new MoveableItem(pos, height, ID, name, worth);
		} catch (ParserError e) {
			e.printStackTrace();
		}

		return null;
	}

	private static MoveableItem parseStoredItem(Scanner scan) {
		try {
			int height = parseInt(scan, "Height");
			int ID = parseInt(scan, "ID");
			if (!gobble(scan, "<Name>")) {
				throw new ParserError(
						"Parsing ItemType: Expecting <Name>, got "
								+ scan.next());
			}
			if (!scan.hasNext()) {
				throw new ParserError(
						"Parsing ItemType: Expected String, got nothing.");
			}
			String name = scan.next();
			if (!gobble(scan, "</Name>")) {
				throw new ParserError(
						"Parsing ItemType: Expecting </Name>, got "
								+ scan.next());
			}
			int worth = parseInt(scan, "Worth");
			return new MoveableItem(new Position(-1, -1), height, ID, name,
					worth);
		} catch (ParserError e) {
			e.printStackTrace();
		}
		return null;

	}

	private static Furniture parseFurniture(Scanner scan, Position pos,
			int height, String name, int ID) {
		try {
			if (!gobble(scan, "<StoredItem>")) {
				throw new ParserError(
						"Parsing Furniture: Expected <StoredItem>, got "
								+ scan.next());
			}
			MoveableItem storedItem = parseStoredItem(scan);
			if (!gobble(scan, "</StoredItem>")) {
				throw new ParserError(
						"Parsing Furniture: Expected </StoredItem>, got "
								+ scan.next());
			}
			return new Furniture(pos, height,ID, name, storedItem);

		} catch (ParserError error) {
			error.printStackTrace();
		}
		return null;
	}

	private static Item parseEquipment(Scanner scan, Position pos, int height,
			String name, int ID) {
		try {
			int attack = parseInt(scan, "Attack");
			int defence = parseInt(scan, "Defence");
			int worth = parseInt(scan, "Worth");
			int slot = parseInt(scan, "Slot");
			if (!gobble(scan, "</Equipment>")) {
				throw new ParserError(
						"Parsing ItemType: Expecting </Equipment>, got "
								+ scan.next());
			}
			return new Equipment(pos, height, ID, name, attack, defence, worth,
					slot);
		} catch (ParserError error) {
			error.printStackTrace();
		}
		return null;
	}

	private static Item parseContainer(Scanner scan, Position pos, int height,
			String name, int ID) {
		try {
			int cats = parseInt(scan, "Cats");
			Container tempContainer = new Container(pos, height,ID, name, cats);
			List<MoveableItem> loot = new ArrayList<MoveableItem>();
			while (!gobble(scan, "</Container>")) {
				loot.add(parseStoredItem(scan));
			}
			tempContainer.setLoot(loot);
			return tempContainer;
		} catch (ParserError error) {
			error.printStackTrace();
		}
		return null;
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

	private static Tile[][] parse2DTileArray(Scanner scan, String type,
			int width, int height, Map<Integer, Tile> tileMap)
			throws ParserError {
		if (!gobble(scan, "<" + type + ">")) {
			throw new ParserError("Parsing 2D Tile Array: Expecting <" + type
					+ ">, got " + scan.next());
		}
		Tile[][] tiles = new Tile[height][width];
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				int tile = scan.nextInt();
				tiles[i][j] = tileMap.get(tile);
			}
		}

		if (!gobble(scan, "</" + type + ">")) {
			throw new ParserError("Parsing 2D Tile Array: Expecting </" + type
					+ ">, got " + scan.next());
		}

		return tiles;
	}

	private static boolean gobble(Scanner scan, String s) {
		if (scan.hasNext(s)) {
			scan.next();
			return true;
		}
		return false;
	}
}
