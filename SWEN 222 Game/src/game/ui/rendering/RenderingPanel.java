package game.ui.rendering;

import game.Main;
import game.ui.GameFrame;
import game.world.Area;
import game.world.BoundingBox;
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
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JPanel;

public class RenderingPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	// fields
	private final int WIDTH = 960;
	private final int HEIGHT = 720;
	private final int DX = (FloorTile.WIDTH / 2) + 1;
	private final int DY = FloorTile.HEIGHT / 2;

	private Area area;
	private List<BoundingBox> tileBoundingBoxes;
	private List<BoundingBox> itemBoundingBoxes;

	private int areaLength;
	private int areaWidth;
	private int areaHeight;
	private int startX;
	private int startY;
	private int direction;

	private Furniture test;

	/**
	 * Constructor:
	 *
	 */
	public RenderingPanel(int direction) {
		super();
		this.direction = direction;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		test = new Furniture(new Point(0,0),  2, "SpriteTEST", null);

	}

	/**
	 * Sets the area currently active on the RenderingWindow to the specified
	 * Area.
	 *
	 * @param area
	 *            --- current area
	 */
	public void setArea(Area area) {
		this.area = area;
		calculateConstants();
		// calculateBoundingBoxes();
	}

	/**
	 * Set the direction of the game view to the specified direction. The
	 * specified value must be equal to a valid direction as defined by the
	 * GameFrame class. Therefore the value must be equal to either NORTH, EAST,
	 * SOUTH or WEST.
	 *
	 * @param direction
	 *            --- the direction of the game view
	 */
	public void setDirection(int direction) {
		if (direction >= GameFrame.NORTH && direction <= GameFrame.WEST) {
			this.direction = direction;
		}
	}

	private void calculateBoundingBoxes(Tile[][] tiles) {
		tileBoundingBoxes = new ArrayList<BoundingBox>();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {

			}
		}
	}

	private Point rotatePosition(Point p, int width, int height, int count) {
		if (count < direction) {
			int length = (count % 2 == 0) ? height : width;
			int y = p.x;
			int x = length - p.y - 1;
			// swap height and width around to simulate array rotating
			rotatePosition(new Point(x, y), height, width, count + 1);
		}
		return p;
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
	 * Calculates the bounding boxes for each tile in the game.
	 */
	/*
	 * public void calculateBoundingBoxes() { //tileBoundingBoxes = new
	 * Polygon[area.getTiles().length][area.getTiles()[0].length];
	 * itemBoundingBoxes = new
	 * Polygon[area.getTiles().length][area.getTiles()[0].length];
	 *
	 * for (int i = 0; i < tileBoundingBoxes.length; i++) { int x = startX - (DX
	 * * i); int y = startY + (DY * i); for (int j = 0; j <
	 * tileBoundingBoxes[i].length; j++) { tileBoundingBoxes[i][j] =
	 * area.getTiles()[i][j].getBoundingBox(x, y);
	 *
	 * // calculate x and y point for bounding box of item if
	 * (area.getItems()[i][j] != null) { int itemHeight =
	 * area.getItems()[i][j].getHeight(); int itemY = y - (itemHeight *
	 * FloorTile.HEIGHT); int[] xPoints = new int[] { x, x + (FloorTile.WIDTH /
	 * 2), x + FloorTile.WIDTH, x + FloorTile.WIDTH, x + (FloorTile.WIDTH / 2),
	 * x }; int[] yPoints = new int[] { itemY + DY, itemY, itemY + DY, itemY +
	 * ((itemHeight + 1) * FloorTile.HEIGHT) - DY, itemY + ((itemHeight + 1) *
	 * FloorTile.HEIGHT), itemY + ((itemHeight + 1) * FloorTile.HEIGHT) - DY };
	 * itemBoundingBoxes[i][j] = new Polygon(xPoints, yPoints, xPoints.length);
	 * } x += DX; y += DY; } } }
	 */

	/**
	 * Find the position in the area that the player has clicked on, based on
	 * the coordinates of their click. Returns null if they have not clicked in
	 * the area.
	 *
	 * @param p
	 *            : the point where the mouse click occured
	 * @return the position that the click corresponds to, null if not in area
	 */
	public Point findPosition(Point p) {
		for (BoundingBox box : tileBoundingBoxes) {
			if (box.contains(p)) {
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
			List<Item> items = area.getItems();
			drawFloors(g, tiles, items);
			drawBoundingBoxes(g);
			// draw test
			// Point draw = rotatePosition(new Point(test.getX(), test.getY()),
			// tiles[0].length, tiles.length, 0);
			// Point draw = new Point(test.getX(), test.getY());
			// System.out.println(draw);
			// int x = startX + (DX * draw.y) - (draw.x * DX);
			// int y = startY + (DY * (draw.x + draw.y)) - (test.getHeight() *
			// FloorTile.HEIGHT);
			// test.draw(g, x, y);
			// drawTileBoxes(g);
			drawCharacter(g);
		}
	}

	private void drawCharacter(Graphics g) {
		int x = 0;
		int y = 0;
		Point draw = test.getPosition();
			if (direction == GameFrame.NORTH) {
			x = startX + (DX * draw.y) - (draw.x * DX);
			y = startY + (DY * (draw.x + draw.y))
					- (test.getHeight() * FloorTile.HEIGHT);
		} else if (direction == GameFrame.EAST) {
			int start = area.getTiles()[0].length - 1;
			x = startX + (DX * (start - draw.y)) + ((start - draw.x) * DX);
			y = startY
					+ (DY * (start - (draw.x + start - draw.y)) - (test
							.getHeight() * FloorTile.HEIGHT));
		} else if (direction == GameFrame.SOUTH) {
			x = startX + (DX * draw.y) - (draw.x * DX);
			y = startY + (DY * (draw.x + draw.y))
					- (test.getHeight() * FloorTile.HEIGHT);
		} else if (direction == GameFrame.WEST) {

		}
		test.draw(g, x, y);
	}

	/**
	 * Draw the floor of the current area to the render panel.
	 */
	private void drawFloors(Graphics g, Tile[][] tiles, List<Item> items) {
		Comparator<Item> comp = null;
		if (direction == GameFrame.NORTH) {
			comp = new NorthComparator(tiles.length, tiles[0].length);
			drawNorthFloorLayout(g, tiles);
		} else if (direction == GameFrame.EAST) {
			comp = new EastComparator(tiles.length, tiles[0].length);
			drawEastFloorLayout(g, tiles);
		} else if (direction == GameFrame.SOUTH) {
			comp = new SouthComparator(tiles.length, tiles[0].length);
			drawSouthFloorLayout(g, tiles);
		} else if (direction == GameFrame.WEST) {
			comp = new WestComparator(tiles.length, tiles[0].length);
			drawWestFloorLayout(g, tiles);
		}
		Collections.sort(items, comp);
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
				tileBoundingBoxes.add(tiles[i][j].getBoundingBox(x, y,
						new Point(i, j)));
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
						new Point(j, i)));
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
						new Point(i, j)));
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
						new Point(j, i)));
				x += DX;
				y += DY;
			}
		}
	}

	private void setupItemBoundingBoxes(List<Item> items) {
		itemBoundingBoxes = new ArrayList<BoundingBox>();
		for (Item item : items) {
			Point p = item.getPosition();
			// itemBoundingBoxes.add(item.getBoundingBox(x, y, p));
		}
	}

	private void drawBoundingBoxes(Graphics g) {
		for (BoundingBox b : tileBoundingBoxes) {
			g.fillPolygon(b);
		}
	}

	/**
	 * Draw the bounding boxes surrounding each item.
	 *
	 * @param g
	 * @param items
	 */
	/*
	 * public void drawBoxes(Graphics g, Item[][] items) { for (int i = 0; i <
	 * items.length; i++) { for (int j = 0; j < items[0].length; j++) { if
	 * (items[i][j] != null) { g.fillPolygon(itemBoundingBoxes[i][j]); } } } }
	 */

	@Override
	public void repaint() {
		if (area != null) {
			calculateConstants();
		}
		super.repaint();
	}

	// Mouse Listener Methods

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			System.out.println("Clicked: (" + e.getX() + ", " + e.getY() + ")");
			Point p = findPosition(new Point(e.getX(), e.getY()));
			if (p != null) {
				System.out.println("Area position: (" + p.x + ", " + p.y + ")");
				test.setPosition(new Point(p.x, p.y));
				//test.setX(p.x);
				//test.setY(p.y);
				// System.out.println("Test position: (" + test.getX() + ", " +
				// test.getY() + ")");
				repaint();
			} else {
				System.out.println("Position not on board.");
			}
		}
		// findItem(e.getX(), e.getY());

	}

	// unneeded mouse listener methods
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		if (GameFrame.selectedItem != null) {
			area.addItem(GameFrame.selectedItem); // wrong
			GameFrame.selectedItem = null;
		}
	}

	public void mouseExited(MouseEvent e) {
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
	private class NorthComparator implements Comparator<Item> {

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
		public int compare(Item o1, Item o2) {
			Point p1 = o1.getPosition();
			Point p2 = o2.getPosition();
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
	private class EastComparator implements Comparator<Item> {

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
		public int compare(Item o1, Item o2) {

			return 0;
		}
	}

	/**
	 * A private class that is used to sort Items in an area so that when the
	 * game view is facing south, the items are ordered from closest to
	 * furtherest away.
	 *
	 * @author David Sheridan
	 *
	 */
	private class SouthComparator implements Comparator<Item> {

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
		public int compare(Item o1, Item o2) {

			return 0;
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
	private class WestComparator implements Comparator<Item> {

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
		public int compare(Item o1, Item o2) {

			return 0;
		}
	}
}
