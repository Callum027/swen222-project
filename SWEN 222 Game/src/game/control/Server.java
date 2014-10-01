package game.control;

import game.util.GamePacket;
import game.world.GameWorld;

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
	
	/* Controls whether or not the server is closing down. */
	private Boolean closing = new Boolean(false);
	
	private GameWorld world;
	private ServerSocket serverSocket = null;
	private List<ServerConnection> serverConnections = new ArrayList<ServerConnection>();
	
	/**
	 * Open a server on the default port, with the given GameWorld.
	 * 
	 * @param w GameWorld to open a server for
	 */
	public Server(GameWorld w) {
		world = w;
	}

	/*
	 * Private helper method for the constructors, to set up the server socket
	 * with a given port number.
	 * 
	 * @param port Port number to listen on
	 */
	public boolean setPort(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (BindException e) {
			System.err.println("server: unable to bind server socket: " + e.getMessage());
			return false;
		} catch (IOException e) {
			System.err.println("server: unhandled, unknown IOException");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Start the main server loop.
	 */
	public void run() {
		// Don't run if the server socket hasn't been binded yet.
		if (serverSocket == null)
			return;
			
		// Enter the main loop, accepting new connections from clients
		// as they come.
		synchronized (closing) {
			while (!closing.booleanValue()) {
				try {
					Socket clientSocket = serverSocket.accept();
					ServerConnection sc = new ServerConnection(clientSocket);
	
					serverConnections.add(sc);
					sc.start();
				}
				catch (IOException e) {
					System.err.println("server: unhandled, unknown IOException");
					e.printStackTrace();
					close();
				}
			}
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
		} catch (IOException e) {
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
				
				synchronized (closing) {
					while (!closing.booleanValue()) {
						// Read a new GamePacket from the client.
						GamePacket gp = GamePacket.read(is);
						
						System.out.println("server: GamePacket read... type: " + gp.getType());
						
						// Update the game world.
						
						// Send the updated game state to the rest of the clients.
					}
				}
				
				// Exited the server connection loop, which means
				// we have to close the client socket.
				clientSocket.close();
			} catch (SocketException e) {
				System.err.println("server: closing connection: " + e.getMessage());
				close();
			} catch (IOException e) {
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
