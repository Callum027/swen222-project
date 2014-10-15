package game.ui.rendering;

import game.exceptions.GameException;
import game.ui.GameComponent;
import game.ui.GameFrame;
import game.world.Area;
import game.world.BoundingBox;
import game.world.Drawable;
import game.world.Position;
import game.world.characters.Enemy;
import game.world.characters.Player;
import game.world.characters.classes.GameClass;
import game.world.events.DropItemEvent;
import game.world.events.InteractEvent;
import game.world.events.MoveEvent;
import game.world.items.Container;
import game.world.items.Door;
import game.world.items.Item;
import game.world.items.MoveableItem;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;


public class RenderingPanel extends JPanel implements GameComponent {

	private static final long serialVersionUID = 1L;

	// fields
	private final int WIDTH = 800;
	private final int HEIGHT = 560;
	private final int DX = (FloorTile.WIDTH / 2) + 1;
	private final int DY = FloorTile.HEIGHT / 2;

	private Area area;
	private List<BoundingBox> tileBoundingBoxes;
	private List<BoundingBox> drawableBoundingBoxes;

	private int areaLength;
	private int areaWidth;
	private int areaHeight;
	private int startX;
	private int startY;
	private int direction;

	private Player player;

	/**
	 * Constructor:
	 * Constructs an instance of RenderingPanel
	 *
	 * @param
	 * 		--- the directon of the game view
	 */
	public RenderingPanel(int direction) {
		super();
		this.direction = direction;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		player = new Player(new Position(0,0), "Frank", 1, GameClass.CharacterClass.WARRIOR);
	}

	/**
	 * Sets the area currently active on the RenderingWindow to the specified
	 * Area.
	 *
	 * @param area
	 * 		--- current area
	 */
	public void setArea(Area area) {
		this.area = area;
		calculateConstants();
	}

	/**
	 * Set the direction of the game view to the specified direction. The
	 * specified value must be equal to a valid direction as defined by the
	 * GameFrame class. Therefore the value must be equal to either NORTH, EAST,
	 * SOUTH or WEST.
	 *
	 * @param direction
	 * 		--- the direction of the game view
	 */
	public void setDirection(int direction) {
		if (direction >= GameFrame.NORTH && direction <= GameFrame.WEST) {
			this.direction = direction;
		}
	}

	@Override
	public void repaint() {
		if (area != null) {
			calculateConstants();
		}
		super.repaint();
	}

	/**
	 * Paints the current area to the render panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(150, 150, 150));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if (area != null) {
			Tile[][] tiles = area.getTiles();
			List<Item> items = new ArrayList<Item>(area.getItems().values());
			List<Enemy> enemies = new ArrayList<Enemy>(area.getEnemies().values());
			List<Player> players = new ArrayList<Player>(area.getPlayers().values());
			List<Drawable> toDraw = new ArrayList<Drawable>(items);
			toDraw.addAll(enemies);
			toDraw.addAll(players);
			drawFloors(g, tiles, items);
			drawDrawableObjects(g, toDraw, tiles);
		}
	}

	/**
	 * Draw the floor of the current area to the render panel.
	 */
	private void drawFloors(Graphics g, Tile[][] tiles, List<Item> items) {
		if (direction == GameFrame.NORTH) {
			drawNorthFloorLayout(g, tiles);
		} else if (direction == GameFrame.EAST) {
			drawEastFloorLayout(g, tiles);
		} else if (direction == GameFrame.SOUTH) {
			drawSouthFloorLayout(g, tiles);
		} else if (direction == GameFrame.WEST) {
			drawWestFloorLayout(g, tiles);
		}
	}

	/**
	 * Draws the floor layout of the area when the game view is facing north.
	 *
	 * @param g
	 *            --- the graphics pane to draw onto
	 * @param tiles
	 *            --- tiles representing the floor layout
	 */

	private void drawNorthFloorLayout(Graphics g, Tile[][] tiles) {
		tileBoundingBoxes = new ArrayList<BoundingBox>();
		for (int i = 0; i < tiles.length; i++) {
			int x = startX - (DX * i);
			int y = startY + (DY * i);
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j].draw(g, x, y, direction);
				tileBoundingBoxes.add(tiles[j][i].getBoundingBox(x, y, new Position(j, i)));
				x += DX;
				y += DY;
			}
		}
	}

	/**
	 * Draws the floor layout of the area when the game view is facing east.
	 *
	 * @param g
	 *            --- the graphics pane to draw onto
	 * @param tiles
	 *            --- tiles representing the floor layout
	 */
	private void drawEastFloorLayout(Graphics g, Tile[][] tiles) {
		tileBoundingBoxes = new ArrayList<BoundingBox>();
		int startI = tiles[0].length - 1;
		for (int i = startI; i >= 0; i--) {
			int x = startX - (DX * (startI - i));
			int y = startY + (DY * (startI - i));
			for (int j = 0; j < tiles.length; j++) {
				tiles[j][i].draw(g, x, y, direction);
				tileBoundingBoxes.add(tiles[j][i].getBoundingBox(x, y,
						new Position(i, j)));
				x += DX;
				y += DY;
			}
		}
	}

	/**
	 * Draws the floor layout of the area when the game view is facing south.
	 *
	 * @param g
	 *            --- the graphics pane to draw onto
	 * @param tiles
	 *            --- tiles representing the floor layout
	 */
	private void drawSouthFloorLayout(Graphics g, Tile[][] tiles) {
		tileBoundingBoxes = new ArrayList<BoundingBox>();
		int startI = tiles.length - 1;
		for (int i = startI; i >= 0; i--) {
			int x = startX - (DX * (startI - i));
			int y = startY + (DY * (startI - i));
			for (int j = tiles[i].length - 1; j >= 0; j--) {
				tiles[i][j].draw(g, x, y, direction);
				tileBoundingBoxes.add(tiles[i][j].getBoundingBox(x, y,
						new Position(j, i)));
				x += DX;
				y += DY;
			}
		}
	}

	/**
	 * Draws the floor layout of the area when the game view is facing west.
	 *
	 * @param g
	 *            --- the graphics pane to draw onto
	 * @param tiles
	 *            --- tiles representing the floor layout
	 */
	private void drawWestFloorLayout(Graphics g, Tile[][] tiles) {
		tileBoundingBoxes = new ArrayList<BoundingBox>();
		for (int i = 0; i < tiles[0].length; i++) {
			int x = startX - (DX * i);
			int y = startY + (DY * i);
			for (int j = tiles.length - 1; j >= 0; j--) {
				tiles[j][i].draw(g, x, y, direction);
				tileBoundingBoxes.add(tiles[j][i].getBoundingBox(x, y,
						new Position(i, j)));
				x += DX;
				y += DY;
			}
		}
	}

	private void sortDrawableObjects(List<Drawable> drawable, int width, int height){
		// sort toDraw based on the current direction
		Comparator<Drawable> comp = null;
		if(direction == GameFrame.NORTH){
			comp = new NorthComparator(height, width);
		}
		else if(direction == GameFrame.EAST){
			comp = new EastComparator(height, width);
		}
		else if(direction == GameFrame.SOUTH){
			comp = new SouthComparator(height, width);
		}
		else if(direction == GameFrame.WEST){
			comp = new WestComparator(height, width);
		}
		Collections.sort(drawable, comp);
	}

	/**
	 * Draws all the Drawable objects in the area to the RenderingPanel and calculates
	 * BoundingBoxes for each object.
	 *
	 * @param g
	 * 		--- graphics object to draw on
	 * @param toDraw
	 * 		--- list of drawable objects
	 * @param tiles
	 * 		--- 2D array of tiles
	 */
	private void drawDrawableObjects(Graphics g, List<Drawable> toDraw, Tile[][] tiles) {
		// sort toDraw based on the current direction
		sortDrawableObjects(toDraw, tiles[0].length, tiles.length);

		drawableBoundingBoxes = new ArrayList<BoundingBox>();

		int width = area.getTiles()[0].length - 1;
		int height = area.getTiles().length - 1;
		// start iteration from back to draw items further away first
		for(int i = toDraw.size() - 1; i >= 0; i--){
			Drawable d = toDraw.get(i);
			int x = 0;
			int y = 0;
			int yOffset = d.getHeight() * FloorTile.HEIGHT;
			Position p = d.getPosition();
			if (direction == GameFrame.NORTH) {
				x = startX + DX * (p.getX() - p.getY());
				y = startY + DY * (p.getX() + p.getY()) - yOffset;
			}
			else if (direction == GameFrame.EAST) {
				x = startX + DX * (p.getY() - (width - p.getX()));
				y = startY + DY * ((width - p.getX()) + (p.getY())) - yOffset;
			}
			else if (direction == GameFrame.SOUTH) {
				x = startX + DX * (p.getY() - p.getX());
				y = startY + DY * ((width - p.getX()) + (height - p.getY())) - yOffset;
			}
			else if (direction == GameFrame.WEST) {
				x = startX + DX * ((width - p.getX()) - p.getY());
				y = startY + DY * (p.getX() + (height - p.getY())) - yOffset;
			}

			d.draw(g, x, y, direction);
			drawableBoundingBoxes.add(d.getBoundingBox(x, y, p));
		}
	}

	/**
	 * Calculates the constant values that are used regularly in rendering the
	 * Area view.
	 */
	private void calculateConstants() {
		areaLength = (area.getTiles().length + area.getTiles()[0].length) - 1;
		areaWidth = (((areaLength - 1) * ((FloorTile.WIDTH / 2) + 1)))
				+ FloorTile.WIDTH;
		areaHeight = ((areaLength - 1) * (FloorTile.HEIGHT / 2)) - 135;
		startX = (WIDTH - areaWidth) / 2;
		// adjust start x so that the rendered outcome is centered in the
		// graphics pane
		if (direction == GameFrame.NORTH || direction == GameFrame.SOUTH) {
			startX += ((area.getTiles().length - 1) * DX);
		} else if (direction == GameFrame.EAST || direction == GameFrame.WEST) {
			startX += ((area.getTiles()[0].length - 1) * DX);
		}
		startY = (HEIGHT - areaHeight) / 2;
	}

	/**
	 * Find the position in the area that the player has clicked on, based on
	 * the coordinates of their click. Returns null if they have not clicked in
	 * the area.
	 *
	 * @param p
	 * 		---the point where the mouse click occurred
	 * @return
	 * 		---the position that the click corresponds to, null if not in area
	 */
	public Position findPosition(Position p) {
		for (BoundingBox box : tileBoundingBoxes) {
			if (box.contains(new Point(p.getX(), p.getY()))) {
				return box.getPosition();
			}
		}
		return null;
	}

	// methods inherited from GameComponent

	/**
	 * Deals with mouse events when the mouse  is clicked the RenderingPanel.
	 */
	@Override
	public void mouseClicked(GameFrame frame, MouseEvent e) {
		// check whether the left or right mouse button was clicked
		if (e.getButton() == MouseEvent.BUTTON3) {
			mouseRightClicked(frame, e);
		}
		else if(e.getButton() == MouseEvent.BUTTON1){
			mouseLeftClicked(frame, e);
		}
	}

	/**
	 * Deals with mouse events when the mouse is pressed on RenderingPanel.
	 */
	@Override
	public void mousePressed(GameFrame frame, MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Position pressed = new Position(x, y);
		Position p = findPosition(pressed);
		for(Item item : area.getItems().values()){
			if(item.getPosition().equals(p) && item instanceof MoveableItem){
				// NEEDS TO BE A GAME EVENT
				frame.setSelectedItem((MoveableItem)item);
				frame.append(frame.getSelectedItem().toString());
				area.removeItem(item);
				repaint();
				break;
			}
		}
	}

	/**
	 * Deals with mouse events when the mouse is released on RenderingPanel.
	 */
	@Override
	public void mouseReleased(GameFrame frame, MouseEvent e){
		// only
		if(frame.getSelectedItem() != null){
			Position release = new Position(e.getX(), e.getY());
			Position p = findPosition(release);
			if(p == null){
				return;
			}
			// check to make sure that the position is clear before dropping item
			boolean positionClear = true;
			for(Item item : area.getItems().values()){
				if(item.getPosition().equals(p)){
					positionClear = false;
					break;
				}
			}
			if(positionClear){
				DropItemEvent drop = new DropItemEvent(frame.getSelectedItem(), p, area.getID());
				try{
					frame.broadcastGameEvent(drop);
				} catch (GameException e1){
					frame.returnItem();
				}
				finally{
					frame.setSelectedItem(null);
					frame.setFrom(null);
				}
				repaint();
			}
		}
	}

	/**
	 * Helper method for mouseClicked that deals with left mouse button clicks.
	 * Finds the drawable object that was clicked on, if any.
	 *
	 * @param frame
	 * 		--- the game frame
	 * @param e
	 * 		--- the generated mouse event
	 */
	private void mouseLeftClicked(GameFrame frame, MouseEvent e){
		for(BoundingBox b : drawableBoundingBoxes){
			if(b.contains(new Point(e.getX(), e.getY()))){
				Drawable drawable = area.getDrawableObject(b.getPosition());
				interact(frame, drawable);
			}
		}
	}

	/**
	 * Helper method for mouseClicked that deals with right mouse button clicks.
	 *
	 *
	 * @param frame
	 * 		--- the game frame
	 * @param e
	 * 		--- the generated mouse event
	 */
	private void mouseRightClicked(GameFrame frame, MouseEvent e){
		Position p = findPosition(new Position(e.getX(), e.getY()));
		if (p != null) {
			Position current = player.getPosition();
			Stack<Position> moves = area.findPath(current, p);
			if(!moves.isEmpty()){
				moves.pop();
			}
			if(!moves.isEmpty()){
				MoveEvent move = new MoveEvent(moves.pop(), player);
				try {
					frame.broadcastGameEvent(move);
				} catch (GameException e1) {

				}
			}
			repaint();
		}
	}

	private void interact(GameFrame frame, Drawable drawable){
		if (drawable instanceof Item) {
			if(drawable instanceof Door){
				frame.setChestVisible(false);
				frame.setStatsVisible(true);
				frame.append("Interacted with a door.");
			}
			else if(drawable instanceof Container){
				Container cont = (Container) drawable;
				frame.setStatsVisible(false);
				frame.addChestContents(cont.getLoot(), cont);
				//cont.setLoot(new ArrayList<MoveableItem>(Arrays.asList(frame.getChestItems())));
				frame.setChestVisible(true);

				frame.append("It is a chest!");
			}
			else if(drawable instanceof Item){
				frame.setChestVisible(false);
				frame.setStatsVisible(true);
				frame.append("Interaction Occurred");
			}

			// Broadcast the game event to all other peers.
			try {
				frame.broadcastGameEvent(new InteractEvent(player, ((Item)drawable)));
			} catch (GameException e) {

			}
		}
	}

	/**
	 * A private class that is used to sort Items in an area so that when the
	 * game view is facing north, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	public class NorthComparator implements Comparator<Drawable> {

		// field
		private int length;

		/**
		 * Constructor: Constructs a NorthComparator, which takes a width and
		 * height so that the length of the area can be calculated.
		 *
		 * @param width
		 *            --- width of area
		 * @param height
		 *            --- height of area
		 */
		public NorthComparator(int width, int height) {
			length = width + height - 1;
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing north. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {
			Position p1 = o1.getPosition();
			Position p2 = o2.getPosition();
			int i = length - (p1.getX() + p1.getY());
			int j = length - (p2.getX() + p2.getY());
			return i - j;
		}
	}

	/**
	 * A private class that is used to sort Items in an area so that when the
	 * game view is facing east, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	public class EastComparator implements Comparator<Drawable> {

		// field
		private int length;
		private int width;

		/**
		 * Constructor: Constructs a EastComparator, which takes a width and
		 * height so that the length of the area can be calculated.
		 *
		 * @param width
		 *            --- width of area
		 * @param height
		 *            --- height of area
		 */
		public EastComparator(int width, int height) {
			length = width + height - 1;
			this.width = width;
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing east. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {
			Position p1 = o1.getPosition();
			Position p2 = o2.getPosition();
			int i = length - ((width - p1.getX()) + p1.getY());
			int j = length - ((width - p2.getX()) + p2.getY());
			return i - j;
		}
	}

	/**
	 * A private class that is used to sort Items in an area so that when the	private Area area;
	private Image[] images;
	private boolean locked;

	public Door(int ID, String name, Area area, boolean locked){
		this.ID = ID;
		this.name = name;
		this.area = area;
		this.locked = locked;
	}
}
	 * game view is facing south, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	public class SouthComparator implements Comparator<Drawable> {

		// field
		private int length;

		/**
		 * Constructor: Constructs a SouthComparator, which takes a width and
		 * height so that the length of the area can be calculated.
		 *
		 * @param width
		 *            --- width of area
		 * @param height
		 *            --- height of area
		 */
		public SouthComparator(int width, int height) {
			length = width + height - 1;
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing south. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {
			Position p1 = o1.getPosition();
			Position p2 = o2.getPosition();
			int i = length - (p1.getX() + p1.getY());
			int j = length - (p2.getX() + p2.getY());
			return j - i;
		}
	}

	/**
	 * A private class that is used to sort Items in an area so that when the
	 * game view is facing west, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	public class WestComparator implements Comparator<Drawable> {

		// field
		private int length;
		private int height;

		/**
		 * Constructor: Constructs a WestComparator, which takes a width and
		 * height so that the length of the area can be calculated.
		 *
		 * @param width
		 *            --- width of area
		 * @param height
		 *            --- height of area
		 */
		public WestComparator(int width, int height) {
			length = width + height - 1;
			this.height = height;
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing west. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {
			Position p1 = o1.getPosition();
			Position p2 = o2.getPosition();
			int i = length - (p1.getX() + (height - p1.getY()));
			int j = length - (p2.getX() + (height - p2.getY()));
			return i - j;
		}
	}

	@Override
	public int addItem(MoveableItem selectedItem) {
		return -1;
	}
}

