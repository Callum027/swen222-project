package game.ui.application;

import game.Main;
import game.ui.GameFrame;
import game.world.items.Equipment;
import game.world.items.MoveableItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A custom JPanel used to represent the players inventory. Stores the players
 * inventory here as well so it can draw it easier. Also has a record of the
 * players cats so it can draw them too. This was done rather than having the
 * whole player object stored it can just have the inventory stored.
 *
 * @author Harry
 *
 */
public class InventoryPanel extends JPanel implements MouseListener {

	private final int width = INVENTORY_WIDTH * squareSize + 20;
	private final int height = INVENTORY_HEIGHT * squareSize + 60;
	public static final int INVENTORY_WIDTH = 3;
	public static final int INVENTORY_HEIGHT = 6;
	public static final int squareSize = 45;
	private MoveableItem[] items = new MoveableItem[INVENTORY_HEIGHT
			* INVENTORY_WIDTH];
	private int cats = 1;
	private MoveableItem itemSelected;
	private EquipPanel equip;
	private int previousSlot = -1;
	private Image background;

	/**
	 * Makes a new InventoryPanel which extends JPanel and sets the width and
	 * height fields so that the panel can be a certain size
	 *
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public InventoryPanel() {
		addItem(new MoveableItem(new Point(0, 0), squareSize, "cat-inv", 9001));
		addItem(new Equipment(new Point(0, 0), squareSize, "mithril-hat", 100, 800,
				9001, EquipPanel.HEAD_SLOT));
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
		background = Main.getImage("Inventory.png");
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		drawBlankInventory(g);
	}

	/**
	 * Draws a grid from the size of the inventory and makes it look nice-ish in
	 * a square
	 *
	 * @param g
	 *            a Graphics object
	 */
	private void drawBlankInventory(Graphics g) {
		g.drawImage(background, 0, 0, null);
		g.setColor(Color.white);
		g.drawString("" + cats, 40, height - 11);
		drawInventoryItems(g);
	}

	/**
	 * Iterates through the whole inventory drawing the items in it
	 *
	 * @param g
	 */
	private void drawInventoryItems(Graphics g) {
		int j = 0;
		int k = 0;
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				items[i].draw(g, (j * (squareSize + 2)) + 8,
						(k * (squareSize + 2)) + 22);
			}
			j++;
			if (j == INVENTORY_WIDTH) {
				j = 0;
				k++;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Uses two ints to find the location in the inventory.
	 *
	 * @param x
	 *            the x of the panel
	 * @param y
	 *            the y of the panel
	 * @return returns an index value for the array
	 */
	private int findInventorySquare(int x, int y) {
		int XSelect = (x / (squareSize + 8));
		// works out how far along the grid it is
		int ySelect = ((y / (squareSize + 8)) * INVENTORY_WIDTH);
		/*
		 * It works out how far down the grid it is and then times it by
		 * INVENTORY_WIDTH This is due to the fact that it is saved in a 1D
		 * array rather than a 2D array even though it is expressed as a 2D
		 * array
		 */
		System.out.println("X = " + XSelect + " Y = " + ySelect);
		int selected = XSelect + ySelect;
		System.out.println("Selected = " + selected);
		/*
		 * adds the x and y values together and represents it as an index in the
		 * array
		 */
		if (selected > INVENTORY_HEIGHT * INVENTORY_WIDTH) {
			// this is to make sure that the place clicked is in the inventory
			selected = -1; // if it's not then set it to -1
		}
		return selected;
	}

	/**
	 * This method is called when the mouse is clicked and determines the square
	 * in the array that the items are stored in
	 *
	 * @param x
	 *            the mouse X
	 * @param y
	 *            the mouse Y
	 */
	private void selectItem(int inv) {
		if (inv >= 0 && inv < items.length) {
			itemSelected = items[inv];
			items[inv] = null;
			previousSlot = inv;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int inv = findInventorySquare(x, y);
			selectItem(inv);
			if (itemSelected != null) {
				System.out.println("Item: " + itemSelected.toString());
			}
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int inv = findInventorySquare(x, y);
			// if (inv < 0) {
			dropItem(inv);
			repaint();
			// }
			itemSelected = null;
			previousSlot = -1;
		}
	}

	/**
	 * This method will drop the item, into an inventory slot
	 *
	 * @param x
	 * @param y
	 */
	private void dropItem(int inv) {
		if (inv >= 0 && inv < INVENTORY_HEIGHT * INVENTORY_WIDTH) {
			if (items[inv] != null) {
				returnItem(itemSelected);
			} else {
				items[inv] = itemSelected;
				itemSelected = null;
				previousSlot = -1;
			}
		}
	}

	/**
	 * Returns the item back to the inventory to the slot it was from, used if
	 * trying to move non equipment to the equipment panel Or if you are trying
	 * to drop an item on top of another item in the inventory
	 *
	 * @param item
	 *            The item to be returned
	 */
	public void returnItem(MoveableItem item) {
		if (previousSlot != -1) {
			itemSelected = item;
			dropItem(previousSlot);
			itemSelected = null;
			previousSlot = -1;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (GameFrame.selectedItem != null) {
			addItem(GameFrame.selectedItem);
		}
		GameFrame.selectedItem = null;

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (itemSelected != null) {
			GameFrame.selectedItem = itemSelected;
		}
		itemSelected = null;
		repaint();
	}

	/**
	 * gets the array of items in the inventory
	 *
	 * @return an array of Movable items, which is the inventory
	 */
	public MoveableItem[] getItems() {
		return items;
	}

	/**
	 * Sets the inventory to be a specific inventory
	 *
	 * @param items
	 *            the array of items for the inventory
	 */
	public void setItems(MoveableItem[] items) {
		this.items = items;
	}

	/**
	 * Gives this panel an equipPanel. Used so that InventoryPanel can talk to
	 * EquipPanel. Is called in the GameFrame to set everything up
	 *
	 * @param equip
	 *            the equipment panel.
	 */
	public void setEquip(EquipPanel equip) {
		this.equip = equip;

	}

	/**
	 * Adds an item to the inventory panel and places it in the first available
	 * slot
	 *
	 * @param item
	 *            the item to be added to the inventory
	 * @return the index that the item was added, returns -1 if there is no room
	 *         available.
	 */
	public int addItem(MoveableItem item) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				items[i] = item;
				repaint();
				return i;
			}
		}
		repaint();
		return -1;
	}

}
