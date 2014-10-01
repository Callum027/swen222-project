package game.ui;

import game.ui.application.EquipPanel;
import game.ui.application.InventoryPanel;
import game.ui.rendering.RenderingPanel;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 * Makes the frame that the game is going to run in Extends JFrame and has some
 * panels in it
 *
 * @author Harry
 *
 */
public class GameFrame extends JFrame implements ActionListener, KeyListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private JButton quit;
	private RenderingPanel render;
	private EquipPanel equip;
	private InventoryPanel inventory;

	/**
	 * Is called automatically from Main
	 *
	 * @param gameWindowX
	 *            the width of the window
	 * @param gameWindowY
	 *            the height of the window
	 * @param cursor
	 *            enables the game to run with a custom cursor
	 */
	public GameFrame(int gameWindowX, int gameWindowY, Cursor cursor) {
		super("SWEN 222 Game Project");
		setResizable(false);
<<<<<<< HEAD

		setLayout(new FlowLayout()); // sets the frame to have a layout so that
										// the screens are in proportion
=======
		// set the frame to have a layout so that the screens are in proportion
		setLayout(new FlowLayout());
>>>>>>> feaae0f36a709631d0d20938bd2125ce0c69cf81
		render = new RenderingPanel();
		quit = new JButton("Quit");
		render.add(quit);
		//render.setPreferredSize(new Dimension((int) (gameWindowX * 0.75), gameWindowY));
		add(render);
		quit.addActionListener(this);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JPanel appPane = new JPanel();
		appPane.setLayout(new BoxLayout(appPane, BoxLayout.Y_AXIS));
		inventory = new InventoryPanel();
		equip = new EquipPanel();
		appPane.add(equip);
		appPane.add(inventory);
		add(appPane);
		setCursor(cursor);
		pack();
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			int quit = getQuitCommand();
			if (quit == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

	}

	/**
	 * A simple command that prompts the dialog box to quit the game only ever
	 * called after quit is pressed
	 *
	 * @return an int from the dialog box that was chosen
	 */
	private int getQuitCommand() {
		return JOptionPane.showOptionDialog(this,
				"Are you sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);
	}

	public RenderingPanel getRender() {
		return render;
	}

	@Override
	public void keyTyped(KeyEvent e) {


	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode()+"");
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			System.out.println("WOO!");
			openMenu();
		}

	}

	private void openMenu() {
		getQuitCommand();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
