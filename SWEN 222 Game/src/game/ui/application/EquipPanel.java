package game.ui.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A simple panel that is to be used for drawing the inventory
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

	/**
	 * Makes a new EquipPanel sets the width and height of the panel
	 *
	 * @param width
	 *            the width of the panel
	 * @param height
	 *            the height of the panel
	 */
	public EquipPanel() {
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		drawEquip(g);
	}

	/**
	 * Draws the equipment slots where they should be, ie the head slot is at
	 * the top and boot slot is at the bottom
	 *
	 * @param g
	 */
	private void drawEquip(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(width / 2, 0, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(width / 4, (int) (height * 0.4), squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect((int) (width * 0.75), (int) (height * 0.4), squareSize,
				squareSize);
		g.setColor(Color.white);
		g.fillRect(width / 2, (int) (height * 0.25), squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(width / 2, (int) (height * 0.75), squareSize, squareSize);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int y = e.getY();
		int x = e.getX();
		System.out.println("X = " + x + " Y = " + y);
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
		int equip =-1;
		if(y>=0 && y<squareSize && x>=width/2 && x<width/2+squareSize){
			equip = HEAD_SLOT;
		}
		else if(y>=(int)(height*0.4) && y<(int)(height*0.4)+squareSize && x>=width/4 && x<width/4+squareSize){
			equip = MAIN_HAND;
		}
		else if(y>=(int)(height*0.4) && y<(int) (height*0.4)+squareSize && x>=(int)(width*0.75) && x<(int)(width*0.75)+squareSize){
			equip = OFF_HAND;
		}
		else if(y>=(int) (height*0.25) && y<(int) (height*0.25)+squareSize && x>=(width/2) && x<width/2+squareSize){
			equip = CHEST_SLOT;
		}
		else if(y>=(int) (height*0.75) && y<(int) (height*0.75)+squareSize && x>=(width/2) && x<width/2+squareSize){
			equip = FEET_SLOT;
		}
		System.out.println("Equip = "+equip);
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
