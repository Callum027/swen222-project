package game.tests;

import game.control.Client;
import game.control.Server;
import game.world.characters.PlayableCharacter;
import game.world.events.MoveEvent;

import org.junit.Test;

public class ClientServerTests {
	@Test
	public void singleProcessTest() {
		Client client = new Client();
		Server server = new Server();
		
		System.out.println("binding server");
		
		server.bind();
		server.start();
		
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("connecting client");
		
		client.connect();
		client.start();
		
		System.out.println("generating game event");
		client.gameEventOccurred(new MoveEvent(0, 0, new PlayableCharacter(0, 0, "test", 0, 0)));
		
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("disconnecting hosts");
		client.close();
		server.close();
	}
	
	/*@Test
	public void clientTest() {
		Client client = new Client();
		
		try {
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.connect();
		client.start();
		
		client.gameEventOccurred(new MoveEvent(0, 0, new PlayableCharacter(0, 0, "test", 0, 0)));
		
		client.close();
	}
	
	@Test
	public void serverTest() {
		Server server = new Server();
		
		server.bind();
		server.start();
		
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		server.close();
	}*/
}
