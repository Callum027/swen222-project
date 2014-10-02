package game.ui.rendering;

import game.Main;
import game.world.Area;
import game.world.items.Furniture;
import game.world.items.Item;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class RenderingPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	// fields
	private final int WIDTH = 960;
	private final int HEIGHT = 720;

	private final int DX = (FloorTile.WIDTH / 2) + 1;
	private final int DY = FloorTile.HEIGHT / 2;

	private Area area;
	private Polygon[][] tileBoundingBoxes;
	private Polygon[][] itemBoundingBoxes;

	private int areaLength;
	private int areaWidth;
	private int areaHeight;
	private int startX;
	private int startY;

	private Furniture test;

	/**
	 * Constructor:
	 *
	 */
	public RenderingPanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);

		test = new Furniture(0, 0, 2, "temp",
				Main.getImage("temp_character.png"), null);
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

		// calculate constant values that are use for rendering
		areaLength = (area.getTiles().length + area.getTiles()[0].length) - 1;
		areaWidth = (((areaLength - 1) * ((FloorTile.WIDTH / 2) + 1))) + FloorTile.WIDTH;
		areaHeight = ((areaLength - 1) * (FloorTile.HEIGHT / 2)) - 135;
		startX = ((WIDTH - areaWidth) / 2) + ((area.getTiles().length - 1) * DX);
		startY = (HEIGHT - areaHeight) / 2;

		calculateBoundingBoxes();
	}

	/**
	 * Calculates the bounding boxes for each tile in the game.
	 */
	public void calculateBoundingBoxes() {
		tileBoundingBoxes = new Polygon[area.getTiles().length][area.getTiles()[0].length];
		itemBoundingBoxes = new Polygon[area.getTiles().length][area.getTiles()[0].length];

		for (int i = 0; i < tileBoundingBoxes.length; i++) {
			int x = startX - (DX * i);
			int y = startY + (DY * i);
			for (int j = 0; j < tileBoundingBoxes[i].length; j++) {
				tileBoundingBoxes[i][j] = area.getTiles()[i][j].getBoundingBox(x, y);

				// calculate x and y point for bounding box of item
				if (area.getItems()[i][j] != null) {
					int itemHeight = area.getItems()[i][j].getHeight();
					int itemY = y - (itemHeight * FloorTile.HEIGHT);
					int[] xPoints = new int[] { x, x + (FloorTile.WIDTH / 2), x + FloorTile.WIDTH, x + FloorTile.WIDTH,
							x + (FloorTile.WIDTH / 2), x };
					int[] yPoints = new int[] { itemY + DY, itemY, itemY + DY,
							itemY + ((itemHeight + 1) * FloorTile.HEIGHT) - DY,
							itemY + ((itemHeight + 1) * FloorTile.HEIGHT),
							itemY + ((itemHeight + 1) * FloorTile.HEIGHT) - DY };
					itemBoundingBoxes[i][j] = new Polygon(xPoints, yPoints, xPoints.length);
				}
				x += DX;
				y += DY;
			}
		}
	}

	/**
	 * Find the position in the area that the player has clicked on, based on
	 * the coordinates of their click. Returns null if they have not clicked in
	 * the area.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public Point findPosition(int x, int y) {
		Point p = new Point(x, y);
		for (int i = 0; i < tileBoundingBoxes.length; i++) {
			for (int j = 0; j < tileBoundingBoxes[i].length; j++) {
				if (tileBoundingBoxes[i][j].contains(p)) {
					return new Point(j, i);
				}
			}
		}
		return null;
	}

	/**
	 * Find out if the user has clicked on an item
	 *
	 * @param x
	 * @param y
	 */
	public void findItem(int x, int y) {
		Point p = new Point(x, y);
		for (int i = 0; i < tileBoundingBoxes.length; i++) {
			for (int j = 0; j < tileBoundingBoxes[i].length; j++) {
				if (area.getItems()[i][j] != null
						&& itemBoundingBoxes[i][j].contains(p)) {
					System.out.println(area.getItems()[i][j].getName());
				}
			}
		}
	}

	// not used currently (doesn't work)
	public int calculateXPosition(int drawX) {
		int x = (startX - drawX) / DX;
		return x;
	}

	// not used currently (doesn't work)
	public int calculateYPosition(int drawY) {
		int y = (drawY - startY) / DY;
		return y;
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
			Item[][] items = area.getItems();
			drawFloors(g, tiles, items);

			// draw test
			int x = startX - (DX * test.getY());
			int y = startY + (DY * test.getY())
					- (test.getHeight() * FloorTile.HEIGHT);
			test.draw(g, x, y);
		}
	}

	/**
	 * Draw the floor of the current area to the render panel.
	 */
	private void drawFloors(Graphics g, Tile[][] tiles, Item[][] items) {
		for (int i = 0; i < tiles.length; i++) {
			// work out coordinates to draw tile
			int x = startX - (DX * i);
			int y = startY + (DY * i);
			for (int j = 0; j < tiles[i].length; j++) {
				// System.out.println("Drawing tile at ("+x+", "+y+")");
				tiles[i][j].draw(g, x, y);
				if (items[i][j] != null) {
					items[i][j].draw(g, x, y
							- (items[i][j].getHeight() * FloorTile.HEIGHT));
				}
				x += DX;
				y += DY;
			}
		}
	}

	/**
	 * Draw the bounding boxes surrounding each item.
	 *
	 * @param g
	 * @param items
	 */
	public void drawBoxes(Graphics g, Item[][] items) {
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[0].length; j++) {
				if (items[i][j] != null) {
					g.fillPolygon(itemBoundingBoxes[i][j]);
				}
			}
		}
	}

	// Mouse Listener Methods

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked: (" + e.getX() + ", " + e.getY() + ")");
		Point p = findPosition(e.getX(), e.getY());
		if (p != null) {
			System.out.println("Area position: (" + p.x + ", " + p.y + ")");
			test.setX(p.x);
			test.setY(p.y);
			repaint();
		} else {
			System.out.println("Position not on board.");
		}
		findItem(e.getX(), e.getY());

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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
