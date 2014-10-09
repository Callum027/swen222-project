package game.exceptions;

/**
 * Sub-class of exception for game-specific exceptions.
 *
 * @author Callum Dickinson
 *
 */
public abstract class GameException extends Exception {
	private static final long serialVersionUID = 1L;

	public GameException() {
		super();
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GameException(Throwable cause) {
		super(cause);
	}
}
