package game.world.events;

import game.exceptions.GameException;
import game.net.NetIO;
import game.world.GameEvent;
import game.world.Position;
import game.world.characters.Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TransportEvent extends GameEvent{

	// fields
	private final Player player;
	private final Position transport;
	private final int areaID;

	/**
	 * Constructor:
	 * Constructs an instance of a transport event.
	 * @param player
	 * 		--- the player to be transported
	 * @param transport
	 * 		--- the position in new area to transport to
	 * @param areaID
	 * 		--- the area player is being transported to
	 */
	public TransportEvent(Player player, Position transport, int areaID){
		if(player == null){
			throw new IllegalArgumentException("player should not be null");
		}
		if(transport == null){
			throw new IllegalArgumentException("transport should not be null");
		}

		this.player = player;
		this.transport = transport;
		this.areaID = areaID;
	}

	/**
	 * Returns the type of this GameEvent.
	 *
	 * @return
	 * 		--- GameEvent.Type.TRANSPORT
	 */
	public Type getType() {
		return GameEvent.Type.TRANSPORT;
	}

	/**
	 * Returns the player that is to be transported.
	 *
	 * @return
	 * 		--- player to transport
	 */
	public Player getPlayer(){
		return player;
	}

	/**
	 * Returns the position that the player will be transported to.
	 *
	 * @return
	 * 		--- position to transport player to
	 */
	public Position getTransportPosition(){
		return transport;
	}

	/**
	 * Returns the unique ID of the area the player is being transported to.
	 *
	 * @return
	 * 		--- unique area ID
	 */
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
		Position transport = Position.read(is);
		int areaID = NetIO.readInt(is);

		return new TransportEvent(player, transport, areaID);
	}

	public void write(OutputStream os) throws IOException {
		super.write(os);

		// Write the changes this event causes to the output stream.
		player.write(os);
		transport.write(os);
		NetIO.writeInt(os, areaID);
	}

}
