package game.exceptions;

/**
 * Sub-class of exception for game-specific exceptions.
 *
 * @author Callum Dickinson
 *
 */
public abstract class GameException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private boolean resendPacket;

	public GameException(boolean rp) {
		super();
		resendPacket = rp;
	}

	public GameException(boolean rp, String message) {
		super(message);
		resendPacket = rp;
	}

	public GameException(boolean rp, String message, Throwable cause) {
		super(message, cause);
		resendPacket = rp;
	}

	public GameException(boolean rp, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		resendPacket = rp;
	}

	public GameException(boolean rp, Throwable cause) {
		super(cause);
		resendPacket = rp;
	}
	
	public boolean shouldResendPacket() {
		return resendPacket;
	}
}
