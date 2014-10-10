package game.ui;

import game.Main;
import game.ui.application.EquipPanel;
import game.ui.application.InventoryPanel;
import game.ui.application.StatsPanel;
import game.ui.rendering.RenderingPanel;
import game.world.GameEventBroadcaster;
import game.world.GameEventListener;
import game.world.items.MoveableItem;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

/**
 * A custom JFrame used to hold all the panels of the game. Implements
 * ActionListener and KeyListener so it can respond to the correct inputs from
 * the user.
 *
 * @author Harry
 *
 */
public class GameFrame extends JFrame implements ActionListener, KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private GameEventBroadcaster geb = new GameEventBroadcaster();

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;

	private RenderingPanel render;
	private JTextArea text;
	private JScrollPane scroll;
	private EquipPanel equip;
	private StatsPanel stats;
	private InventoryPanel inventory;

	public static MoveableItem selectedItem;
	/*
	 * selectedItem is the item that has been selected in one of the panels and
	 * is stored here so that it can be easily moved between panels.
	 */
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
	public GameFrame(int gameWindowX, int gameWindowY) {
		super("It's a Catastrophe!");
		setupMenuBar();
		direction = NORTH;
		// set the frame to have a layout so that the screens are in proportion
		//setLayout(new FlowLayout());

		// setup the components of the game frame
		render = new RenderingPanel(direction);
		text = new JTextArea(7, 49);
		scroll = new JScrollPane(text);
		inventory = new InventoryPanel();
		equip = new EquipPanel(inventory);
		stats = new StatsPanel(equip);
		inventory.setEquip(equip);
		equip.setStats(stats);

		// setup the render pane
		JPanel renderPane = new JPanel();
		renderPane.addMouseListener(this);
		renderPane.setLayout(new BoxLayout(renderPane, BoxLayout.Y_AXIS));
		renderPane.add(render);
		render.addMouseListener(this);
		renderPane.add(scroll);
		renderPane.addMouseListener(this);

		// setup app pane
		JPanel appPane = new JPanel();
		appPane.setLayout(new BoxLayout(appPane, BoxLayout.Y_AXIS));

		appPane.add(stats);
		stats.addMouseListener(this);
		appPane.add(equip);
		equip.addMouseListener(this);
		appPane.add(inventory);
		appPane.add(stats);
		inventory.addMouseListener(this);
		appPane.addMouseListener(this);

		// setup the game frame layout
		setLayout(new FlowLayout());
		add(renderPane);
		add(appPane);

		addMouseListener(this);
		addKeyListener(this);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image img = Main.getImage("ff_cursor.png");
		Cursor cursor = (img != null) ? tk.createCustomCursor(img, new Point(0,
				0), "cursor") : Cursor.getDefaultCursor();
		// makes a custom cursor from an image file
		setCursor(cursor);
		setFocusable(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Returns the RenderingPanel stored in the Frame so it can be used other
	 * places
	 *
	 * @return The rendering panel
	 */
	public RenderingPanel getRender() {
		return render;
	}

	/**
	 * Sets up the menu bar at the top of the GameFrame.
	 */
	private void setupMenuBar() {
		JMenuBar menu = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(this);
		file.add(quit);
		menu.add(file);
		setJMenuBar(menu);
	}

	/**
	 * Used to prompt the user with the decision to quit the game. If they
	 * choose yes, the game is exited.
	 */
	private void quitGame() {
		int result = JOptionPane.showOptionDialog(this,
				"Are you sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	// key listener methods

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String action = null;
		if (source instanceof JMenuItem) {
			action = ((JMenuItem) source).getText();
		}
		if (action.equals("Quit")) {
			quitGame();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
			direction = NORTH;
		} else if (e.getKeyCode() == KeyEvent.VK_A
				|| e.getKeyCode() == KeyEvent.VK_LEFT) {
			// System.out.println("pressed A");
			direction = (direction == WEST) ? NORTH : direction + 1;
		} else if (e.getKeyCode() == KeyEvent.VK_S
				|| e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = SOUTH;
		} else if (e.getKeyCode() == KeyEvent.VK_D
				|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// System.out.println("pressed D");
			direction = (direction == NORTH) ? WEST : direction - 1;
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			quitGame();
		}
		// render.setDirection(direction);
		// render.repaint();

		if (direction == NORTH) {
			System.out.println("facing north");
		}
		if (direction == EAST) {
			System.out.println("facing east");
		}
		if (direction == SOUTH) {
			System.out.println("facing south");
		} else if (direction == WEST) {
			System.out.println("facing west");
		}

	}

	// unneeded method
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Add a GameEventListener to this GameFrame.
	 *
	 * @param gel Game event listener
	 */
	public void addGameEventListener(GameEventListener gel) {
		geb.addGameEventListener(gel);
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
		Component c = e.getComponent();
		if(c instanceof RenderingPanel){
			text.append("Entered the Rendering Panel\n");
		}
		else if(c instanceof StatsPanel){
			text.append("Entered the Stats Panel\n");
		}
		else if(c instanceof EquipPanel){
			text.append("Entered the Equipment Panel\n");
		}
		else if(c instanceof InventoryPanel){
			text.append("Entered the Inventory Panel\n");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
