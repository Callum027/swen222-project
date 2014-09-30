package game.ui.application;

import game.world.items.*;

import java.awt.Color;
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
	private int width;
	private int height;
	public static final int squareSize = 40;

	/**
	 * Makes a new EquipPanel sets the width and height of the panel
	 * @param width the width of the panel
	 * @param height the height of the panel
	 */
	public EquipPanel(int width, int height) {
		this.width = width;
		this.height = height;
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
	 * It is going to draw grids in a semi-organised way so you know what slot is what
	 * At the moment it just draws some squares in a diagonal
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
