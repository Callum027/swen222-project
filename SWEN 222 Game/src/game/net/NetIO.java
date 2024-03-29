package game.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

public class NetIO {
	/** Size of a byte on network input/output. */
	public static final int BYTE_LENGTH = 1;
	/** Size of a short on network input/output. */
	public static final int SHORT_LENGTH = 2;
	/** Size of an int on network input/output. */
	public static final int INTEGER_LENGTH = 4;
	/** Size of a long on network input/output. */
	public static final int LONG_LENGTH = 8;
	
	/**
	 * Read a boolean from the input stream.
	 * 
	 * @param is Input stream
	 * @return Boolean
	 * @throws IOException
	 */
	public static boolean readBoolean(InputStream is) throws IOException {
		int b = is.read();

		if (b == -1)
			throw new EOFException("reached end of input stream in readBoolean");

		return (b == 1) ? true : false;
	}

	/**
	 * Read a byte from the input stream.
	 * 
	 * @param is Input stream
	 * @return Byte
	 * @throws IOException
	 */
	public static byte readByte(InputStream is) throws IOException {
		int b = is.read();

		if (b == -1)
			throw new EOFException("reached end of input stream in readByte");

		return (byte)b;
	}

	/**
	 * Read a short from the input stream.
	 * 
	 * @param is Input stream
	 * @return Short
	 * @throws IOException
	 */
	public static short readShort(InputStream is) throws IOException {
		short s;
		byte[] bytes = new byte[SHORT_LENGTH];
		int i = is.read(bytes);

		if (i == -1)
			throw new EOFException("reached end of input stream in readShort");

		s = (short)((bytes[0] << 8) | bytes[1]);
		return s;
	}

	/**
	 * Read an int from the input stream.
	 * 
	 * @param is Input stream
	 * @return Integer
	 * @throws IOException
	 */
	public static int readInt(InputStream is) throws IOException {
		byte[] bytes = new byte[INTEGER_LENGTH];
		int i = is.read(bytes);

		if (i == -1)
			throw new EOFException("reached end of input stream in readInt");

		i = (int)((bytes[0] << 24) | (bytes[1] << 16) | (bytes[2] << 8) | bytes[3]);
		return i;
	}

	/**
	 * Read a long from the input stream.
	 * 
	 * @param is Input stream
	 * @return Long
	 * @throws IOException
	 */
	public static long readLong(InputStream is) throws IOException {
		long l;
		byte[] bytes = new byte[LONG_LENGTH];
		int i = is.read(bytes);

		if (i == -1)
			throw new EOFException("reached end of input stream in readLong");

		l = (long)((bytes[0] << 56)
				| (bytes[1] << 48)
				| (bytes[2] << 40)
				| (bytes[3] << 32)
				| (bytes[4] << 24)
				| (bytes[5] << 16)
				| (bytes[6] << 8)
				| bytes[7]);

		return l;
	}

	/**
	 * Read a byte array from the input stream. This only reads as many bytes as the given byte array
	 * can hold.
	 *
	 * @param is Input stream
	 * @param bytes Byte array object to read into
	 * @throws IOException
	 */
	public static void readByteArray(InputStream is, byte[] bytes) throws IOException {
		int i = is.read(bytes);

		if (i == -1)
			throw new EOFException("reached end of input stream in readByteArray(bytes)");
	}

	/**
	 * Read a byte array from the input stream. This only reads as many bytes as the given length, and
	 * starts from the given offset.
	 *
	 * @param is Input stream
	 * @param bytes Byte array object to read into
	 * @param off Offset to start reading into
	 * @param len Length to read
	 * @throws IOException
	 */
	public static void readByteArray(InputStream is, byte[] bytes, int off, int len) throws IOException {
		int i = is.read(bytes, off, len);

		if (i == -1)
			throw new EOFException("reached end of input stream in readByteArray(bytes, off, len)");
	}
	
	/**
	 * Read a character from the input stream.
	 * 
	 * @param is Input stream
	 * @return Character
	 * @throws IOException
	 */
	public static char readChar(InputStream is) throws IOException {
		int c = is.read();
		
		if (c == -1)
			throw new EOFException("reached end of input stream in readChar");
		
		return (char)c;
	}
	
	/**
	 * Read a String from the input stream.
	 * @param is Input stream
	 * @return String
	 * @throws IOException
	 */
	public static String readString(InputStream is) throws IOException {
		int l;
		StringBuilder sb = new StringBuilder();
		
		// Read the length of the string. We don't use null terminators,
		// to avoid problems related to buffer overflow exploits.
		l = readInt(is);
		
		// Read the number of characters from the given input stream.
		for (int i = 0; i < l; i++)
		{
			char c = readChar(is);
			
			if (c == -1)
				throw new EOFException("reached end of input stream in readString");
			
			sb.append(c);
		}
		
		return sb.toString();
	}

	/**
	 * Write a boolean to the output stream.
	 *
	 * @param os Output stream
	 * @param b Boolean
	 * @throws IOException
	 */
	public static void writeBoolean(OutputStream os, boolean b) throws IOException {
		os.write((b) ? 1 : 0);
	}

	/**
	 * Write a byte to the output stream.
	 *
	 * @param os Output stream
	 * @param b Byte
	 * @throws IOException
	 */
	public static void writeByte(OutputStream os, byte b) throws IOException {
		os.write((int)b);
	}

	/**
	 * Write a short to the output stream.
	 *
	 * @param os Output stream
	 * @param s Short
	 * @throws IOException
	 */
	public static void writeShort(OutputStream os, short s) throws IOException {
		byte[] bytes = new byte[SHORT_LENGTH];

		bytes[0] = (byte)(s >> 8);
		bytes[1] = (byte)s;

		os.write(bytes);
	}

	/**
	 * Write an int to the output stream.
	 *
	 * @param os Output stream
	 * @param i Integer
	 * @throws IOException
	 */
	public static void writeInt(OutputStream os, int i) throws IOException {
		byte[] bytes = new byte[INTEGER_LENGTH];

		bytes[0] = (byte)((i & 0xFF000000) >> 24);
		bytes[1] = (byte)((i & 0x00FF0000) >> 16);
		bytes[2] = (byte)((i & 0x0000FF00) >> 8);
		bytes[3] = (byte)i;

		os.write(bytes);
	}

	/**
	 * Write a long to the output stream.
	 *
	 * @param os Output stream
	 * @param l Long
	 * @throws IOException
	 */
	public static void writeLong(OutputStream os, long l) throws IOException {
		byte[] bytes = new byte[LONG_LENGTH];

		bytes[0] = (byte)((l & 0xFF00000000000000L) >> 56);
		bytes[1] = (byte)((l & 0x00FF000000000000L) >> 48);
		bytes[2] = (byte)((l & 0x0000FF0000000000L) >> 40);
		bytes[3] = (byte)((l & 0x000000FF00000000L) >> 32);
		bytes[4] = (byte)((l & 0x00000000FF000000L) >> 24);
		bytes[5] = (byte)((l & 0x0000000000FF0000L) >> 16);
		bytes[6] = (byte)((l & 0x000000000000FF00L) >> 8);
		bytes[7] = (byte)l;

		os.write(bytes);
	}
	
	/**
	 * Write a char to the output stream.
	 * 
	 * @param os Output stream
	 * @param c Character
	 * @throws IOException
	 */
	public static void writeChar(OutputStream os, char c) throws IOException {
		os.write(c);
	}
	
	/**
	 * Write a String to the output stream.
	 * 
	 * @param os Output stream
	 * @param s String
	 * @throws IOException
	 */
	public static void writeString(OutputStream os, String s) throws IOException {
		writeInt(os, s.length());
		
		for (char c: s.toCharArray())
			writeChar(os, c);
	}
}
