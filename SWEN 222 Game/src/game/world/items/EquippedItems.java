package game.world.items;

/**
 * The Items that the Player has currently equipped. Implementation involving
 * equipping items is done in this class
 *
 * @author Nick Tran
 *
 */
public class EquippedItems {

	private Equipment head; // the head armour
	private Equipment body; // the body armour
	private Equipment boots; // the boots

	private Equipment mainHand; // the main-head weapon
	private Equipment offHand; // the off-hand weapon

	/**
	 * Equips the head armour
	 *
	 * @param head
	 *            the head armour to equip
	 */
	public void equipHead(Equipment head) {
		this.head = head;
	}

	/**
	 * Equips the body armour
	 *
	 * @param body
	 *            the armour to equip
	 */
	public void equipBody(Equipment body) {
		this.body = body;
	}

	/**
	 * Equips the boots
	 *
	 * @param boots
	 *            the boots to equip
	 */
	public void equipBoots(Equipment boots) {
		this.boots = boots;
	}

	/**
	 * Equips a weapon to the main hand
	 *
	 * @param mainHand
	 *            the weapon to equip
	 */
	public void equipMainHand(Equipment mainHand) {
		this.mainHand = mainHand;
	}

	/**
	 * Equips a weapon to the off hand
	 *
	 * @param offHand
	 *            the weapon to equip
	 */
	public void equipOffHand(Equipment offHand) {
		this.offHand = offHand;
	}

	/**
	 * Retrieves the head armour
	 *
	 * @return the currently equipped head armor
	 */
	public Equipment getHead() {
		return head;
	}

	/**
	 * Retrieves the body armour
	 *
	 * @return the currently equipped body armor
	 */
	public Equipment getBody() {
		return body;
	}

	/**
	 * Retrieves the boots
	 *
	 * @return the currently equipped boots
	 */
	public Equipment getBoots() {
		return boots;
	}

	/**
	 * Retrieves the main-hand weapon
	 *
	 * @return the currently equipped main-hand weapon
	 */
	public Equipment getMainHand() {
		return mainHand;
	}

	/**
	 * Retrieves the off-hand weapon
	 *
	 * @return the currently equipped off-hand weapon
	 */
	public Equipment getoffHand() {
		return offHand;
	}
}
