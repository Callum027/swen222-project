package game.control;

import game.exceptions.GameException;
import game.net.GamePacket;
import game.net.packets.AckPacket;
import game.net.packets.ErrPacket;
import game.net.packets.QuitPacket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * Superclass for Client and Server, which implements common code between them for reading and writing
 * GamePackets.
 *
 * @author Callum Dickinson
 *
 */
public abstract class NetIOController extends Thread {
	private static final int SOCKET_TIMEOUT = 50;
	private boolean closing = false;

	/**
	 * Read a GamePacket from the given socket, with error checking.
	 * Sends an ACK back to the socket if a successful read (and that packet is not an ACK/ERR itself),
	 * and sends an ERR if an error occured.
	 *
	 * @param socket Socket to read from
	 * @return GamePacket
	 */
	protected GamePacket read(Socket socket) {
		if (socket != null && !closing) {
			try {
				// Get the output stream of the socket.
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				// Set the normal timeout on this socket.
				socket.setSoTimeout(SOCKET_TIMEOUT);

				try {
					GamePacket gp = null;

					System.out.println("read: reading packet from peer");

					// Read a game packet from the server. Keep trying to read a packet
					// until we get one.
					while (gp == null) {
						try {
							gp = GamePacket.read(is);
						}
						catch (SocketTimeoutException e) {
							// Do nothing, try reading from the socket again...
						}
					}

					synchronized (socket) {


						// Send an ACK back to the server, if this packet is not an ACK, ERR or QUIT itself.
						// ACKs and ERRs shouldn't come through here. write() should be calling the GamePacket
						// read()/write() methods directly.
						switch (gp.getType()) {
							case ACK:
							case ERR:
								System.out.println("read: received " + gp.getType() + " from peer??? This should not be handled here");
								break;
							case QUIT:
								System.out.println("read: received QUIT");
								socket.close();
								break;
							default:
								System.out.println("read: sending ACK to peer");
								new GamePacket(GamePacket.Type.ACK, new AckPacket()).write(socket.getOutputStream());
								break;
						}

						// Return the successfully read GamePacket.
						return gp;
					}
				}
				catch (GameException e) {
					System.err.println("read: while reading packet from peer: " + e);

					// Drop the rest of the packet from the input stream.
					is.skip(is.available());

					// Send an ERR packet to the server, to get them to resend their last packet.
					new GamePacket(GamePacket.Type.ERR, new ErrPacket()).write(os);
				}
			}
			catch (SocketException e) {
				System.err.println("read: closing connection: " + e.getMessage());
				close(socket);
			}
			catch (IOException e) {
				System.err.println("read: unhandled, unknown IOException");
				e.printStackTrace();
				close(socket);
			}
		}

		return null;
	}

	/**
	 * Writes GamePacket to the given socket, with error checking. Waits for an ACK or ERR
	 * from the socket.
	 *
	 * @param socket Socket to write to
	 * @param gp GamePacket
	 */
	protected void write(Socket socket, GamePacket gp) {
		if (socket != null && !closing) {
			try {
				synchronized (socket) {
					boolean success = false;

					while (!success) {
						// Send off the game packet to the server!
						gp.write(socket.getOutputStream());

						System.out.println("write: waiting for reply from peer");

						// Wait for a reply from the server.
						try {
							GamePacket reply = null;

							// Permanently block while we wait for a reply.
							socket.setSoTimeout(0);

							while (reply == null)
							{
								try {
									reply = GamePacket.read(socket.getInputStream());
								}
								catch (SocketTimeoutException e) {
								}
							}

							// Check if this reply is an ACK or ERR, and if so, handle accordingly.
							switch (reply.getType()) {
								case ACK:
									System.out.println("write: received ACK from peer");
									success = true;
									break;
								case ERR:
									System.out.println("write: received ERR from peer, resending packet");
									break;
								default:
									System.out.println("write: ERROR: illegal state: received " + reply.getType() + " packet in ACK/ERR check, resending");
									break;
							}
						}
						catch (GameException e) {
							System.err.println("write: unexpected GameException thrown");
							e.printStackTrace();
						}
					}
				}
			}
			catch (SocketException e) {
				System.err.println("write: closing connection: " + e.getMessage());
				close(socket);
			}
			catch (IOException e) {
				System.err.println("write: unhandled, unknown IOException");
				e.printStackTrace();
				close(socket);
			}
		}
	}

	/**
	 * Tell the controller to close the server connection. This controller
	 * cannot be used after this is called.
	 */
	protected void close(Socket socket) {
			closing = true;
			try {
				new GamePacket(GamePacket.Type.QUIT, new QuitPacket()).write(socket.getOutputStream());
				socket.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Close the connection on this controller. This controller cannot be used once this is called.
	 */
	public abstract void close();

	public boolean isClosing() {
		return closing;
	}
}
