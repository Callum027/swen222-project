package game.ui.application;

import game.Main;
import game.exceptions.GameException;
import game.ui.GameComponent;
import game.ui.GameFrame;
import game.world.Position;
import game.world.events.TransferEvent;
import game.world.items.Container;
import game.world.items.MoveableItem;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Another JPanel that is used to represent the chest inventory. Pretty much
 * works the same as the players inventory, however it is smaller but most of
 * the methods have been copied from inventory.
 *
 * @author Harry
 *
 */
public class ChestPanel extends JPanel implements GameComponent {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 155;
	public static final int HEIGHT = 169;

	private Image background;

	private final int INVENTORY_WIDTH = 3;
	private final int INVENTORY_HEIGHT = 3;
	private final int squareSize = 45;
	private final int X_OFFSET = 0;
	private final int Y_OFFSET = 4;

	private MoveableItem itemSelected;

	private MoveableItem chest[] = new MoveableItem[INVENTORY_WIDTH
			* INVENTORY_HEIGHT];
	private int cats;

	private int previousSlot;
	private Container cont;

	/**
	 * Sets up the panel, adds in cats that have been passed to it as a
	 * container contains a set amount of cats, which can't be added to. Cats
	 * have to be taken when a player clicks on the chest and they can't only
	 * take a part of them, they have to take them all. Also cats can't be
	 * stored in a chest.
	 *
	 * @param cats
	 *            The amount of cats that is stored in the chest.
	 */
	public ChestPanel(int cats) {
		this.cats = cats;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		background = Main.getImage("chest-inventory.png");
		repaint();
	}

	@Override
	public int getWidth() {
		return WIDTH;
	}

	@Override
	public int getHeight() {
		return HEIGHT;
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(background, 0, 0, null);
		g.drawString("" + cats, 43, HEIGHT - 7);
		drawItems(g);
	}

	private void drawItems(Graphics g) {
		int j = 0;
		int k = 0;
		for (int i = 0; i < chest.length; i++) {
			if (chest[i] != null) {
				chest[i].draw(g, (j * (squareSize + 2)) - X_OFFSET,
						(k * (squareSize + 2)) - Y_OFFSET, 0);
			}
			j++;
			if (j == INVENTORY_WIDTH) {
				j = 0;
				k++;
			}
		}
	}

	/**
	 * Adds a MoveableItem to the container, used for when an item is dragged
	 * down to the panel from anywhere
	 *
	 * @param item
	 *            The item to be added to the panel
	 * @return The index that the item was saved in, if it returns -1 there was
	 *         no room left for it in the array.
	 */
	public int addItem(MoveableItem item) {
		for (int i = 0; i < chest.length; i++) {
			// checks if the item in the slot is not full
			if (chest[i] == null) {
				// if it's not then add it to the array in the slot
				chest[i] = item;
				repaint();
				return i;
			}
		}
		repaint();
		return -1;
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
		int ySelect = ((y / (squareSize + 10)) * INVENTORY_WIDTH);
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

	@Override
	public void mouseClicked(GameFrame frame, MouseEvent e) {
	}

	@Override
	public void mouseReleased(GameFrame frame, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			/*
			 * if there is no selected item, ie the slot that was clicked had no
			 * item in it or the click was out of bounds then it doesn't try to
			 * place the item. Otherwise it adds the item into the correct slot
			 */
			if (frame.getSelectedItem() != null) {
				itemSelected = frame.getSelectedItem();
				TransferEvent transfer = new TransferEvent(frame.getSelectedItem(), frame.getPlayerID(), cont);
				try {
					frame.broadcastGameEvent(transfer);
				} catch (GameException e1) {

				}
				addItem(itemSelected);
				frame.setSelectedItem(null);
				itemSelected = null;
			}
			/*
			 * regardless if it succeeds or not it resets the itemSelected and
			 * the previousSlot
			 */
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
			/* finds the inventory slot by using two ints */
			selectItem(frame, inv);
			if (itemSelected != null) {
				frame.append("Item: " + itemSelected.toString());
			}
			repaint();
		}
	}

	private void selectItem(GameFrame frame, int inv) {
		if (inv >= 0 && inv < chest.length) {
			itemSelected = chest[inv]; // gets the item from the array
			frame.setFrom(this);
			frame.setSelectedItem(itemSelected);
			chest[inv] = null;
			/*
			 * Sets the slot in the inventory to be null. Essentially it frees
			 * up the slot.
			 */
			previousSlot = inv;
		}
	}

	public MoveableItem[] getChest() {
		return chest;
	}

	public void setChest(MoveableItem[] chest) {
		this.chest = chest;
	}

	public int getCats() {
		return cats;
	}

	public void setCats(int cats) {
		this.cats = cats;
	}

	public Container getCont() {
		return cont;
	}

	public void setCont(Container cont) {
		this.cont = cont;
	}

}
