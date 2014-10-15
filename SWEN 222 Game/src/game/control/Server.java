package game.control;

import game.Main;
import game.exceptions.ErrPacketException;
import game.exceptions.GameException;
import game.net.GamePacket;
import game.net.packets.HelloPacket;
import game.net.packets.JoinPacket;
import game.net.packets.PlayerPacket;
import game.world.GameEvent;
import game.world.GameEventBroadcaster;
import game.world.GameEventListener;
import game.world.characters.Player;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Accept connections from clients to allow them to access the game world.
 * Update everybody when new events happen.
 *
 * @author Callum
 *
 */
public class Server extends Thread {
	private static final int SERVER_SOCKET_TIMEOUT = 50; // 50ms
	private static final int MAIN_LOOP_COOLDOWN = 50; // 50ms
	
	/** The default port the server listens on, if none is specified. */
	public static final int DEFAULT_PORT = 19642;

	/* The actual port the server will use. */
	private int port = DEFAULT_PORT;

	/* Controls whether or not the server is closing down. */
	private boolean closing = false;

	private ServerSocket serverSocket = null;
	private List<ServerConnection> serverConnections = new ArrayList<ServerConnection>();

	private GameEventBroadcaster serverBroadcaster = new GameEventBroadcaster();

	/**
	 * Private helper method for the constructors, to set up the server socket
	 * with a given port number. Can only be applied if the server is not running.
	 *
	 * @param p Port number to listen on
	 * @return True if the port was changed
	 */
	public boolean setPort(int p) {
		if (serverSocket == null) {
			port = p;
			return true;
		}

		return true;
	}

	/**
	 * Bind the server socket.
	 *
	 * @return True if binding succeeded
	 */
	public boolean bind() {
		try {
			if (serverSocket == null) {
				serverSocket = new ServerSocket(port);
				System.out.println("server: binded a socket to port " + port);
				return true;
			}
		}
		catch (BindException e) {
			System.err.println("server: unable to bind server socket: " + e.getMessage());
		}
		catch (IOException e) {
			System.err.println("server: unhandled, unknown IOException");
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Start the main server loop.
	 */
	public void run() {
		// Return if the server socket didn't bind.
		if (serverSocket == null)
			return;

		// Enter the main loop, accepting new connections from clients
		// as they come.
		try {
			while (!closing) {
				Socket clientSocket = null;
				
				// Try to accept a new client connection.
				while (clientSocket == null) {
					// If the closing state has changed since we
					// acquired a lock on serverSocket, break out of the loop.
					if (closing)
						return;
						
					try {
						synchronized (serverSocket) {
							serverSocket.setSoTimeout(SERVER_SOCKET_TIMEOUT);
							clientSocket = serverSocket.accept();
						}
					}
					catch (SocketTimeoutException e) {
						// Sleep the current thread, to give other threads
						// the opportunity to lock the socket for sending packets.
						try {
							Thread.sleep(MAIN_LOOP_COOLDOWN);
						}
						catch (InterruptedException e1) {
						}
					}
				}
				
				// Create a new ServerConnection for the client socket.
				// This will manage the connection in a separate thread.
				ServerConnection sc = new ServerConnection(clientSocket);

				// When the server connection is constructed, it will automatically
				// get added to the list of server connections, so just start it.
				sc.start();
			}
		}
		catch (IOException e) {
			System.err.println("server: unhandled, unknown IOException");
			e.printStackTrace();
			close();
		}

		// We have exited the main server loop. This only happens when
		// someone calls close() on the server.
	}

	/**
	 * Close all client connections to the server, and stop listening
	 * for new connections. The server cannot be used after this.
	 */
	public void close() {
		closing = true;
		
		synchronized (serverSocket) {
			// Stop accepting new connections, by closing the server socket.
			try {
				serverSocket.close();
			}
			catch (IOException e) {
				System.err.println("server: unhandled, unknown IOException");
				e.printStackTrace();
			}
		}

		// Close all client connections.
		synchronized (serverConnections) {
			Iterator<ServerConnection> it = serverConnections.iterator();

			while (it.hasNext()) {
				ServerConnection sc = it.next();
				sc.close();

				it.remove();
			}
		}
	}

	public void addGameEventListener(GameEventListener gel) {
		serverBroadcaster.addGameEventListener(gel);
	}

	/**
	 * A private helper class which separates direct communication with
	 * clients from the main server thread.
	 *
	 * @author Callum
	 *
	 */
	private class ServerConnection extends NetIOController implements GameEventListener {
		private final Socket clientSocket;

		public ServerConnection(Socket cs) {
			if (cs == null)
				throw new IllegalArgumentException();

			// Associate the given client socket with this server connection.
			clientSocket = cs;
			
			// Add this server connection to the list of server connections,
			// and add the other server connections as GameEventListeners.
			synchronized (serverConnections) {
				serverConnections.add(this);
			}
			
			// Send a HELLO packet to the client to test communications.
			try {
				write(clientSocket, new GamePacket(GamePacket.Type.HELLO, new HelloPacket()));
			}
			catch (ErrPacketException e) {
				System.out.println("server: received unrecoverable ERR packet from client upon HELLO, closing connection");
				close();
			}
			catch (GameException e) {
				// It won't throw this, but if it does, we want to know...
				e.printStackTrace();
			}
		}

		public void run() {
			System.out.println("server: accepted connection from " + clientSocket.getInetAddress());
			System.out.println("server: reading packet from client...");
			while (!isClosing()) {
				GamePacket gp = read(clientSocket);
				
				if (gp != null) {
					System.out.println("server: read GamePacket from " + clientSocket.getInetAddress());
					
					switch (gp.getType()) {
						// We have received a game event from a client.
						case EVENT:
							GameEvent ge = (GameEvent)gp.getPayload();

							System.out.println("server: GameEvent type is: " + ge.getType());

							// Update the game world.
							try {
								serverBroadcaster.broadcastGameEvent(ge);
							}
							catch (GameException e) {
								System.err.println("client: unexpected GameException");
								e.printStackTrace();
							}

							// Send the updated game state to the rest of the clients.
							// Not the best solution for this since it doesn't use
							// a GameEventBroadcaster call, but I don't want to make
							// GameEventBroadcaster too complicated by having it broadcast
							// on an array that is being changed outside its scope.
							synchronized (serverConnections) {
								for (ServerConnection sc: serverConnections) {
									if (sc != this) {
										try {
											sc.gameEventOccurred(ge);
										}
										catch (GameException e) {
											System.err.println("server: unexpected GameException when broadcasting game event");
											e.printStackTrace();
										}
									}
								}
							}
							break;
						case JOIN:
							JoinPacket jp = (JoinPacket)gp.getPayload();
							
							// Create a new player for the joining client.
							Player player = new Player(jp.getPosition(),
									jp.getName(),
									Main.getGameWorld().getNextPlayerID(),
									jp.getPlayerClass());
							
							// Add this player to the game world.
							Main.getGameWorld().addPlayer(player);
							
							// Broadcast this change to all clients.
							synchronized (serverConnections) {
								for (ServerConnection sc: serverConnections) {
									if (sc != this) {
										try {
											sc.write(sc.clientSocket, gp);
										}
										catch (GameException e) {
											System.err.println("server: unexpected GameException when broadcasting game event");
											e.printStackTrace();
										}
									}
								}
							}
							
							// Send the client their player number.
							try {
								write(clientSocket, new GamePacket(GamePacket.Type.PLAYER, new PlayerPacket(player.getId())));
							} catch (GameException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case QUIT:
							System.out.println("server: read QUIT packet, closing connection");
							close();
							break;
						default:
							break;
					}
				}
			}
		}
		
		public void gameEventOccurred(GameEvent ge) throws GameException {
			System.out.println("server: sending a GameEvent of type " + ge.getType() + " to the client");
			write(clientSocket, new GamePacket(GamePacket.Type.EVENT, ge));
		}

		public void close() {
			close(clientSocket);
		}
	}
}
