package game;

import game.control.Client;
import game.control.Server;
import game.loading.AreaParser;
import game.loading.CharacterParser;
import game.loading.ItemParser;
import game.loading.ParserError;
import game.ui.GameFrame;
import game.world.Area;
import game.world.GameWorld;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

public class Main {

	/** Constants used internally in the Main class. */
	private static final String IMAGE_PATH = "src" + File.separatorChar
			+ "game" + File.separatorChar + "ui" + File.separatorChar
			+ "images";
	private static final String AREA_FILE = "src" + File.separatorChar + "game"
			+ File.separatorChar + "loading" + File.separatorChar + "Areas.xml";
	private static final String ITEMS_FILE = "src" + File.separatorChar
			+ "game" + File.separatorChar + "loading" + File.separatorChar
			+ "Items1.xml";
	private static final String CHARACTER_FILE = "src" + File.separatorChar
			+ "game" + File.separatorChar + "loading" + File.separatorChar
			+ "characters.xml";

	/** Game mode: client, server, or client and server. */
	private static GameMode mode = GameMode.CLIENTANDSERVER;
	private static int connectPort = 0;
	private static String clientConnectAddr = null;

	/** Client and server, which control networked communication. */
	private static Server server = null;
	private static Client client = null;

	/** area which gets added to the game world. */
	private static Area area = null;
	private static Map<Integer, Area> areaMap = null;

	/** Game world and game window. */
	private static GameWorld gameWorld = null;
	private static GameFrame gameWindow = null;
	/** Is this game set up properly? */
	private static Boolean gameWorldSetUp = false;

	/**
	 * Quick'n'dirty processing of command line arguments.
	 *
	 * @param args
	 *            Command-line arguments
	 * @return true if operation succeeded
	 */
	private static boolean processArgs(String[] args) {
		if (args == null)
			return false;

		// Process command line arguments.
		for (int i = 0; i < args.length; i++) {
			String a = args[i];

			// This instance is a server program.
			if (a.equals("--server") || a.equals("-s"))
				mode = GameMode.SERVER;
			// This instance is a client program.
			else if (a.equals("--client") || a.equals("-c")
					|| a.equals("--join") || a.equals("-j"))
				mode = GameMode.CLIENT;
			// Get the port to connect to.
			else if (connectPort == 0
					&& i > 0
					&& (args[i - 1].equals("--port") || args[i - 1]
							.equals("--p")) && a.matches("\\d+"))
				connectPort = Integer.parseInt(a);
			// Get address the client is to connect to.
			else if (mode == GameMode.CLIENT
					&& (args[i - 1].equals("--join") || args[i - 1]
							.equals("--j")) && clientConnectAddr == null)
				clientConnectAddr = a;
			// Ignore. Processing is done later.
			else if (a.equals("--port") || a.equals("-p"))
				;
			else {
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
		// Return true if it is already set up.
		synchronized (gameWorldSetUp) {
			if (gameWorldSetUp)
				return true;
			try {
				areaMap = AreaParser.parseAreas(AREA_FILE);
				area = areaMap.get(1);

				// Add the main area to the game world, but only if the area
				// successfully loaded.
				if (!areaMap.isEmpty()) {
					gameWorld = new GameWorld();
					gameWorld.addAreas(areaMap);
					ItemParser.parseItemList(ITEMS_FILE, area, gameWorld);
					//CharacterParser.parseCharacters(CHARACTER_FILE, area,
							//gameWorld);
				} else {
					System.err
							.println("main: ERROR: could not load game world area");
					return false;
				}

				gameWorldSetUp = true;
				return true;
			} catch (ParserError e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return false;
	}

	/**
	 * Set up the server, binding to a specific TCP port.
	 *
	 * @param port
	 *            port to listen on
	 * @return true if operation succeeded
	 */
	private static boolean setupServer(int port) {
		// Set up the game world if it has not been set up already.
		setupGameWorld();

		// Create the server, and add the game world as a listener for game
		// events.
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
	 * @param addr
	 *            Address to connect to
	 * @param port
	 *            Port to connect to
	 * @return true if operation succeeded
	 */
	private static boolean setupClient(String addr, int port) {
		// Set up the game world if it has not been set up already.
		setupGameWorld();

		// Create a new client object, and make this client listen to game
		// events from the GUI.
		client = new Client();

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

		if (ret && server == null)
			ret = setupServer(0);

		if (ret && client == null)
			ret = setupClient(null, 0);

		return ret;
	}

	/**
	 * Set up the GUI.
	 */
	private static boolean setupGameWindow() {
		// Set up the game world if it has not been set up already.
		setupGameWorld();

		// Set up the game GUI.
		gameWindow = new GameFrame(800, 600);

		// Render the area.
		gameWindow.getRender().setArea(area);
		gameWindow.getRender().repaint();

		// Add the client as a game event listener to this game window.
		gameWindow.addGameEventListener(client);
		// Only do this if the brown stuff hits the fan!
		// gameWindow.addGameEventListener(gameWorld);

		return true;
	}

	/**
	 * Get an Image instance for a given file name.
	 *
	 * @param filename
	 *            File name
	 * @return Image
	 */
	public static Image getImage(String filename) {
		try {
			Image image = ImageIO.read(new File(IMAGE_PATH + File.separatorChar
					+ filename));
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
	 * Return the client for this game.
	 *
	 * @return Server
	 */
	public static Client getClient() {
		return client;
	}

	/**
	 * Return the server for this game.
	 *
	 * @return Server
	 */
	public static Server getServer() {
		return server;
	}

	/**
	 * Start the game, with added command-line arguments.
	 *
	 * @param args
	 *            Command-line arguments
	 */
	public static void main(String args[]) {
		if (!processArgs(args))
			System.exit(1);

		if (!setupGameWorld())
			System.exit(2);

		// Do different actions, depending on the given arguments.
		switch (mode) {
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
	 * Quick'n'dirty private enum determining what kind of instance this game
	 * is.
	 *
	 * @author dickincall
	 *
	 */
	private enum GameMode {
		CLIENT, SERVER, CLIENTANDSERVER
	}
}