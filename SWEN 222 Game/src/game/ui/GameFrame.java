package game.ui;

import game.ui.application.EquipPanel;
import game.ui.application.InventoryPanel;
import game.ui.rendering.RenderingPanel;
import game.world.items.MovableItem;

import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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

	private static final long serialVersionUID = 1L;
	
	private static final int NORTH = 0;
	private static final int EAST = 1;
	private static final int SOUTH = 2;
	private static final int WEST = 3;

	private RenderingPanel render;
	private EquipPanel equip;
	private InventoryPanel inventory;
	public static MovableItem selectedItem;
	
	private int direction;

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
		super("An Excellent Adventure!");
		setupMenuBar();
		direction = NORTH;
		// set the frame to have a layout so that the screens are in proportion
		setLayout(new FlowLayout());
		render = new RenderingPanel();
		add(render);
		JPanel appPane = new JPanel();
		appPane.setLayout(new BoxLayout(appPane, BoxLayout.Y_AXIS));
		inventory = new InventoryPanel();
		equip = new EquipPanel(inventory);
		inventory.setEquip(equip);
		appPane.add(equip);
		appPane.add(inventory);
		add(appPane);
		addKeyListener(this);
		setCursor(cursor);
		setFocusable(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public RenderingPanel getRender() {
		return render;
	}
	
	/**
	 * Sets up the menu bar at the top of the GameFrame.
	 */
	private void setupMenuBar(){
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		file.add(quit);
		menu.add(file);
		setJMenuBar(menu);
	}

	/**
	 * Used to prompt the user with the decision to quit the game.
	 * If they choose yes, the game is exited.
	 */
	private void quitGame() {
		int result = JOptionPane.showOptionDialog(this, "Are you sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
						null, null, null);
		if(result == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

	// key listener methods

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String action = null;
		if(source instanceof JMenuItem){
			action = ((JMenuItem)source).getText();
		}
		if (action.equals("Quit")) {
			quitGame();
		}
	
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println(e.getKeyCode()+"");
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
			System.out.println("WOO!");
			//openMenu();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			direction = NORTH;
		}
		else if(e.getKeyCode() == KeyEvent.VK_A){
			System.out.println("pressed A");
			direction = (direction == NORTH) ? WEST : direction - 1;
		}
		else if(e.getKeyCode() == KeyEvent.VK_S){
			direction = SOUTH;
		}
		else if(e.getKeyCode() == KeyEvent.VK_D){
			System.out.println("pressed D");
			direction = (direction == WEST) ? NORTH : direction + 1;
		}
	}

	// unneeded method
	public void keyReleased(KeyEvent e) {}

}
