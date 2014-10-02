package game.control;

import game.net.GamePacket;
import game.world.GameEvent;
import game.world.GameEventBroadcaster;
import game.world.GameEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
	/** The default port the server listens on, if none is specified. */
	public static final int DEFAULT_PORT = 19642;
	
	/* The actual port the server will use. */
	private int port = DEFAULT_PORT;
	
	/* Controls whether or not the server is closing down. */
	private Boolean closing = new Boolean(false);

	private ServerSocket serverSocket = null;
	private List<ServerConnection> serverConnections = new ArrayList<ServerConnection>();
	
	private GameEventBroadcaster geb = new GameEventBroadcaster();

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
	
	/*
	 * Bind the server socket.
	 * 
	 * @return True if binding succeeded
	 */
	private boolean bind() {
		try {
			if (serverSocket == null)
			{
				serverSocket = new ServerSocket(port);
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
		// Bind the server socket.
		if (!bind())
			return;
			
		// Enter the main loop, accepting new connections from clients
		// as they come.
		try {
			while (!closing.booleanValue()) {
				synchronized (closing) {
					Socket clientSocket = serverSocket.accept();
					ServerConnection sc = new ServerConnection(clientSocket);
	
					serverConnections.add(sc);
					sc.start();
				}
			}
		}
		catch (IOException e) {
			System.err.println("server: unhandled, unknown IOException");
			e.printStackTrace();
			close();
		}
		
		// We have exited the main server loop. This only happens when
		// someone calls close() on the server. So, do what they want:
		// close all client connections, and close the server socket!
		Iterator<ServerConnection> it = serverConnections.iterator();
		
		while (it.hasNext()) {
			ServerConnection sc = it.next();
			sc.close();
			
			it.remove();
		}
		
		try {
			serverSocket.close();
		}
		catch (IOException e) {
			System.err.println("server: unhandled, unknown IOException");
			e.printStackTrace();
		}
	}
	
	/**
	 * Close all client connections to the server, and stop listening
	 * for new connections. The server cannot be used after this.
	 */
	public void close() {
		synchronized (closing) {
			closing = new Boolean(true);
		}
	}
	
	public void addGameEventListener(GameEventListener gel) {
		geb.addGameEventListener(gel);
	}
	
	/*
	 * A private helper class which separates direct communication with
	 * clients from the main server thread.
	 * 
	 * @author Callum
	 *
	 */
	private class ServerConnection extends Thread {
		private Boolean closing = new Boolean(false);
		private final Socket clientSocket;
		
		public ServerConnection(Socket cs) {
			clientSocket = cs;
		}
		
		public void run() {
			try {
				InputStream is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();
				
				while (!closing.booleanValue()) {
					synchronized (closing) {
						// Read a new GamePacket from the client.
						GamePacket gp = GamePacket.read(is);
						
						// Determine the next action according to the contents of the GamePacket.
						switch (gp.getType())
						{
							// We have received a game event from a client.
							case EVENT:
								GameEvent ge = (GameEvent)gp.getPayload();
								
								System.out.println("server: received an EVENT packet from " + clientSocket.getInetAddress());
								System.out.println("        EVENT type: " + ge.getType());
								
								// Update the game world.
								geb.broadcastGameEvent(ge);
								
								// TODO: Send the updated game state to the rest of the clients.
								break;
							default:
								break;
						}
					}
				}
				
				// Exited the server connection loop, which means
				// we have to close the client socket.
				clientSocket.close();
			}
			catch (SocketException e) {
				System.err.println("server: closing connection: " + e.getMessage());
				close();
			}
			catch (IOException e) {
				System.err.println("server: unhandled, unknown IOException");
				e.printStackTrace();
				close();
			}
		}
		
		public void close() {
			synchronized (closing) {
				closing = new Boolean(true);
			}
		}
	}
}
