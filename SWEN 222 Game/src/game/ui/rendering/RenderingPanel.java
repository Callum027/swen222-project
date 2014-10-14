package game.ui.rendering;

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
import game.world.events.MoveEvent;
import game.world.items.Door;
import game.world.items.Furniture;
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
	private Furniture shelf;

	/**
	 * Constructor:
	 *
	 */
	public RenderingPanel(int direction) {
		super();
		this.direction = direction;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		player = new Player(new Position(0,0), "Frank", 1, GameClass.playerClass.WARRIOR);
		shelf = new Furniture(new Position(5, 5), 2, 1, "shelf", null);

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
		area.addItem(shelf);
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
			List<Drawable> toDraw = new ArrayList<Drawable>(items);
			toDraw.addAll(enemies);
			drawFloors(g, tiles, items);
			drawDrawableObjects(g, toDraw, tiles);
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

	private void drawDrawableObjects(Graphics g, List<Drawable> toDraw, Tile[][] tiles) {
		// sort toDraw based on the current direction
		sortDrawableObjects(toDraw, tiles[0].length, tiles.length);

		drawableBoundingBoxes = new ArrayList<BoundingBox>();
		
		// start iteration from back to draw items further away first
		int width = area.getTiles()[0].length - 1;
		int height = area.getTiles().length - 1;
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
	
	public void drawBoundingBoxes(Graphics g){
		g.setColor(Color.cyan);
		for(BoundingBox b : drawableBoundingBoxes){
			g.fillPolygon(b);
		}
	}

	/**
	 * Draw the floor of the current area to the render panel.
	 */
	private void drawFloors(Graphics g, Tile[][] tiles, List<Item> items) {
		//Comparator<Item> comp = null;
		if (direction == GameFrame.NORTH) {
			//comp = new NorthComparator(tiles.length, tiles[0].length);
			drawNorthFloorLayout(g, tiles);
		} else if (direction == GameFrame.EAST) {
			//comp = new EastComparator(tiles.length, tiles[0].length);
			drawEastFloorLayout(g, tiles);
		} else if (direction == GameFrame.SOUTH) {
			//comp = new SouthComparator(tiles.length, tiles[0].length);
			drawSouthFloorLayout(g, tiles);
		} else if (direction == GameFrame.WEST) {
			//comp = new WestComparator(tiles.length, tiles[0].length);
			drawWestFloorLayout(g, tiles);
		}
		//Collections.sort(items, comp);
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
						new Position(j, i)));
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
						new Position(i, j)));
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
						new Position(j, i)));
				x += DX;
				y += DY;
			}
		}
	}
	@Override
	public void repaint() {
		if (area != null) {
			calculateConstants();
		}
		super.repaint();
	}

	// comparators for sorting items

	/**
	 * A private class that is used to sort Items in an area so that when the
	 * game view is facing north, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	private class NorthComparator implements Comparator<Drawable> {

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
			if(p1 == null){
				System.out.println("P2 is null");
			}
			int i = length - (int)(p1.getX() - p1.getY());
			int j = length - (int)(p2.getX() - p2.getY());
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
	private class EastComparator implements Comparator<Drawable> {

		// field
		private int length;

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
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing east. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {

			return 0;
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
	private class SouthComparator implements Comparator<Drawable> {

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
			if(p1 == null){
				System.out.println("P2 is null");
			}
			int i = length - (int)(p1.getX() - p1.getY());
			int j = length - (int)(p2.getX() - p2.getY());
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
	private class WestComparator implements Comparator<Drawable> {

		// field
		private int length;

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
		}

		/**
		 * Compares two Items to determine which one is closer while the game
		 * view is facing west. Returns a negative integer if Item o1 is closer
		 * than o2, zero if item o1 is at the same distance as o2 and a position
		 * integer if o1 is further away than o2.
		 *
		 */
		public int compare(Drawable o1, Drawable o2) {

			return 0;
		}
	}

	@Override
	public void mouseClicked(GameFrame frame, MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			mouseRightClicked(frame, e);
		}
		else if(e.getButton() == MouseEvent.BUTTON1){
			mouseLeftClicked(frame, e);
		}
	}
	
	private void mouseLeftClicked(GameFrame frame, MouseEvent e){
		for(BoundingBox b : drawableBoundingBoxes){
			if(b.contains(new Point(e.getX(), e.getY()))){
				Drawable drawable = area.getDrawableObject(b.getPosition());
				interact(frame, drawable);
			}
		}
	}
	
	private void interact(GameFrame frame, Drawable drawable){
		if(drawable instanceof Door){
			((Door)drawable).interact(player, frame.getGameEventBroadcaster());
			frame.append("Interacted with a door.");
		}
		else if(drawable instanceof Item){
			((Item)drawable).interact(player);
			frame.append("Interaction Occurred");
		}
	}
	
	private void mouseRightClicked(GameFrame frame, MouseEvent e){
		Position p = findPosition(new Position(e.getX(), e.getY()));
		if (p != null) {
			frame.append("Clicked at position: "+p);
			Position current = player.getPosition();
			Stack<Position> moves = area.findPath(current, p);
			if(!moves.isEmpty()){
				moves.pop();
			}
			if(!moves.isEmpty()){
				MoveEvent move = new MoveEvent(moves.pop(), player);
				frame.getGameEventBroadcaster().broadcastGameEvent(move);
			}
			repaint();
		}
	}

	// unneeded game component methods
	@Override
	public void mouseReleased(GameFrame frame, MouseEvent e) {
		if(frame.getSelectedItem() != null){
			Position release = new Position(e.getX(), e.getY());
			Position p = findPosition(release);
			if(p == null){
				return;
			}
			boolean positionClear = true;
			for(Item item : area.getItems().values()){
				if(item.getPosition().equals(p)){
					positionClear = false;
					break;
				}
			}
			if(positionClear){
				DropItemEvent drop = new DropItemEvent(frame.getSelectedItem(), p, area.getID());
				frame.getGameEventBroadcaster().broadcastGameEvent(drop);
				frame.setSelectedItem(null);
				repaint();
			}
		}
	}

	@Override
	public void mousePressed(GameFrame frame, MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Position pressed = new Position(x, y);
		Position p = findPosition(pressed);
		for(Item item : area.getItems().values()){
			if(item.getPosition().equals(p) && item instanceof MoveableItem){
				frame.setSelectedItem((MoveableItem)item);
				frame.append(frame.getSelectedItem().toString());
				area.removeItem(item);
				repaint();
				break;
			}
		}
	}
}

