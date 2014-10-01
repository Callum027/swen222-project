package game;

import java.awt.Cursor;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import game.control.Player;
import game.loading.Parser;
import game.ui.*;
import game.world.Area;
import game.world.GameWorld;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

public class Main {

	// temporary while we are waiting for Chris to do data storage
	private String[] tilesFile = new String[] { "1, FloorTile, floor_tile3.png" };
//	private String[] areaFile = new String[] { "10, 10",
//			"1, 1, 1, 1, 1, 1, 1, 1, 1, 1", "1, 1, 1, 1, 1, 1, 1, 1, 1, 1",
//			"1, 1, 1, 1, 1, 1, 1, 1, 1, 1", "1, 1, 1, 1, 1, 1, 1, 1, 1, 1",
//			"1, 1, 1, 1, 1, 1, 1, 1, 1, 1", "1, 1, 1, 1, 1, 1, 1, 1, 1, 1",
//			"1, 1, 1, 1, 1, 1, 1, 1, 1, 1", "1, 1, 1, 1, 1, 1, 1, 1, 1, 1",
//			"1, 1, 1, 1, 1, 1, 1, 1, 1, 1", "1, 1, 1, 1, 1, 1, 1, 1, 1, 1" };
	private String areaFile = "Area.xml";
	private GameFrame gameWindow;
	private static final String IMAGE_PATH = "ui/graphics/";

	public Main() {
		Map<Integer, Tile> tileMap = createTileMap(tilesFile);
		gameWindow = new GameFrame(1280, 720, Cursor.getDefaultCursor());
		Area area = Parser.parseArea(areaFile, tileMap);
		GameWorld gameWorld = new GameWorld();
		gameWorld.addArea(area);
		gameWindow.getRender().setArea(area);
		gameWindow.getRender().repaint();
	}

	public File createFile(String[] data, String pathname) {
		File file = new File(pathname);
		try {
			@SuppressWarnings("resource")
			PrintWriter write = new PrintWriter(file);
			for (int i = 0; i < data.length; i++) {
				write.println(data[i]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Creates a tile map which maps a unique integer value to a unique tile, to
	 * be used in rendering the game world.
	 *
	 * The file format is: ID, TileType, Image_Name.png
	 *
	 * Where commas separate the values. ID is the integer value which maps to
	 * the tile, the TileType is either "Floor" or "Wall" and Image_Name.png is
	 * the file name of the image representing the tile.
	 *
	 * @param file
	 *            --- contains data about the tiles
	 * @return --- mapping of integers to tiles
	 */
	public Map<Integer, Tile> createTileMap(String[] data) {
		Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
		for (int i = 0; i < data.length; i++) {
			String[] line = data[i].split(", ");
			int id = Integer.parseInt(line[0]);
			Image image = getImage(line[2]);
			Tile tile = null;
			if (line[1].equals("FloorTile")) {
				tile = new FloorTile(image);
			} else if (line[1].equals("WallTile")) {
				tile = new WallTile(image);
			}
			tileMap.put(id, tile);
		}
		return tileMap;
	}

	public static Image getImage(String filename) {
		java.net.URL imageURL = Main.class.getResource(IMAGE_PATH + filename);
		try {
			Image image = ImageIO.read(imageURL);
			return image;
		} catch (IOException e) {
			throw new RuntimeException("Unable to locate " + filename);
		}
	}

	public static void main(String arr[]) {
		new Main();
	}
}
