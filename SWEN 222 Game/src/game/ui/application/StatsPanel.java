package game.ui.application;

import game.Main;
import game.ui.GameComponent;
import game.ui.GameFrame;
import game.world.items.MoveableItem;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Extends JPanel and holds the stats of the player. Makes it look nice-ish and
 * is updated every time the equipment of the player is updated. Doesn't store
 * any of the equipment information other than the equipPanel. Does store the
 * total values of the equipment as well as the hp of the player
 *
 * @author Harry King
 *
 */
public class StatsPanel extends JPanel implements GameComponent {

	private static final long serialVersionUID = 1L;

	private int hp = 100;
	private int attack = 0;
	private int defence = 0;

	private static final int WIDTH = 155;
	private static final int HEIGHT = 169;

	private final int HP_X;
	private final int HP_Y;
	private final int ATTACK_X;
	private final int ATTACK_Y;
	private final int DEFENCE_X;
	private final int DEFENCE_Y;

	private Image background;

	private EquipPanel equip;
	private InventoryPanel inventory;

	/**
	 * Constructor. Sets up the StatsPanel, uses references from the equipPanel
	 * to set up the dimensions
	 *
	 * @param equip
	 *            The equip panel so it can get the stats of the player
	 */
	public StatsPanel(EquipPanel equip, InventoryPanel inventory) {
		this.inventory=inventory;
		this.equip = equip;

		HP_X = WIDTH - 110;
		HP_Y = HEIGHT - 130;

		ATTACK_X = WIDTH - 110;
		ATTACK_Y = HEIGHT - 115;

		DEFENCE_X = WIDTH - 110;
		DEFENCE_Y = HEIGHT - 100;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		background = Main.getImage("Stats.png");
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
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
		attack = equip.getTotalAttack();
		defence = equip.getDefence();
	}

	@Override
	public void mouseClicked(GameFrame frame, MouseEvent e) {

	}

	@Override
	public void mouseReleased(GameFrame frame, MouseEvent e) {

		if(inventory.addItem(frame.getSelectedItem()) != -1){
			frame.setSelectedItem(null);
		}
	}

	@Override
	public void mousePressed(GameFrame frame, MouseEvent e) {

	}

	@Override
	public int addItem(MoveableItem item) {

		return -1;
	}

}
