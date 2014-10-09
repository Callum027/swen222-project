package game.ui.application;

import game.Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class StatsPanel extends JPanel {

	private int hp = 100;
	private int attack = 0;
	private int defence = 0;

	private int width;
	private int height;

	private final int HP_X;
	private final int HP_Y;
	private final int ATTACK_X;
	private final int ATTACK_Y;
	private final int DEFENCE_X;
	private final int DEFENCE_Y;

	private Image background;

	private EquipPanel equip;

	public StatsPanel(EquipPanel equip) {
		System.out.println("Making a stats Panel");
		this.equip = equip;
		this.width = equip.getWidth();
		this.height = equip.getHeight();
		HP_X = width + 20;
		HP_Y = height - 150;

		ATTACK_X = width + 20;
		ATTACK_Y = height -135;

		DEFENCE_X = width +20;
		DEFENCE_Y = height-120;
		setPreferredSize(new Dimension(width, height));
		background = Main.getImage("Stats.png");
		System.out.println("Got here");
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		System.out.println("paintcomponet");
		g.drawImage(background, 0, 0, null);
		drawStats(g);
	}

	/**
	 * A method to draw the HP, Attack and Defence stats
	 *
	 * @param g
	 *            the graphics component
	 */
	private void drawStats(Graphics g) {
		System.out.println("drawStats");
		getStats();
		g.drawString("HP: " + hp, HP_X, HP_Y);
		g.drawString("Attack: " + attack, ATTACK_X, ATTACK_Y);
		g.drawString("Defence: " + defence, DEFENCE_X, DEFENCE_Y);
	}

	/**
	 * Just calls the the other get stats methods done like this so it can
	 * easily add in other get'stat' methods if needed
	 */
	public void getStats() {
		System.out.println("GetStats");
		attack = equip.getTotalAttack();
		defence = equip.getDefence();
	}
}
