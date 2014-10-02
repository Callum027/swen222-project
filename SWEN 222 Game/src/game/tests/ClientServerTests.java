package game.tests;

import game.control.Client;
import game.control.Server;
import game.world.events.MoveEvent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//@RunWith(Suite.class)
//@SuiteClasses({})
public class ClientServerTests {
	@Test
	public void BasicConnectionTest() {
		Client client = new Client();
		Server server = new Server();
		
		server.start();
		client.start();
		
		client.gameEventOccurred(new MoveEvent(0, 0, null));
		
		server.close();
		client.close();
	}
}
