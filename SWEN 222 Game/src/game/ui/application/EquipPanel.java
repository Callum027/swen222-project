package game.ui.application;

import game.Main;
import game.ui.GameFrame;
import game.world.items.Equipment;
import game.world.items.EquippedItems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A custom JPanel used to represent the current equipment equiped by the
 * player. It has an EquipedItem object which is the same as the players but
 * also stored here for ease of access.
 *
 * @author Harry
 *
 */
public class EquipPanel extends JPanel implements MouseListener {
	private final int equipSize = 5;
	private final int width;
	private final int height;

	public static final int HEAD_SLOT = 0;
	public static final int OFF_HAND = 2;
	public static final int MAIN_HAND = 1;
	public static final int CHEST_SLOT = 3;
	public static final int FEET_SLOT = 4;

	public static final int squareSize = 43;

	private final int HEAD_Y;
	private final int HEAD_X;
	private final int BODY_X;
	private final int BODY_Y;
	private final int MAIN_X;
	private final int MAIN_Y;
	private final int OFF_X;
	private final int OFF_Y;
	private final int BOOTS_X;
	private final int BOOTS_Y;

	private StatsPanel stats;

	private EquippedItems items = new EquippedItems();
	private Equipment equipSelected;
	private int previousSelected = -1;
	private InventoryPanel inventory;
	private Image background;

	/**
	 * Makes a new EquipPanel sets the width and height of the panel
	 *
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public EquipPanel(InventoryPanel inventory) {
		this.inventory = inventory;

		width = inventory.getWidth();
		height = 169;

		HEAD_X = width + 55;
		HEAD_Y = height - 147;
		BODY_X = width + 55;
		BODY_Y = height - 100;
		OFF_X = width + 102;
		OFF_Y = height - 100;
		MAIN_X = width + 8;
		MAIN_Y = height - 100;
		BOOTS_X = width + 55;
		BOOTS_Y = height - 52;



		items.equipHead(new Equipment(new Point(0, 0), squareSize, "iron-hat",
				0, 50, 50, HEAD_SLOT));
		items.equipMainHand(new Equipment(new Point(0, 0), squareSize,
				"iron-sword", 50, 0, 50, MAIN_HAND));
		items.equipBody(new Equipment(new Point(0, 0), squareSize,
				"iron-armour", 0, 50, 50, CHEST_SLOT));
		items.equipBoots(new Equipment(new Point(0, 0), squareSize,
				"iron-boots", 0, 50, 50, FEET_SLOT));
		items.equipOffHand(new Equipment(new Point(0, 0), squareSize,
				"iron-shield", 0, 50, 50, OFF_HAND));
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
		background = Main.getImage("Equip.png");
		repaint();
	}

	@Override
	public int getWidth(){
		return width;
	}

	@Override
	public int getHeight(){
		return height;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.drawImage(background, 0, 0, null);
		drawEquipmentItems(g);
		stats.refresh();
	}

	/**
	 * Adds up the defence for all the equiped items
	 *
	 * @return The total defence value
	 */
	public int getDefence() {
		int def = 0;
		if (items.getHead() != null) {
			def += items.getHead().getDefence();
		}
		if (items.getBody() != null) {
			def += items.getBody().getDefence();
		}
		if (items.getMainHand() != null) {
			def += items.getMainHand().getDefence();
		}
		if (items.getoffHand() != null) {
			def += items.getoffHand().getDefence();
		}
		if (items.getBoots() != null) {
			def += items.getBoots().getDefence();
		}
		return def;
	}

	/**
	 * Used to get the total attack value of the all equipment
	 *
	 * @return The attack value of the total equipment
	 */
	public int getTotalAttack() {
		int at = 0;
		if (items.getHead() != null) {
			at += items.getHead().getAttack();
		}
		if (items.getBody() != null) {
			at += items.getBody().getAttack();
		}
		if (items.getMainHand() != null) {
			at += items.getMainHand().getAttack();
		}
		if (items.getoffHand() != null) {
			at += items.getoffHand().getAttack();
		}
		if (items.getBoots() != null) {
			at += items.getBoots().getAttack();
		}
		return at;
	}

	/**
	 * Draws the equiped items of the character if they have an item equiped in
	 * the slot
	 *
	 * @param g
	 *            The Graphics component
	 */
	private void drawEquipmentItems(Graphics g) {
		if (items.getHead() != null) {
			items.getHead().draw(g, HEAD_X, HEAD_Y);
		}
		if (items.getMainHand() != null) {
			items.getMainHand().draw(g, MAIN_X, MAIN_Y);
		}
		if (items.getoffHand() != null) {
			items.getoffHand().draw(g, OFF_X, OFF_Y);
		}
		if (items.getBody() != null) {
			items.getBody().draw(g, BODY_X, BODY_Y);
		}
		if (items.getBoots() != null) {
			items.getBoots().draw(g, BOOTS_X, BOOTS_Y);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Works out what equipment slot has been clicked on based on where the
	 * mouse was clicked
	 *
	 * @param x
	 *            The mouse X
	 * @param y
	 *            The mouse Y
	 */
	private int findEquip(int x, int y) {
		int equip = -1;

		if (y >= HEAD_Y && y < HEAD_Y + squareSize && x >= HEAD_X
				&& x < HEAD_X + squareSize) {
			equip = HEAD_SLOT;
		} else if (y >= MAIN_Y && y < MAIN_Y + squareSize && x >= MAIN_X
				&& x < MAIN_X + squareSize) {
			equip = MAIN_HAND;
		} else if (y >= OFF_Y && y < OFF_Y + squareSize && x >= OFF_X
				&& x < OFF_X + squareSize) {
			equip = OFF_HAND;
		} else if (y >= BODY_Y && y < BODY_Y + squareSize && x >= BODY_X
				&& x < BODY_X + squareSize) {
			equip = CHEST_SLOT;
		} else if (y >= BOOTS_Y && y < BOOTS_Y + squareSize && x >= BOOTS_X
				&& x < BOOTS_X + squareSize) {
			equip = FEET_SLOT;
		}
		return equip;
	}

	/**
	 * Selects the equipment found by the findEquip() method, if there is no
	 * item equiped it tells the player. Otherwise, it tells the player what the
	 * equipment is and its stats.
	 *
	 * @param equip
	 *            The int value that represents the slot that the equipment is
	 *            stored in.
	 */
	private void selectEquip(int equip) {
		previousSelected = equip;
		stats.getStats();
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
				System.out.println("No item equiped on head");
			} else {
				System.out.println("Equiped item on head "
						+ items.getHead().toString());
				equipSelected = items.getHead();
				items.equipHead(null);
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null) {
				System.out.println("No item equiped in main hand");
			} else {
				System.out.println("Equiped item in main hand "
						+ items.getMainHand().toString());
				equipSelected = items.getMainHand();
				items.equipMainHand(null);
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null) {
				System.out.println("No item equiped in off hand");
			} else {
				System.out.println("Equiped item in off hand "
						+ items.getoffHand().toString());
				equipSelected = items.getoffHand();
				items.equipOffHand(null);
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null) {
				System.out.println("No items equiped on chest");
			} else {
				System.out.println("Equiped item on chest "
						+ items.getBody().toString());
				equipSelected = items.getBody();
				items.equipBody(null);
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null) {
				System.out.println("No items equiped on feet");
			} else {
				System.out.println("Equiped item on feet "
						+ items.getBoots().toString());
				equipSelected = items.getBoots();
				items.equipBoots(null);
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int y = e.getY();
			int x = e.getX();
			int equip = findEquip(x, y);
			selectEquip(equip);
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int equip = findEquip(x, y);
			dropEquip(equip);
			repaint();
		}
	}

	/**
	 * Called when the mouse is release. Putting the item into the slot the
	 * mouse was released on if that slot is empty. If it is not empty it
	 * returns the item to the slot it was taken from, with the returnItem()
	 * method.
	 *
	 * @param equip
	 *            The slot the equipment is to be put into
	 */
	private void dropEquip(int equip) {
		if (equipSelected == null) {
			return;
		}
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null && equipSelected.getSlot() == HEAD_SLOT) {
				items.equipHead(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null
					&& equipSelected.getSlot() == MAIN_HAND) {
				items.equipMainHand(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null
					&& equipSelected.getSlot() == OFF_HAND) {
				items.equipOffHand(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null
					&& equipSelected.getSlot() == CHEST_SLOT) {
				items.equipBody(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null
					&& equipSelected.getSlot() == FEET_SLOT) {
				items.equipBoots(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		default:
			returnItem();
			break;
		}
	}

	/**
	 * This method returns the item to the slot that it was taken from if the
	 * slot that it was tried to put in is not a valid slot. Used when moving an
	 * item between slots in the equip section. A different method is called
	 * when moving items from inventory to equip
	 */
	private void returnItem() {
		switch (previousSelected) {
		case HEAD_SLOT:
			items.equipHead(equipSelected);
			equipSelected = null;
			previousSelected = -1;
			break;
		case MAIN_HAND:
			items.equipMainHand(equipSelected);
			equipSelected = null;
			previousSelected = -1;
			break;
		case OFF_HAND:
			items.equipOffHand(equipSelected);
			equipSelected = null;
			previousSelected = -1;
			break;
		case CHEST_SLOT:
			items.equipBody(equipSelected);
			equipSelected = null;
			previousSelected = -1;
			break;
		case FEET_SLOT:
			items.equipBoots(equipSelected);
			equipSelected = null;
			previousSelected = -1;
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (GameFrame.selectedItem != null
				&& GameFrame.selectedItem instanceof Equipment) {
			Equipment equip = (Equipment) GameFrame.selectedItem;
			if (addEquip(equip) == -1) {
				inventory.returnItem(equip);
			}

		} else {
			inventory.returnItem(GameFrame.selectedItem);
		}
		GameFrame.selectedItem = null;
	}

	/**
	 * Gets the stats for the equiped item that the mouse is hovering over, not
	 * working yet
	 *
	 * @param equip
	 *            The value of the slot of the equiped item
	 */
	private void equipStats(int equip) {
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
			} else {
				stats.getStats();
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null) {
			} else {
				stats.getStats();
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null) {
			} else {
				stats.getStats();
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null) {
			} else {
				stats.getStats();
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null) {
				System.out.println("No items equiped on feet");
			} else {
				stats.getStats();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (equipSelected != null) {
			GameFrame.selectedItem = equipSelected;
			equipSelected = null;
		}
	}

	/**
	 * Gets the equiped items that the panel has
	 *
	 * @return The equiped items
	 */
	public EquippedItems getItems() {
		return items;
	}

	/**
	 * Sets the items for the panel so it can draw them appropriately also calls
	 * getStats again so it updates the total stats
	 *
	 * @param items
	 *            The EquippedItems item which is a class containing all the
	 *            items equiped by the player
	 */
	public void setItems(EquippedItems items) {
		this.items = items;

	}

	/**
	 * Adds the equipment to the correct slot that it belongs in. Used when
	 * moving an item from the inventory to the equipment slot. If the slot is
	 * full it returns -1 rather than the equipment position it was put in
	 *
	 * @param equipment
	 *            The equipment to be added
	 * @return The int related to the slot of the equipment. Returns -1 if there
	 *         was no room to equip it
	 */
	public int addEquip(Equipment equipment) {
		int slot = equipment.getSlot();
		switch (slot) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
				items.equipHead(equipment);
				repaint();
				return HEAD_SLOT;
			} else {
				break;
			}
		case MAIN_HAND:
			if (items.getMainHand() == null) {
				items.equipMainHand(equipment);
				repaint();
				return MAIN_HAND;
			} else {
				break;
			}
		case OFF_HAND:
			if (items.getoffHand() == null) {
				items.equipOffHand(equipment);
				repaint();
				return OFF_HAND;
			} else {
				break;
			}
		case CHEST_SLOT:
			if (items.getBody() == null) {
				items.equipBody(equipment);
				repaint();
				return CHEST_SLOT;
			} else {
				break;
			}
		case FEET_SLOT:
			if (items.getBoots() == null) {
				items.equipBoots(equipment);
				repaint();
				return FEET_SLOT;
			} else {
				break;
			}
		default:
			break;
		}

		return -1;
	}

	public StatsPanel getStats() {
		return stats;
	}

	public void setStats(StatsPanel stats) {
		this.stats = stats;
	}
}
