package game.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({})
public class ClientServerTests {
	@Test
	public void BasicConnectionTest() {
		/*public static void main(String[] args)
		{
			String host = (args.length > 1) ? args[1] : null;
			int port  = (args.length > 2) ? Integer.parseInt(args[2]) : Server.DEFAULT_PORT;
			
			// Make a connection to the server.
			Client c = new Client(host, port);
			
			System.out.println("client: calling gameEventOccurred");

			// Manual call to gameEventOccurred!
			c.gameEventOccurred(new InteractEvent());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Close the connection.
			c.close();
		}*/
		
		/*
			public static void main(String[] args)
			{
				int port  = (args.length > 1) ? Integer.parseInt(args[1]) : Server.DEFAULT_PORT;
				
				// Make a connection to the server.
				Server s = new Server(null);
				
				if (s.setPort(DEFAULT_PORT))
				{
					s.start();
					System.out.println("server: listening on port " + port);
				}
			}
		 */
	}
}
