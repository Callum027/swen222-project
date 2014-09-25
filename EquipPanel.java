package game.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class EquipPanel extends JPanel {
	private final Item[] equip = new Item[5];
	private int width;
	private int height;
	public static final int squareSize = 40;

	public EquipPanel(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void paintComponent(Graphics g){
		g.setColor(Color.blue);
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
		for(int i=0; i<equip.length; i++){
			g.drawRect(squareSize*i, squareSize*i, squareSize, squareSize);
		}

	}
}
