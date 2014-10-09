package game.loading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import game.world.Area;
import game.world.Position;
import game.world.items.Container;
import game.world.items.Equipment;
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

	public static void parseItems(String filename, Area area) {
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
				area.addItem(parseItemType(scan));
			}
		} catch (ParserError error) {
			error.printStackTrace();
		}
	}

	private static Item parseItemType(Scanner scan) {
		try {
			int x = parseInt(scan, "XPos");
			int y = parseInt(scan, "YPos");
			int height = parseInt(scan, "Height");
			Position pos = new Position(x, y);
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
				int cats = parseInt(scan, "Cats");
				Container tempContainer = new Container(pos, height, name, cats);
				List<MoveableItem> loot = new ArrayList<MoveableItem>();
				while (!gobble(scan, "</Container>")) {
					Item tempItem = parseItemType(scan);
					if (tempItem instanceof MoveableItem) {
						loot.add((MoveableItem) tempItem);
					} else {
						throw new ParserError(
								"Parsing ItemType: Expecting a moveable Item, got"
										+ tempItem.getClass());
					}
				}
				tempContainer.setLoot(loot);
				return tempContainer;
			}
			if (gobble(scan, "<Equipment>")) {
				int attack = parseInt(scan, "Attack");
				int defence = parseInt(scan, "Defence");
				int worth = parseInt(scan, "Worth");
				int slot = parseInt(scan, "Slot");
				if (!gobble(scan, "</Equipment>")) {
					throw new ParserError(
							"Parsing ItemType: Expecting </Equipment>, got "
									+ scan.next());
				}
				return new Equipment(pos, height, name, attack, defence, worth,
						slot);
			}
			if (gobble(scan, "<Furniture>")) {

			}
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
