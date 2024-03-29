package game.tests;

import java.awt.Point;

import game.control.Client;
import game.control.Server;
import game.exceptions.GameException;
import game.net.packets.TestPacket;
import game.world.Area;
import game.world.GameWorld;
import game.world.Position;
import game.world.characters.Player;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.GameClass.CharacterClass;
import game.world.events.MoveEvent;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;

import org.junit.Test;

public class ClientServerTests {

	private MoveEvent getTestMoveEvent() {
		return new MoveEvent(new Position(1, 1), new Player(new Position(0, 0),
				"test", 0, GameClass.CharacterClass.ROGUE));
	}

	/*
	 * @Test public void singleProcessTest() { Client client = new Client();
	 * Server server = new Server();
	 *
	 * System.out.println("binding server");
	 *
	 * server.bind(); server.start();
	 *
	 * try { Thread.sleep(100); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 *
	 * System.out.println("connecting client");
	 *
	 * client.connect(); client.start();
	 *
	 * System.out.println("generating game event"); client.gameEventOccurred(new
	 * MoveEvent(new Position(1, 1), new Player(new Position(0, 0), "test", 0,
	 * GameClass.playerClass.ROGUE)));
	 *
	 * try { Thread.sleep(1000); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); }
	 *
	 * System.out.println("disconnecting hosts"); client.close();
	 * server.close(); }
	 */

	@Test
	public void clientTest() {
		Client client = new Client();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		client.connect();
		client.start();

		//try {
			TestPacket tp = new TestPacket();
			
			System.out.println("Test byte: " + tp.getTestByte());
			System.out.println("Test short: " + tp.getTestShort());
			System.out.println("Test int: " + tp.getTestInt());
			System.out.println("Test long: " + tp.getTestLong());
			System.out.println("Test string: " + tp.getTestString());
			
			//client.gameEventOccurred(getTestEvent());
		//}
		//catch (GameException e) {
		//	e.printStackTrace();
		//}

		//client.close();
	}

	@Test
	public void serverTest() {
		Server server = new Server();

		server.bind();
		server.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		server.close();
	}
}
