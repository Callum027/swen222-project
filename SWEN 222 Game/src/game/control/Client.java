package game.control;

import game.Main;
import game.exceptions.ErrPacketException;
import game.exceptions.GameException;
import game.net.GamePacket;
import game.net.packets.JoinPacket;
import game.net.packets.PlayerPacket;
import game.world.GameEvent;
import game.world.GameEventBroadcaster;
import game.world.GameEventListener;
import game.world.Position;
import game.world.characters.Player;
import game.world.characters.classes.GameClass;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Connects with a game server to allow access to the game world.
 * Not just with a local machine, but practically anywhere, as long
 * as they can establish a reliable TCP connection!
 *
 * @author Callum
 *
 */
public class Client extends NetIOController implements GameEventListener {
	private boolean gettingPlayerID = false;
	private int playerID = -1;
	private Socket socket = null;
	public GameEventBroadcaster geb = new GameEventBroadcaster();

	/**
	 * Connect to the local host on the default port.
	 *
	 * @return true if connected, false otherwise
	 */
	public boolean connect() {
		return connect(null, Server.DEFAULT_PORT);
	}

	/**
	 * Connect to the local host on a specific port.
	 *
	 * @param port Port to connect on
	 * @return true if connected, false otherwise
	 */
	public boolean connect(int port) {
		return connect(null, port);
	}

	/**
	 * Connect to a remote host on the default port.
	 *
	 * @param host Host to connect to
	 * @return true if connected, false otherwise
	 */
	public boolean connect(String host) {
		return connect(host, Server.DEFAULT_PORT);
	}

	/**
	 * Connect to a specific host and port, and return whether it succeeded or not.
	 *
	 * @param host Host to connect to
	 * @param port Port to connect on
	 * @return true if connected, false otherwise
	 */
	public boolean connect(String host, int port) {
		if (socket != null)
			return false;

		try {
			socket = new Socket(InetAddress.getByName(host), port);
			System.out.println("client: connected to " + socket.getInetAddress());
			return true;
		}
		catch (UnknownHostException e) {
			System.err.println("client: error looking up host: unknown host");
			close(socket);
		}
		catch (ConnectException e) {
			System.err.println("client: error connecting: " + e.getMessage());
			close(socket);
		}
		catch (IOException e) {
			System.err.println("client: unhandled, unknown IOException");
			e.printStackTrace();
			close(socket);
		}

		socket = null;
		return false;
	}

	/**
	 * Start the client thread main loop.
	 */
	public void run() {
		// If they haven't run connect() yet or the connection was unsuccessful,
		// don't run the main loop.
		if (socket == null)
			return;

		while (!isClosing())
		{
			GamePacket gp = read(socket);

			if (gp != null) {
				switch (gp.getType()) {
					case EVENT:
						System.out.println("client: broadcasting game event to listeners");
						try {
							geb.broadcastGameEvent((GameEvent)gp.getPayload());
						}
						catch (GameException e) {
							System.err.println("client: unexpected GameException");
							e.printStackTrace();
						}
						break;
					case JOIN:
						System.out.println("client: adding player to local game world");
						JoinPacket jp = (JoinPacket)gp.getPayload();
						
						// Create a new player for the joining client.
						Player player = new Player(jp.getPosition(),
								jp.getName(),
								Main.getGameWorld().getNextPlayerID(),
								jp.getPlayerClass());
						
						Main.getGameWorld().getArea(jp.getAreaID()).addPlayer(player);
						break;
					case PLAYER:
						System.out.println("client: getting our local player ID");
						if (gettingPlayerID) {
							PlayerPacket pp = (PlayerPacket)gp.getPayload();
							playerID = pp.getID();
							gettingPlayerID = false;
						}
						break;
					case QUIT:
						System.out.println("client: read QUIT packet, closing connection");
						close();
						break;
					default:
						break;
				}
			}
		}
	}
	
	/**
	 * Get the client's player ID. Returns -1 if the client does not have one yet.
	 * @return
	 */
	public int getPlayerID() {
		while (gettingPlayerID);
		return playerID;
	}
	
	/**
	 * Join the current game run by the server, with the given parameters for the
	 * new player.
	 * 
	 * @param position Position to spawn at
	 * @param name Player name
	 * @param playerClass Player class
	 * @throws GameException
	 */
	public void join(int areaID, Position position, String name, GameClass.CharacterClass playerClass) throws GameException {
		if (!gettingPlayerID && playerID < 0) {
			gettingPlayerID = true;
			
			try {
				write(socket, new GamePacket(GamePacket.Type.JOIN, new JoinPacket(areaID, position, name, playerClass)));
			}
			catch (ErrPacketException e) {
				throw e;
			}
		}
	}

	/**
	 * Update the server when a game events happens.
	 */
	public void gameEventOccurred(GameEvent ge) throws GameException {
		System.out.println("client: sending a GameEvent of type " + ge.getType() + " to the server");
		write(socket, new GamePacket(GamePacket.Type.EVENT, ge));
		System.out.println("client: done sending");
	}

	/**
	 * Add a game event listener to this client.
	 *
	 * @param gel Game event listener
	 */
	public void addGameEventListener(GameEventListener gel)
	{
		geb.addGameEventListener(gel);
	}

	@Override
	public void close() {
		close(socket);
	}
}
