package game.exceptions;

public class EnemyIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public EnemyIDNotFoundException(byte id) {
		super(false, "enemy ID not found: " + id);
	}
}