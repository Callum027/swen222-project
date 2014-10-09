package game;

import game.control.Client;
import game.control.Server;
import game.loading.GameParser;
import game.ui.GameFrame;
import game.world.Area;
import game.world.GameWorld;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Main {


	private static final String IMAGE_PATH = "src" + File.separatorChar + "game" + File.separatorChar + "ui" + File.separatorChar + "images";
	private static String areaFile = "src" + File.separatorChar + "game" + File.separatorChar + "loading" + File.separatorChar + "Area.xml";

	private static String[] tilesFile = new String[] { "1, FloorTile, floor_tile3" };

	/* Game mode: client, server, or client and server. */
	private static GameMode mode = GameMode.CLIENTANDSERVER;
	private static int connectPort = 0;
	private static String clientConnectAddr = null;

	/* Client and server, which control networked communication. */
	private static Server server = null;
	private static Client client = null;

	private static Map<Integer, Tile> tileMap = createTileMap(tilesFile);
	private static Area area = GameParser.parseArea(areaFile, tileMap);

	private static GameWorld gameWorld = new GameWorld();
	private static GameFrame gameWindow = new GameFrame(1280, 720);

	/**
	 * Quick'n'dirty processing of command line arguments.
	 *
	 * @param args Command-line arguments
	 * @return true if operation succeeded
	 */
	private static boolean setupGameMode(String[] args) {
		if (args == null)
			return false;

		// Process command line arguments.
		for (int i = 0; i < args.length; i++) {
			String a = args[i];

			// This instance is a server program.
			if (a.equals("--server") || a.equals("-s"))
				mode = GameMode.SERVER;
			// This instance is a client program.
			else if (a.equals("--client") || a.equals("-c") || a.equals("--join") || a.equals("-j"))
				mode = GameMode.CLIENT;
			// Get the port to connect to.
			else if (connectPort == 0 && i > 0 && (args[i-1].equals("--port") || args[i-1].equals("--p")) && a.matches("\\d+"))
				connectPort = Integer.parseInt(a);
			// Get address the client is to connect to.
			else if (mode == GameMode.CLIENT && (args[i-1].equals("--join") || args[i-1].equals("--j")) && clientConnectAddr == null)
				clientConnectAddr = a;
			// Ignore. Processing is done later.
			else if (a.equals("--port") || a.equals("-p"));
			else
			{
				System.err.println("main: invalid argument: " + a);
				return false;
			}
		}

		return true;
	}

	/**
	 * Set up the game world.
	 *
	 * @return true if operation succeeded
	 */
	private static boolean setupGameWorld() {
		// Add the main area to the game world.
		if (area != null)
			gameWorld.addArea(area);
		else
		{
			System.err.println("main: ERROR: could not load game world area");
			return false;
		}

		return true;
	}

	/**
	 * Set up the server, binding to a specific TCP port.
	 *
	 * @param port port to listen on
	 * @return true if operation succeeded
	 */
	private static boolean setupServer(int port) {
		if (gameWorld == null) {
			System.err.println("main: ERROR: setupServer() called before setupGameWorld()");
			return false;
		}

		// Create the server, and add the game world as a listener for game events.
		server = new Server();
		server.addGameEventListener(gameWorld);

		// Set the port for the server to listen on.
		if (port > 0)
			server.setPort(port);

		// Bind the server to the socket, and start the thread!
		server.bind();
		server.start();

		return true;
	}

	/**
	 * Set up the client, connecting to either the local server or a remote one.
	 *
	 * @param addr Address to connect to
	 * @param port Port to connect to
	 * @return true if operation succeeded
	 */
	private static boolean setupClient(String addr, int port) {
		if (gameWorld == null) {
			System.err.println("main: ERROR: setupClient() called before setupGameWorld()");
			return false;
		}

		// Create a new client object, and make this client listen to game events from the GUI.
		client = new Client();
		//gameWindow.addGameEventListener(client);

		// Connect the client to the server.
		if (addr == null && port <= 0)
			client.connect();
		else if (addr == null && port > 0)
			client.connect(port);
		else if (addr != null && port <= 0)
			client.connect(addr);
		else
			client.connect(addr, port);

		// Start the client thread.
		client.start();

		return true;
	}

	/**
	 * Set up a dedicated server instance.
	 *
	 * @return true if operation succeeded
	 */
	private static boolean setupDedicatedServer() {
		return setupServer(connectPort);
	}

	/**
	 * Set up a dedicated client instance.
	 */
	private static boolean setupDedicatedClient() {
		return setupClient(clientConnectAddr, connectPort);
	}

	/**
	 * Set up a combined client-and-server combo.
	 */
	private static boolean setupClientAndServer() {
		boolean ret = true;

		if (ret && server != null)
			ret = setupServer(0);

		if (ret && client == null)
			ret = setupClient(null, 0);

		return ret;
	}

	/**
	 * Set up the GUI.
	 */
	private static boolean setupGameWindow() {
		// Render the area.
		gameWindow.getRender().setArea(area);
		gameWindow.getRender().repaint();

		return true;
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
	public static Map<Integer, Tile> createTileMap(String[] data) {
		Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();
		for (int i = 0; i < data.length; i++) {
			String[] line = data[i].split(", ");
			int id = Integer.parseInt(line[0]);
			//Image image = getImage(line[2]);
			Tile tile = null;
			if (line[1].equals("FloorTile")) {
				tile = new FloorTile(line[2]);
			} else if (line[1].equals("WallTile")) {
				tile = new WallTile(line[2]);
			}
			tileMap.put(id, tile);
		}
		return tileMap;
	}

	/**
	 * Get an Image instance for a given file name.
	 *
	 * @param filename File name
	 * @return Image
	 */
	public static Image getImage(String filename) {
		try {
			Image image = ImageIO.read(new File(IMAGE_PATH  + File.separatorChar + filename));
			return image;
		} catch (IOException e) {
			System.err.println("main: getImage: " + filename + ": ERROR: " + e);
			return null;
		}
	}

	/**
	 * Return the game world. Used internally by the game logic, so it knows
	 * what world to work on.
	 *
	 * @return game world
	 */
	public static GameWorld getGameWorld() {
		return gameWorld;
	}

	/**
	 * Start the game, with added command-line arguments.
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String args[]) {
		if (!setupGameMode(args))
			System.exit(1);

		if (!setupGameWorld())
			System.exit(2);

		// Do different actions, depending on the given arguments.
		switch (mode)
		{
			// Self-contained client and server, all in one.
			case CLIENTANDSERVER:
				if (!setupClientAndServer())
					System.exit(3);
				break;
			// Dedicated server.
			case SERVER:
				if (!setupDedicatedServer())
					System.exit(4);
				break;
			// Dedicated client, who connects to a remote server.
			case CLIENT:
				if (!setupDedicatedClient())
					System.exit(5);
				break;
		}

		if (!setupGameWindow())
			System.exit(6);
	}

	/**
	 * Quick'n'dirty private enum determining what kind of instance
	 * this game is.
	 *
	 * @author dickincall
	 *
	 */
	private enum GameMode {
		CLIENT,
		SERVER,
		CLIENTANDSERVER
	}
}