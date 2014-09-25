package game.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class InventoryPanel extends JPanel implements MouseListener{

	private int width;
	private int height;
	public static final int INVENTORY_WIDTH = 5;
	public static final int INVENTORY_HEIGHT = 5;
	public static final int squareSize = 40;
	private Item[] inventory = new Item[INVENTORY_WIDTH*INVENTORY_HEIGHT];


	/**
	 * Makes a new InventoryPanel which extends JPanel and sets the width and height fields so that the panel can be a certain size
	 * @param width the width of the panel
	 * @param height the height of the panel
	 */
	public InventoryPanel(int width, int height){
		this.width=width;
		this.height=height;

	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(0,0, width, height);
		System.out.println("("+width+", "+height+")");
		g.setColor(Color.black);
		drawInventory(g);
	}

	/**
	 * Draws a grid from the size of the invetory and makes it look nice-ish in a square
	 * @param g a Graphics object
	 */
	private void drawInventory(Graphics g) {
		for(int i =0; i<INVENTORY_WIDTH; i++){
			for(int j=0; j<INVENTORY_HEIGHT; j++){
				g.drawRect(squareSize*i, squareSize*j, squareSize, squareSize);
			}
		}


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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
