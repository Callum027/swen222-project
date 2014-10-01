package game.ui.application;

import game.Main;
import game.world.characters.PlayableCharacter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A simple panel that drawing the inventory items on screen
 * Also can determine what inventory slot was selected so it can return the value for the array
 * @author Harry
 *
 */
public class InventoryPanel extends JPanel implements MouseListener {

	private int width = INVENTORY_WIDTH * squareSize + 10;
	private int height = INVENTORY_HEIGHT * squareSize + 50;
	public static final int INVENTORY_WIDTH = 5;
	public static final int INVENTORY_HEIGHT = 4;
	public static final int squareSize = 45;

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
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);

		drawInventory(g);
	}

	/**
	 * Draws a grid from the size of the inventory and makes it look nice-ish in
	 * a square
	 *
	 * @param g
	 *            a Graphics object
	 */
	private void drawInventory(Graphics g) {
		for (int i = 0; i < INVENTORY_WIDTH; i++) {
			for (int j = 0; j < INVENTORY_HEIGHT; j++) {
				g.setColor(Color.white);
				g.fillRect(squareSize * i, squareSize * j, squareSize,
						squareSize);
				g.setColor(Color.black);
				g.drawRect(squareSize * i, squareSize * j, squareSize,
						squareSize);
			}

		}
		Image img = Main.getImage("cat-inv.jpg");
		g.setColor(Color.white);
		g.drawImage(img, 10, height - 50, 45, 45, null);
		g.drawString("" + PlayableCharacter.getCats(), 65, height - 10);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		selectItem(x, y);

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
	private void selectItem(int x, int y) {
		System.out.println("X = " + x + " Y = " + y);
		int XSelect = x / squareSize; // works out how far along the grid it is
		int ySelect = (y / squareSize) * INVENTORY_WIDTH;
		/*
		 * It works out how far down the grid it is and then times it by
		 * INVENTORY_HEIGHT This is due to the fact that it is saved in a 1D
		 * array rather than a 2D array even though it is expressed as a 2D
		 * array
		 */

		int selected = XSelect + ySelect; // adds the x and y values together
											// and represents it as an index in
											// the array
		if (selected > INVENTORY_HEIGHT * INVENTORY_WIDTH) {
			selected = -1;
		}
		System.out.println("Selected = " + selected);
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

}
