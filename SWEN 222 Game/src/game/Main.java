package game;

import java.awt.Cursor;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import game.ui.*;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

public class Main {

	private GameFrame gameWindow;
	private static final String IMAGE_PATH = "ui/graphics/";

	public Main() {
		File tiles = new File("tiles.txt");
		Map<Integer, Tile> tileMap = createTileMap(tiles);
		gameWindow = new GameFrame(1280, 720, Cursor.getDefaultCursor());
	}

	/**
	 * Creates a tile map which maps a unique integer value to a
	 * unique tile, to be used in rendering the game world.
	 *
	 * The file format is:
	 *    ID, TileType, Image_Name.png
	 *
	 * Where commas separate the values. ID is the integer value
	 * which maps to the tile, the TileType is either "Floor" or
	 * "Wall" and Image_Name.png is the file name of the image
	 * representing the tile.
	 *
	 * @param file
	 * 			--- contains data about the tiles
	 * @return
	 * 			--- mapping of integers to tiles
	 */
	public Map<Integer, Tile> createTileMap(File file){
		Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
		try{
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				String[] line = scan.nextLine().split(", ");
				int id = Integer.parseInt(line[0]);
				Image image = getImage(line[2]);
				Tile tile = null;
				if(line[1].equals("Floor")){
					tile = new FloorTile(image);
				}
				else if(line[1].equals("Wall")){
					tile = new WallTile(image);
				}
				tileMap.put(id, tile);
			}
			scan.close();
		}catch(IOException e){System.out.println(e);}
		return tileMap;
	}

	public static Image getImage(String filename){
		java.net.URL imageURL = Main.class.getResource(IMAGE_PATH+filename);
		try{
			Image image = ImageIO.read(imageURL);
			return image;
		}catch(IOException e){throw new RuntimeException("Unable to locate "+filename);}
	}

	public static void main(String arr[]) {
		new Main();
	}
}
