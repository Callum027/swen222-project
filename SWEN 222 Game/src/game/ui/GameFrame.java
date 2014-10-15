package game.ui;

import game.Main;
import game.exceptions.GameException;
import game.ui.application.*;
import game.ui.rendering.RenderingPanel;
import game.world.GameEvent;
import game.world.GameEventBroadcaster;
import game.world.GameEventListener;
import game.world.items.MoveableItem;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A custom JFrame used to hold all the panels of the game. Implements
 * ActionListener and KeyListener so it can respond to the correct inputs from
 * the user.
 *
 * @author Harry
 *
 */
public class GameFrame extends JFrame implements ActionListener, KeyListener,
		MouseListener {

	private enum State {
		START, IN_GAME
	};

	private static final long serialVersionUID = 1L;

	private State state = State.START;

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
	private ChestPanel chest;
	private StartPanel start;

	private JPanel renderPane;
	private JPanel appPane;

	private GameComponent from;
	private MoveableItem selectedItem;
	/*
	 * selectedItem is the item that has been selected in one of the panels and
	 * is stored here so that it can be easily moved between panels.
	 */
	private int direction;
	private int playerID;

	private Component currentComponent = null;

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

		start = new StartPanel(this);
		add(start);

		setupMenuBar();
		direction = NORTH;
		// set the frame to have a layout so that the screens are in proportion

		// setup the components of the game frame
		render = new RenderingPanel(direction);
		text = new JTextArea(7, 49);
		scroll = new JScrollPane(text);
		inventory = new InventoryPanel();
		equip = new EquipPanel(inventory);
		stats = new StatsPanel(equip, inventory);
		chest = new ChestPanel(90);
		inventory.setEquip(equip);
		equip.setStats(stats);
		renderPane = new JPanel();
		appPane = new JPanel();
		addMouseListener(this);
		addKeyListener(this);

		setFocusable(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

	public MoveableItem getSelectedItem() {
		return selectedItem;
	}

	public int getPlayerID(){
		return playerID;
	}

	public void setSelectedItem(MoveableItem item) {
		selectedItem = item;
	}

	/**
	 * Sets up the menu bar at the top of the GameFrame.
	 */
	private void setupMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenuItem menu = new JMenuItem("Menu");
		JMenuItem quit = new JMenuItem("Quit");
		menu.addActionListener(this);
		quit.addActionListener(this);
		file.add(menu);
		file.add(quit);
		menuBar.add(file);
		setJMenuBar(menuBar);
	}

	/**
	 * Called when either New Game or Menu is pressed. Changes the game state
	 * from one to the other so it knows to display either the main menu or the
	 * game view.
	 *
	 * @param s
	 *            The state to change to
	 */
	public void changeState(State s) {
		if (s == state) {
			return;
		}
		if (s == State.START) {
			remove(renderPane);
			remove(appPane);
			setupStartState();
		} else if (s == State.IN_GAME) {
			remove(start);
			setupInGameState();
		}
		state = s;
		pack();
	}

	/**
	 * Sets up the start state when the game is initially called. It is also
	 * used when the Menu is selected from the File menu.
	 */
	public void setupStartState() {
		add(start);
	}

	/**
	 * Sets up the game state, that is the state that the actual game can be
	 * played in. This is called when New Game is pressed from the start menu
	 */
	public void setupInGameState() {
		// setup the render pane
		renderPane = new JPanel();
		renderPane.addMouseListener(this);
		renderPane.setLayout(new BoxLayout(renderPane, BoxLayout.Y_AXIS));
		renderPane.add(render);
		render.addMouseListener(this);
		renderPane.add(scroll);
		renderPane.addMouseListener(this);

		// setup app pane
		appPane = new JPanel();
		appPane.setLayout(new BoxLayout(appPane, BoxLayout.Y_AXIS));

		stats.addMouseListener(this);
		appPane.add(equip);
		equip.addMouseListener(this);
		appPane.add(inventory);
		appPane.add(stats);
		appPane.add(chest);
		chest.addMouseListener(this);
		stats.setVisible(true);
		chest.setVisible(false);
		inventory.addMouseListener(this);
		appPane.addMouseListener(this);

		// setup the game frame layout
		setLayout(new FlowLayout());
		add(renderPane);
		add(appPane);
	}

	@Override
	public void removeAll() {
		remove(start);
		remove(renderPane);
		remove(appPane);
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
		} else if (source instanceof JButton) {
			action = ((JButton) source).getText();
		}

		if (action.equals("Menu")) {
			changeState(State.START);
		} else if (action.equals("Quit")) {
			quitGame();
		}
		else if (action.equals("New Game")) {
			String name = start.getNameText();
			playerID = Main.getClient().getPlayerID();
			Main.getClient().connect();
			changeState(State.IN_GAME);
		}
		else if(action.equals("Host New Game")){

		}
		else if(action.equals("Join Game")){

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
		render.setDirection(direction);
		render.repaint();

		if (direction == NORTH) {
			text.append("Facing north\n");
		} else if (direction == EAST) {
			text.append("Facing east\n");
		} else if (direction == SOUTH) {
			text.append("facing south\n");
		} else if (direction == WEST) {
			text.append("Facing west\n");
		}

	}

	// unneeded method
	@Override
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Add a GameEventListener to this GameFrame.
	 *
	 * @param gel
	 *            Game event listener
	 */
	public void addGameEventListener(GameEventListener gel) {
		geb.addGameEventListener(gel);
	}

	/**
	 * Finds and returns the current GameComponent that has focus on the
	 * GameFrame.
	 *
	 * @return --- GameComponent currently in focus
	 */
	public GameComponent getCurrentGameComponent() {
		if (currentComponent instanceof RenderingPanel) {
			return render;
		} else if (currentComponent instanceof InventoryPanel) {
			return inventory;
		} else if (currentComponent instanceof EquipPanel) {
			return equip;
		} else if (currentComponent instanceof StatsPanel && stats.isVisible()) {
			return stats;
		} else if (currentComponent instanceof ChestPanel && chest.isVisible()) {
			return chest;
		}
		return null;
	}

	public void broadcastGameEvent(GameEvent ge) throws GameException {
		geb.broadcastGameEvent(ge);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GameComponent current = getCurrentGameComponent();
		if (current != null) {
			current.mouseClicked(this, e);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		GameComponent current = getCurrentGameComponent();
		if (current != null) {
			current.mousePressed(this, e);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		if (c instanceof GameComponent) {
			if (c instanceof EquipPanel) {
				int x = e.getXOnScreen();
				int y = e.getY();
				e = new MouseEvent(c, e.getID(), e.getWhen(), e.getModifiers(),
						x, y, e.getClickCount(), e.isPopupTrigger());
			} else if (c instanceof InventoryPanel) {
				int x = e.getXOnScreen();
				int y = EquipPanel.HEIGHT + e.getY();
				e = new MouseEvent(c, e.getID(), e.getWhen(), e.getModifiers(),
						x, y, e.getClickCount(), e.isPopupTrigger());
			} else if (c instanceof ChestPanel) {
				int x = e.getXOnScreen();
				int y = EquipPanel.HEIGHT + InventoryPanel.HEIGHT + e.getY();
				e = new MouseEvent(c, e.getID(), e.getWhen(), e.getModifiers(),
						x, y, e.getClickCount(), e.isPopupTrigger());
			}
			GameComponent current = getCurrentGameComponent();
			if (current != null) {
				current.mouseReleased(this, e);
			}
		} else {
			e.consume();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		currentComponent = e.getComponent();
		if (currentComponent instanceof RenderingPanel) {
			// text.append("Entered the Rendering Panel\n");
		} else if (currentComponent instanceof StatsPanel) {
			// text.append("Entered the Stats Panel\n");
		} else if (currentComponent instanceof EquipPanel) {
			// text.append("Entered the Equipment Panel\n");
		} else if (currentComponent instanceof InventoryPanel) {
			// text.append("Entered the Inventory Panel\n");
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public void append(String message) {
		text.append(message + "\n");
	}

	/**
	 * Used to set the stats panel visible or invisible
	 *
	 * @param b
	 *            Where true sets it visible and false sets in invisible
	 */
	public void setStatsVisible(boolean b) {
		stats.setVisible(b);
	}

	/**
	 * Used to set the chest panel visible or invisible
	 *
	 * @param b
	 *            Where true sets it visible and false sets in invisible
	 */
	public void setChestVisible(boolean b) {
		chest.setVisible(b);
	}

	public void returnItem() {
		if(from !=null){
			from.addItem(selectedItem);
		}
		from = null;
	}

	public GameComponent getFrom() {
		return from;
	}

	public void setFrom(GameComponent from) {
		this.from = from;
	}

	public void addChestContents(List<MoveableItem> loot) {
		for(MoveableItem i : loot){
			chest.addItem(i);
		}

	}

	public MoveableItem[] getChestItems() {
		return chest.getChest();
	}



}

