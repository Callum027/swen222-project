package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.Position;
import game.world.characters.classes.GameClass;

public class JoinPacket implements Streamable {
	private final int areaID;
	private final Position position;
	private final String name;
	private final GameClass.CharacterClass playerClass;
	
	public JoinPacket(int areaID, Position position, String name, GameClass.CharacterClass playerClass) {
		this.areaID = areaID;
		this.position = position;
		this.name = name;
		this.playerClass = playerClass;
	}
	
	public int getAreaID() {
		return areaID;
	}
	
	public Position getPosition() {
		return position;
	}

	public String getName() {
		return name;
	}

	public GameClass.CharacterClass getPlayerClass() {
		return playerClass;
	}

	/**
	 * Read a JoinPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return JoinPacket
	 * @throws IOException
	 */
	public static JoinPacket read(InputStream is) throws IOException, GameException {
		return new JoinPacket(NetIO.readInt(is),
				Position.read(is),
				NetIO.readString(is),
				GameClass.CharacterClass.read(is));
	}

	public void write(OutputStream os) throws IOException {
		NetIO.writeInt(os, areaID);
		position.write(os);
		NetIO.writeString(os, name);
		playerClass.write(os);
	}

}
