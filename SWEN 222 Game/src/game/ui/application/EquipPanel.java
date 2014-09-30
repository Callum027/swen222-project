package game.ui.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * A simple panel that is to be used for drawing the inventory
 * @author Harry
 *
 */
public class EquipPanel extends JPanel implements MouseListener {
	private final int equipSize = 5;
	private int width = 225;
	private int height = 495;
	public static final int squareSize = 45;

	/**
	 * Makes a new EquipPanel sets the width and height of the panel
	 * @param width the width of the panel
	 * @param height the height of the panel
	 */
	public EquipPanel() {
		setPreferredSize(new Dimension(width, height));
		addMouseListener(this);
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		drawEquip(g);
	}

	/**
	 * Draws the equipment slots where they should be, ie the head slot is at the top and boot slot is at the bottom
	 * @param g
	 */
	private void drawEquip(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(width/2, 0, squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(width/4, (int)(height*0.4), squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect((int)(width*0.75),(int) (height*0.4), squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(width/2,(int) (height*0.25), squareSize, squareSize);
		g.setColor(Color.white);
		g.fillRect(width/2, (int) (height*0.75), squareSize, squareSize);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int y = e.getY();
		int x = e.getX();
		System.out.println("X = "+x+" Y = "+y);
		getEquip(x, y);

	}

	private void getEquip(int x, int y) {


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
