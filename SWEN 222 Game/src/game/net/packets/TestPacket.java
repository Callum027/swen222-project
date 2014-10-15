package game.net.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.exceptions.GameException;
import game.net.NetIO;
import game.net.Streamable;

public class TestPacket implements Streamable {
	private final byte testByte;
	private final short testShort;
	private final int testInt;
	private final long testLong;
	private final String testString;
	
	public TestPacket() {
		testByte = (byte)(Math.random() * Byte.MAX_VALUE);
		testShort = (short)(Math.random() * Short.MAX_VALUE);
		testInt = (int)(Math.random() * Integer.MAX_VALUE);
		testLong = (long)(Math.random() * Long.MAX_VALUE);
		testString = testByte + " " + testShort + " " + testInt + " " + testLong;
	}
	
	public TestPacket(byte b, short s, int i, long l, String st) {
		testByte = b;
		testShort = s;
		testInt = i;
		testLong = l;
		testString = st;
	}
	
	public byte getTestByte() {
		return testByte;
	}

	public short getTestShort() {
		return testShort;
	}

	public int getTestInt() {
		return testInt;
	}

	public long getTestLong() {
		return testLong;
	}

	public String getTestString() {
		return testString;
	}

	/**
	 * Read an ErrPacket from this input stream.
	 *
	 * @param is Input stream
	 * @return AckPacket
	 * @throws IOException
	 */
	public static TestPacket read(InputStream is) throws IOException, GameException {
		return new TestPacket(NetIO.readByte(is),
				NetIO.readShort(is),
				NetIO.readInt(is),
				NetIO.readLong(is),
				NetIO.readString(is));
	}

	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, testByte);
		NetIO.writeShort(os, testShort);
		NetIO.writeInt(os, testInt);
		NetIO.writeLong(os, testLong);
		NetIO.writeString(os, testString);
	}

}
