package game.control;

import game.exceptions.GameException;
import game.net.GamePacket;
import game.world.GameEvent;
import game.world.GameEventListener;
import game.world.GameState;
import game.world.GameStateBroadcaster;
import game.world.GameStateListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Connects with a game server to allow access to the game world.
 * Not just with a local machine, but practically anywhere, as long
 * as they can establish a reliable TCP connection!
 *
 * @author Callum
 *
 */
public class Client extends Thread implements GameEventListener {
	private Socket socket = null;
	public GameStateBroadcaster gsb = new GameStateBroadcaster();

	private Boolean closing = new Boolean(false);

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
			close();
		}
		catch (ConnectException e) {
			System.err.println("client: error connecting: " + e.getMessage());
			close();
		}
		catch (IOException e) {
			System.err.println("client: unhandled, unknown IOException");
			e.printStackTrace();
			close();
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

		try {
			while (!closing.booleanValue()) {
				try {
					// Get the output stream of the socket.
					InputStream is = socket.getInputStream();

					// Read a game packet from the server.
					GamePacket gp = GamePacket.read(is);

					switch (gp.getType()) {
						// We have received a game state update. Broadcast it to all clients.
						case STATE:
							GameState gs = (GameState)gp.getPayload();
							gsb.broadcastGameState(gs);
							break;
						default:
							break;
					}
				}
				catch (GameException e) {
					System.err.println("client: while reading packet from server: " + e);
				}
			}

			// The client has been told to close the connection.
			// Exit the main loop and close the socket.
			socket.close();
		}
		catch (SocketException e) {
			System.err.println("client: closing connection: " + e.getMessage());
			close();
		}
		catch (IOException e) {
			System.err.println("client: unhandled, unknown IOException");
			e.printStackTrace();
			close();
		}
	}

	/**
	 * Tell the client to close the server connection. This Client
	 * cannot be used after this is called.
	 */
	public void close() {
			closing = new Boolean(true);
			// TODO: Send a close packet to the server
	}

	/**
	 * Update the server when a game events happens.
	 */
	public void gameEventOccurred(GameEvent ge) {
		try {
			if (socket != null && !closing.booleanValue()) {
				System.out.println("client: sending a GameEvent of type " + ge.getType() + " to the server");
				// Pack the game event into a game packet, and send it off!
				new GamePacket(GamePacket.Type.EVENT, ge).write(socket.getOutputStream());
			}
		}
		catch (SocketException e) {
			System.err.println("client: closing connection: " + e.getMessage());
			close();
		}
		catch (IOException e) {
			System.err.println("client: unhandled, unknown IOException");
			e.printStackTrace();
			close();
		}
	}

	/**
	 * Add a game state listener to this client.
	 *
	 * @param gsl Game state listener
	 */
	public void addGameStateListener(GameStateListener gsl)
	{
		gsb.addGameEventListener(gsl);
	}
}
