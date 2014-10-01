package game.control;
import game.world.GameEvent;
import game.world.GameEventListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread implements GameEventListener {
	private Socket socket;
	
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

	/**
	 * Listen for when a game event happens, and update the
	 * server when it happens.
	 */
	public void gameEventOccurred(GameEvent ge) {

	}
}
