package game.control;

import game.util.GamePacket;
import game.world.GameWorld;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
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
	private boolean closing;
	
	private GameWorld world;
	private ServerSocket serverSocket;
	private List<ServerConnection> serverConnections;
	
	/**
	 * Open a server on the default port, with the given GameWorld.
	 * 
	 * @param w GameWorld to open a server for
	 */
	public Server(GameWorld w) {
		closing = false;
		world = w;
		serverConnections = new ArrayList<ServerConnection>();
		
		setupServerSocket(DEFAULT_PORT);
	}
	
	/**
	 * Open a server on the specified port, with the given GameWorld.
	 * @param w GameWorld to open a server for
	 * @param port Port to listen on
	 */
	public Server(GameWorld w, int port) {
		closing = false;
		world = w;
		serverConnections = new ArrayList<ServerConnection>();
		
		setupServerSocket(port);
	}
	
	/*
	 * Private helper method for the constructors, to set up the server socket
	 * with a given port number.
	 * 
	 * @param port Port number to listen on
	 */
	private void setupServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the main server loop.
	 */
	public void run() {
		// Enter the main loop, accepting new connections from clients
		// as they come.
		while (!closing) {
			try {
				Socket clientSocket = serverSocket.accept();
				ServerConnection sc = new ServerConnection(clientSocket);

				serverConnections.add(sc);
				sc.start();
			}
			catch (IOException e) {
				e.printStackTrace();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Close all client connections to the server, and stop listening
	 * for new connections. The server cannot be used after this.
	 */
	public void close() {
		closing = true;
	}
	
	/*
	 * A private helper class which separates direct communication with
	 * clients from the main server thread.
	 * 
	 * @author Callum
	 *
	 */
	private class ServerConnection extends Thread {
		private boolean closing;
		private final Socket clientSocket;
		
		public ServerConnection(Socket cs) {
			closing = false;
			clientSocket = cs;
		}
		
		public void run() {
			try {
				InputStream is = clientSocket.getInputStream();
				OutputStream os = clientSocket.getOutputStream();
				
				while (!closing) {
					// Read a new GamePacket from the client.
					GamePacket gp = GamePacket.read(is);
					
					System.out.println("server: GamePacket read... type: " + gp.getType());
					
					// Update the game world.
					
					// Send the updated game state to the rest of the clients.
				}
				
				// Exited the server connection loop, which means
				// we have to close the client socket.
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void close() {
			closing = true;
		}
	}
	
	/**
	 * Test main method to try a connection to a server.
	 * 
	 * @param args Optional arguments, host and port in that order
	 */
	public static void main(String[] args)
	{
		int port  = (args.length > 1) ? Integer.parseInt(args[1]) : Server.DEFAULT_PORT;
		
		// Make a connection to the server.
		Server s = new Server(null, port);
		s.start();
		
		System.out.println("server: listening on port " + port);
	}
}
