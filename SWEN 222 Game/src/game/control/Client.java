package game.control;
import game.util.GamePacket;
import game.world.GameEvent;
import game.world.GameEventListener;

import java.io.IOException;
import java.io.OutputStream;
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
public class Client extends Thread implements GameEventListener {
	private Socket socket;
	private boolean closing = false;
	
	/**
	 * Connect to the local server on the default port.
	 */
	public Client() {
		
		connect(null, Server.DEFAULT_PORT);
	}
	
	/**
	 * Make a new client instance to communicate with a local server
	 * on a given port.
	 * 
	 * @param port Port to connect on
	 */
	public Client(int port) {
		connect(null, port);
	}
	
	/**
	 * Make a new client instance to communicate to a given server
	 * to on a given port.
	 * 
	 * @param host Host to connect to
	 * @param port Port to connect on
	 */
	public Client(String host, int port) {
		connect(host, port);
	}
	
	/*
	 * Helper method to connect to a remote (or local) host.
	 * 
	 * @param host Host to connect to
	 * @param port Port to connect on
	 */
	private void connect(String host, int port) {
		try {
			socket = new Socket(InetAddress.getByName(host), port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Start the client thread main loop.
	 */
	public void run() {
		
	}
	
	public void close() {
		synchronized ((Boolean)closing) {
			closing = true;
		}
	}

	/**
	 * Listen for when a game event happens, and update the
	 * server when it happens.
	 */
	public void gameEventOccurred(GameEvent ge) {
		synchronized ((Boolean)closing) {
			if (!closing) {
				try {
					// Get the output stream of the socket.
					OutputStream os = socket.getOutputStream();
					
					// Write the game packet header.
					os.write(new GamePacket(GamePacket.Type.EVENT).toByteArray());
					
					// Write the game event as the payload!
					//os.write(ge.toByteArray());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Test main method to try a connection to a server.
	 * 
	 * @param args Optional arguments, host and port in that order
	 */
	public static void main(String[] args)
	{
		String host = (args.length > 1) ? args[1] : null;
		int port  = (args.length > 2) ? Integer.parseInt(args[2]) : Server.DEFAULT_PORT;
		
		// Make a connection to the server.
		Client c = new Client(host, port);
		
		System.out.println("client: calling gameEventOccurred");

		// Manual call to gameEventOccurred!
		c.gameEventOccurred(null);
		
		// Close the connection.
		c.close();
	}
}
