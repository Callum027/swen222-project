package game.world.events;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.Position;
import game.world.characters.GameCharacter;
import game.world.characters.Player;

public class TransportEvent extends GameEvent{

	// fields
	private Player player;
	private int areaID;
	
	public TransportEvent(Player player, int areaID){
		if(player == null){
			throw new IllegalArgumentException("player should not be null");
		}
		
		this.player = player;
		this.areaID = areaID;
	}
	
	public Type getType() {
		return GameEvent.Type.TRANSPORT;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public int getAreaID(){
		return areaID;
	}
	
	/**
	 * Read an TransportEvent from the input stream.
	 *
	 * @param is Input stream
	 * @return MoveEvent
	 * @throws IOException
	 */
	public static TransportEvent read(InputStream is) throws IOException, GameException {
		Player player = Player.read(is);
		int areaID = NetIO.readInt(is);

		return new TransportEvent(player, areaID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		NetIO.writeInt(os, areaID);
	}

}
