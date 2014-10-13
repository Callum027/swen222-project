package game.ui.application;

import game.Main;
import game.ui.GameComponent;
import game.ui.GameFrame;
import game.world.Position;
import game.world.items.Equipment;
import game.world.items.MoveableItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

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
public class InventoryPanel extends JPanel implements GameComponent {

	private static final long serialVersionUID = 1L;
	private final int width = INVENTORY_WIDTH * squareSize + 20;
	private final int height = INVENTORY_HEIGHT * squareSize + 60;
	public static final int INVENTORY_WIDTH = 3;
	public static final int INVENTORY_HEIGHT = 6;
	public static final int squareSize = 45;
	private MoveableItem[] items = new MoveableItem[INVENTORY_HEIGHT
			* INVENTORY_WIDTH];
	private int cats = 1;
	private MoveableItem itemSelected;
	private Graphics selectedImage;
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
		Position p = new Position(0, 0);
		addItem(new MoveableItem(p, 1, 0, "cat-inv", 9001));
		addItem(new Equipment(p, 1, 0, "wizard-hat", 0, 20, 500, EquipPanel.HEAD_SLOT));
		addItem(new Equipment(p, 1, 0, "mithril-sword", 200, 0, 500,
				EquipPanel.MAIN_HAND));
		addItem(new Equipment(p, 1, 0, "mithril-shield", 1, 200, 500,
				EquipPanel.OFF_HAND));
		addItem(new Equipment(p, 1, 0, "mithril-armour", 0, 200, 500,
				EquipPanel.CHEST_SLOT));
		addItem(new Equipment(p, 1, 0, "mithril-boots", 0, 200, 500,
				EquipPanel.FEET_SLOT));
		setPreferredSize(new Dimension(width, height));
		// addMouseListener(this);
		background = Main.getImage("Inventory.png");
		selectedImage = null;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
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
						(k * (squareSize + 2)) + 22, 0);
			}
			j++;
			if (j == INVENTORY_WIDTH) {
				j = 0;
				k++;
			}
		}
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
		int selected = XSelect + ySelect;
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
	private void selectItem(GameFrame frame, int inv) {
		if (inv >= 0 && inv < items.length) {
			itemSelected = items[inv];
			frame.setSelectedItem(itemSelected);
			items[inv] = null;
			previousSlot = inv;
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

	@Override
	public void mouseClicked(GameFrame frame, MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(GameFrame frame, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			/* int x = e.getX(); */
			/* int y = e.getY(); */
			/* int inv = findInventorySquare(x, y); */
			if (frame.getSelectedItem() != null) {
				itemSelected = frame.getSelectedItem();
				addItem(itemSelected);
				frame.setSelectedItem(null);
				itemSelected = null;
			}
			repaint();
			itemSelected = null;
			previousSlot = -1;
		}
	}

	@Override
	public void mousePressed(GameFrame frame, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			int x = e.getX();
			int y = e.getY();
			int inv = findInventorySquare(x, y);
			/* finds the inventory slot by using to ints */
			selectItem(frame, inv);
			if (itemSelected != null) {
				frame.append("Item: " + itemSelected.toString());
			}
			repaint();
		}

	}

}
