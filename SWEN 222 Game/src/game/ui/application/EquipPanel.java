package game.ui.application;

import game.Main;
import game.ui.GameFrame;
import game.world.items.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A simple panel that is to be used for drawing the inventory Also can
 * determine what equip slot was selected so it can return a related int value
 * for what slot on the character it was
 *
 * @author Harry
 *
 */
public class EquipPanel extends JPanel implements MouseListener {
	private final int equipSize = 5;
	private final int width = 235;
	private final int height = 495;

	public static final int HEAD_SLOT = 0;
	public static final int OFF_HAND = 2;
	public static final int MAIN_HAND = 1;
	public static final int CHEST_SLOT = 3;
	public static final int FEET_SLOT = 4;

	public static final int squareSize = 45;

	private final int HEAD_X = (int) (width * 0.5);
	private final int HEAD_Y = (int) (height * 0.1);
	private final int BODY_X = (int) (width * 0.5);
	private final int BODY_Y = (int) (height * 0.25);
	private final int MAIN_X = width / 4;
	private final int MAIN_Y = (int) (height * 0.25);
	private final int OFF_X = (int) (width * 0.75);
	private final int OFF_Y = (int) (height * 0.25);
	private final int BOOTS_X = width / 2;
	private final int BOOTS_Y = (int) (height * 0.5);
	private final int HP_X = (int) (width * 0.2);
	private final int HP_Y = (int) (height * 0.6666);
	private final int ATTACK_X = (int) (width * 0.2);
	private final int ATTACK_Y = (int) (height * 0.7);
	private final int DEFENCE_X = (int) (width * 0.2);
	private final int DEFENCE_Y = (int) (height * 0.75);

	private int hp = 100;
	private int attack = 0;
	private int defence = 0;
	private EquippedItems items = new EquippedItems();
	private Equipment equipSelected;
	private int previousSelected;

	/**
	 * Makes a new EquipPanel sets the width and height of the panel
	 *
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public EquipPanel() {
		items.equipHead(new Equipment(0, 0, squareSize, "Cat Hat", Main
				.getImage("cat-inv.png"), 0, 100, 100));
		items.equipMainHand(new Equipment(0, 0, squareSize, "Cat Sword", Main
				.getImage("cat-inv.png"), 50, 0, 50));
		items.equipOffHand(new Equipment(0, 0, squareSize, "Cat Shield", Main
				.getImage("cat-inv.png"), 0, 200, 200));
		items.equipBody(new Equipment(0, 0, squareSize, "Cat Brestplate", Main
				.getImage("cat-inv.png"), 0, 300, 300));
		items.equipBoots(new Equipment(0, 0, squareSize, "Cat Boots", Main
				.getImage("cat-inv.png"), 0, 400, 400));
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
		getStats();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		drawBlankEquip(g);
	}

	/**
	 * Draws the equipment slots where they should be, ie the head slot is at
	 * the top and boot slot is at the bottom
	 *
	 * @param g
	 *            the graphics component
	 */
	private void drawBlankEquip(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(HEAD_X, HEAD_Y, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(MAIN_X, MAIN_Y, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(OFF_X, OFF_Y, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(BODY_X, BODY_Y, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(BOOTS_X, BOOTS_Y, squareSize, squareSize);
		drawEquipmentItems(g);
		drawStats(g);
	}

	/**
	 * A method to draw the HP, Attack and Defence stats underneath the
	 * equipment slots
	 *
	 * @param g
	 *            the graphics component
	 */
	private void drawStats(Graphics g) {
		g.drawString("HP: " + hp, HP_X, HP_Y);
		g.drawString("Attack: " + attack, ATTACK_X, ATTACK_Y);
		g.drawString("Defence: " + defence, DEFENCE_X, DEFENCE_Y);
	}

	/**
	 * Just calls the the other get stats methods done like this so it can
	 * easily add in other get'stat' methods if needed
	 */
	private void getStats() {
		attack = getTotalAttack();
		defence = getDefence();
	}

	/**
	 * Adds up the defence for all the equiped items
	 *
	 * @return the total defence value
	 */
	private int getDefence() {
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
	 * @return the attack value of the total equipment
	 */
	private int getTotalAttack() {
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
	 *            the Graphics component
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
	 * Works out what equipment slot has been clicked on
	 *
	 * @param x
	 *            the mouse X
	 * @param y
	 *            the mouse Y
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
	 * Selects the equpipment found by the findEquip method
	 *
	 * @param equip
	 *            the value of the equipment slot
	 */
	private void selectEquip(int equip) {
		previousSelected = equip;
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
				;
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
		int y = e.getY();
		int x = e.getX();
		int equip = findEquip(x, y);
		selectEquip(equip);
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int equip = findEquip(x, y);
		dropEquip(equip);
		repaint();
	}

	/**
	 * Called when the mouse is release. Putting the item into the slot the
	 * mouse was released on if that slot is empty. If it is not empty it
	 * returns the item to the slot it was taken from
	 *
	 * @param equip
	 *            the slot the equipment is to be put into
	 */
	private void dropEquip(int equip) {
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
				items.equipHead(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null) {
				items.equipMainHand(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null) {
				items.equipMainHand(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null) {
				items.equipBody(equipSelected);
				equipSelected = null;
				previousSelected = -1;
			} else {
				returnItem();
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null) {
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

	private void returnItem() {
		switch (previousSelected){
		case HEAD_SLOT:
			items.equipHead(equipSelected);
			equipSelected=null;
			previousSelected=-1;
			break;
		case MAIN_HAND:
			items.equipMainHand(equipSelected);
			equipSelected=null;
			previousSelected=-1;
			break;
		case OFF_HAND:
			items.equipOffHand(equipSelected);
			equipSelected=null;
			previousSelected=-1;
			break;
		case CHEST_SLOT:
			items.equipBody(equipSelected);
			equipSelected=null;
			previousSelected=-1;
			break;
		case FEET_SLOT:
			items.equipBoots(equipSelected);
			equipSelected=null;
			previousSelected=-1;
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		int y = e.getY();
		int x = e.getX();
		System.out.println("Y = " + y + "X = " + x);
		int equip = findEquip(x, y);
		equipStats(equip);
		repaint();

	}

	/**
	 * gets the stats for the equiped item that the mouse is hovering over
	 *
	 * @param equip
	 *            the value of the slot of the equiped item
	 */
	private void equipStats(int equip) {
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
			} else {
				attack = items.getHead().getAttack();
				defence = items.getHead().getDefence();
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null) {
			} else {
				attack = items.getMainHand().getAttack();
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null) {
			} else {
				attack = items.getoffHand().getAttack();
				defence = items.getoffHand().getDefence();
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null) {
			} else {
				attack = items.getBody().getAttack();
				defence = items.getBody().getDefence();
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null) {
				System.out.println("No items equiped on feet");
			} else {
				attack = items.getBoots().getAttack();
				defence = items.getBoots().getDefence();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		getStats();
	}

	/**
	 * gets the equiped items that the panel has
	 *
	 * @return the equiped items
	 */
	public EquippedItems getItems() {
		return items;
	}

	/**
	 * sets the items for the panel so it can draw them appropriatly also calls
	 * getStats again so it updates the total stats
	 *
	 * @param items
	 *            the equipedItems
	 */
	public void setItems(EquippedItems items) {
		this.items = items;
		getStats();
	}
}
