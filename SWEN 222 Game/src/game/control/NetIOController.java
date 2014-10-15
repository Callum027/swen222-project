package game.control;

import game.exceptions.GameException;
import game.net.GamePacket;
import game.net.packets.AckPacket;
import game.net.packets.ErrPacket;
import game.net.packets.QuitPacket;

import java.io.BufferedOutputStream;
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
	private static final int SOCKET_TIMEOUT = 50; // 50ms
	private static final int MAIN_LOOP_COOLDOWN = 50; // 50ms
	
	private boolean closing = false;
	private boolean pauseMainLoop = false;

	/**
	 * Read a GamePacket from the given socket, with error checking.
	 * Sends an ACK back to the socket if a successful read (and that packet is not an ACK/ERR itself),
	 * and sends an ERR if an error occurred.
	 *
	 * @param socket Socket to read from
	 * @return GamePacket
	 */
	protected GamePacket read(Socket socket) {
		if (socket != null && !closing) {
			try {
				try {
					GamePacket gp = null;

					System.out.println("NetIOController.read: listening for packets from peer");

					// Read a game packet from the server. Keep trying to read a packet
					// until we get one.
					while (gp == null) {
						// Provide a point which the main reading loop can be
						// interrupted with.
						if (pauseMainLoop)
							continue;
						
						synchronized (socket) {
							try {
								// Set the normal timeout on this socket.
								socket.setSoTimeout(SOCKET_TIMEOUT);
								gp = GamePacket.read(socket.getInputStream());
							}
							catch (SocketTimeoutException e) {
								// Do nothing, try reading from the socket again...
							}
						}
						
						// Sleep the current thread, to give other threads
						// the opportunity to lock the socket for sending packets.
						try {
							Thread.sleep(MAIN_LOOP_COOLDOWN);
						}
						catch (InterruptedException e1) {
						}
					}
					
					System.out.println("NetIOController.read: received packet from peer");

					// Check the incoming packet to see what type of packet it is,
					// and respond to the server accordingly.
					synchronized (socket) {
						// Send an ACK back to the server, if this packet is not an ACK, ERR or QUIT itself.
						// ACKs and ERRs shouldn't come through here. write() should be calling the GamePacket
						// read()/write() methods directly.
						switch (gp.getType()) {
							case ACK:
							case ERR:
								System.out.println("NetIOController.read: received " + gp.getType() + " from peer??? This should not be handled here");
								break;
							case QUIT:
								System.out.println("NetIOController.read: received QUIT, closing connection");
								socket.close();
								break;
							default:
								System.out.println("NetIOController.read: sending ACK to peer");
								write(socket.getOutputStream(), new GamePacket(GamePacket.Type.ACK, new AckPacket()));
								break;
						}
					}
					
					// Return the successfully read GamePacket.
					return gp;
				}
				catch (GameException e) {
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					
					System.out.println("NetIOController.read: while reading packet from peer: " + e);

					// Drop the rest of the packet from the input stream.
					is.skip(is.available());

					// Send an ERR packet to the server, to get them to resend their last packet.
					write(os, new GamePacket(GamePacket.Type.ERR, new ErrPacket(e.shouldResendPacket())));
				}
			}
			catch (SocketException e) {
				System.out.println("NetIOController.read: closing connection: " + e.getMessage());
				close(socket);
			}
			catch (IOException e) {
				System.out.println("NetIOController.read: unhandled, unknown IOException");
				e.printStackTrace();
				close(socket);
			}
		}

		return null;
	}
	
	/**
	 * Actually perform the writing operation to the output stream. Store the
	 * write operations into a buffer before sending them, in one go.
	 * @param os
	 * @param gp
	 */
	private void write(OutputStream os, GamePacket gp) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(os);
		
		// Write the game packet to the buffered output stream.
		gp.write(bos);
		
		// Send the packet to the output stream, in one go!
		bos.flush();
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
					boolean resendPacket = false;
					
					// Pause the main listening loop while we start our communication
					// with the server.
					pauseMainLoop = true;

					do {
						System.out.println("NetIOController.write: sending GamePacket to peer");
						
						// Send the packet to the output stream!
						write(socket.getOutputStream(), gp);

						System.out.println("NetIOController.write: waiting for reply from peer");

						// Wait for a reply from the server.
						try {
							GamePacket reply = null;

							// Permanently block while we wait for a reply.
							socket.setSoTimeout(0);
							reply = GamePacket.read(socket.getInputStream());
							
							// Check if this reply is an ACK or ERR, and if so, handle accordingly.
							switch (reply.getType()) {
								case ACK:
									System.out.println("NetIOController.write: received ACK from peer");
									break;
								case ERR:
									System.out.println("NetIOController.write: received ERR from peer");
									ErrPacket ep = (ErrPacket)reply.getPayload();
									
									if (ep.shouldResendPacket())
									{
										System.out.println("NetIOController.write: resending packet");
										resendPacket = true;
									}
									else
										System.out.println("NetIOController.write: NOT resending packet");
									break;
								default:
									System.out.println("NetIOController.write: ERROR: illegal state: received " + reply.getType() + " packet in ACK/ERR check, resending");
									resendPacket = true;
									break;
							}
						}
						catch (GameException e) {
							System.err.println("NetIOController.write: unexpected GameException thrown");
							e.printStackTrace();
						}
					} while (resendPacket);
					
					// Unpause the main loop, let it start reading packets again.
					pauseMainLoop = false;
				}
			}
			catch (SocketException e) {
				System.err.println("NetIOController.write: closing connection: " + e.getMessage());
				close(socket);
			}
			catch (IOException e) {
				System.err.println("NetIOController.write: unhandled, unknown IOException");
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
				if (!socket.isClosed())
				{
					write(socket.getOutputStream(), new GamePacket(GamePacket.Type.QUIT, new QuitPacket()));
					socket.close();
				}
			}
			catch (SocketException e) {
				System.out.println("NetIOController.close: socket error while closing: " + e.getMessage());
			}
			catch (IOException e) {
				System.err.println("NetIOController.close: unexpected IOException");
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
