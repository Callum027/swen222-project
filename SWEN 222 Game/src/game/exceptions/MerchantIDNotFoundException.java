package game.exceptions;

public class MerchantIDNotFoundException extends GameException {
	private static final long serialVersionUID = 1L;

	public MerchantIDNotFoundException(byte id) {
		super(false, "merchant ID not found: " + id);
	}
}
