package game.ui.application;

import game.Main;
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
	private int width = 235;
	private int height = 495;
	public static final int HEAD_SLOT = 0;
	public static final int OFF_HAND = 2;
	public static final int MAIN_HAND = 1;
	public static final int CHEST_SLOT = 3;
	public static final int FEET_SLOT = 4;
	public static final int squareSize = 45;
	private final int HEAD_X = width / 2;
	private final int HEAD_Y = 0;
	private final int BODY_X = width / 2;
	private final int BODY_Y = (int) (height * 0.25);
	private final int MAIN_X = width / 4;
	private final int MAIN_Y = (int) (height * 0.4);
	private final int OFF_X = (int) (width * 0.75);
	private final int OFF_Y = (int) (height * 0.4);
	private final int BOOTS_X = width / 2;
	private final int BOOTS_Y = (int) (height * 0.75);
	private EquippedItems items = new EquippedItems();

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
	}

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
		int y = e.getY();
		int x = e.getX();
		getEquip(x, y);

	}

	/**
	 * Works out what equipment slot has been clicked on
	 *
	 * @param x
	 *            the mouse X
	 * @param y
	 *            the mouse Y
	 */
	private void getEquip(int x, int y) {
		int equip = -1;
		if (y >= 0 && y < squareSize && x >= width / 2
				&& x < width / 2 + squareSize) {
			equip = HEAD_SLOT;
		} else if (y >= (int) (height * 0.4)
				&& y < (int) (height * 0.4) + squareSize && x >= width / 4
				&& x < width / 4 + squareSize) {
			equip = MAIN_HAND;
		} else if (y >= (int) (height * 0.4)
				&& y < (int) (height * 0.4) + squareSize
				&& x >= (int) (width * 0.75)
				&& x < (int) (width * 0.75) + squareSize) {
			equip = OFF_HAND;
		} else if (y >= (int) (height * 0.25)
				&& y < (int) (height * 0.25) + squareSize && x >= (width / 2)
				&& x < width / 2 + squareSize) {
			equip = CHEST_SLOT;
		} else if (y >= (int) (height * 0.75)
				&& y < (int) (height * 0.75) + squareSize && x >= (width / 2)
				&& x < width / 2 + squareSize) {
			equip = FEET_SLOT;
		}
		switch (equip) {
		case HEAD_SLOT:
			if (items.getHead() == null) {
				System.out.println("No item equiped on head");
			} else {
				System.out.println("Equiped item on head "
						+ items.getHead().toString());
			}
			break;
		case MAIN_HAND:
			if (items.getMainHand() == null) {
				System.out.println("No item equiped in main hand");
			} else {
				System.out.println("Equiped item in main hand "
						+ items.getMainHand().toString());
			}
			break;
		case OFF_HAND:
			if (items.getoffHand() == null) {
				System.out.println("No item equiped in off hand");
			} else {
				System.out.println("Equiped item in off hand "
						+ items.getoffHand().toString());
			}
			break;
		case CHEST_SLOT:
			if (items.getBody() == null) {
				System.out.println("No items equiped on chest");
			} else {
				System.out.println("Equiped item on chest "
						+ items.getBody().toString());
			}
			break;
		case FEET_SLOT:
			if (items.getBoots() == null) {
				System.out.println("No items equiped on feet");
			} else {
				System.out.println("Equiped item on feet "
						+ items.getBoots().toString());
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public EquippedItems getItems() {
		return items;
	}

	public void setItems(EquippedItems items) {
		this.items = items;
	}
}
