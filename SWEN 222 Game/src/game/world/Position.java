package game.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import game.net.NetIO;
import game.net.Streamable;

/**
 * Built-in class to represent position. Implements Streamable.
 *
 * @author Callum Dickinson
 *
 */
public class Position implements Streamable {

	private final int x, y;

	/**
	 * Create a new position instance, with the given co-ordinates.
	 *
	 * @param x X co-ordinate
	 * @param y Y co-ordinate
	 */
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Get the X co-ordinate of this position.
	 *
	 * @return X co-ordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the Y co-ordinate of this position.
	 *
	 * @return Y co-ordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Read a Position from the input stream.
	 *
	 * @param is InputStream
	 * @return Position
	 * @throws IOException
	 */
	public static Position read(InputStream is) throws IOException {
		int x = NetIO.readInt(is);
		int y = NetIO.readInt(is);

		return new Position(x, y);
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeInt(os, x);
		NetIO.writeInt(os, y);
	}

	public String toString(){
		return "Position: ("+x+", "+y+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
