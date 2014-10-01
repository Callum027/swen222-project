package game.ui.rendering;

import game.world.Area;
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

public class RenderingPanel extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;

	// fields
	private final int WIDTH = 960;
	private final int HEIGHT = 720;
	private Area area;
	private Polygon[][] tileBoundingBoxes;
	private Polygon[][] itemBoundingBoxes;

	private int areaLength;
	private int areaWidth;
	private int areaHeight;

	/**
	 * Constructor:
	 *
	 */
	public RenderingPanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
	}

	/**
	 * Sets the area currently active on the RenderingWindow
	 * to the specified Area.
	 *
	 * @param area
	 * 			--- current area
	 */
	public void setArea(Area area){
		this.area = area;

		areaLength = (area.getTiles().length + area.getTiles()[0].length) - 1;
		areaWidth = (((areaLength - 1) * ((FloorTile.WIDTH / 2 )+ 1))) + FloorTile.WIDTH;
		areaHeight = ((areaLength - 1) * (FloorTile.HEIGHT / 2)) - 135;

		calculateBoundingBoxes();
	}

	public void calculateBoundingBoxes(){
		tileBoundingBoxes = new Polygon[area.getTiles().length][area.getTiles()[0].length];
		itemBoundingBoxes = new Polygon[area.getTiles().length][area.getTiles()[0].length];
		
		int dx = (FloorTile.WIDTH / 2) + 1;
		int dy = FloorTile.HEIGHT / 2;
		int startX = ((WIDTH - areaWidth) / 2) + ((area.getTiles().length - 1) * dx);
		int startY = (HEIGHT - areaHeight) / 2;

		for(int i = 0; i < tileBoundingBoxes.length; i++){
			int x = startX - (dx * i);
			int y = startY + (dy * i);
			for(int j = 0; j < tileBoundingBoxes[i].length; j++){
				// calculate x and y points for bounding box of tile
				int[] xPoints = new int[]{x, x + (FloorTile.WIDTH / 2), x + FloorTile.WIDTH, x + (FloorTile.WIDTH / 2)};
				int[] yPoints = new int[]{y + (FloorTile.HEIGHT / 2), y, y + (FloorTile.HEIGHT / 2), y + FloorTile.HEIGHT};
				tileBoundingBoxes[i][j] = new Polygon(xPoints, yPoints, xPoints.length);
				
				// calculate x and y point for bounding box of item
				if(area.getItems()[i][j] != null){
					int itemHeight = area.getItems()[i][j].getHeight();
					int itemY = y - (itemHeight * FloorTile.HEIGHT);
					xPoints = new int[]{x, x + (FloorTile.WIDTH / 2), x + FloorTile.WIDTH, x + FloorTile.WIDTH, x + (FloorTile.WIDTH / 2), x};
					yPoints = new int[]{itemY + (FloorTile.HEIGHT/2), itemY, itemY + (FloorTile.HEIGHT/2),
							itemY + ((itemHeight+1)*FloorTile.HEIGHT) - (FloorTile.HEIGHT/2), itemY + ((itemHeight+1)*FloorTile.HEIGHT),
							itemY + ((itemHeight+1)*FloorTile.HEIGHT) - (FloorTile.HEIGHT/2)};
					itemBoundingBoxes[i][j] = new Polygon(xPoints, yPoints, xPoints.length);
				}
				
				x += dx;
				y += dy;
			}
		}
	}

	public Point findPosition(int x, int y){
		Point p = new Point(x, y);
		for(int i = 0; i < tileBoundingBoxes.length; i++){
			for(int j = 0; j < tileBoundingBoxes[i].length; j++){
				if(tileBoundingBoxes[i][j].contains(p)){
					return new Point(j, i);
				}
			}
		}
		return null;
	}
	
	public void findItem(int x, int y){
		Point p = new Point(x, y);
		for(int i = 0; i < tileBoundingBoxes.length; i++){
			for(int j = 0; j < tileBoundingBoxes[i].length; j++){
				if(area.getItems()[i][j] != null && itemBoundingBoxes[i][j].contains(p)){
					System.out.println(area.getItems()[i][j].getName());
				}
			}
		}
	}

	public int calculateXPosition(int drawX){
		int dx = (FloorTile.WIDTH / 2) + 1;
		int startX = ((WIDTH - areaWidth) / 2) + ((area.getTiles().length - 1) * dx);
		int x = (startX - drawX) / dx;
		return x;
	}

	public int calculateYPosition(int drawY){
		int dy = FloorTile.HEIGHT / 2;
		int startY = (HEIGHT - areaHeight) / 2;
		int y = (drawY - startY) / dy;
		return y;
	}

	/**
	 *
	 */
	@Override
	public void paintComponent(Graphics g){
		g.setColor(new Color(150, 150, 150));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(area != null){
			Tile[][] tiles = area.getTiles();
			Item[][] items = area.getItems();
			drawFloors(g, tiles, items);
		}
	}

	/**
	 *
	 */
	private void drawFloors(Graphics g, Tile[][] tiles, Item[][] items){
		int dx = (FloorTile.WIDTH / 2) + 1;
		int dy = FloorTile.HEIGHT / 2;
		int startX = ((WIDTH - areaWidth) / 2) + ((tiles.length - 1) * dx);
		int startY = (HEIGHT - areaHeight) / 2;
		for(int i = 0; i < tiles.length; i++){
			int x = startX - (dx * i);
			int y = startY + (dy * i);
			for(int j = 0; j < tiles[i].length; j++){
				//System.out.println("Drawing tile at ("+x+", "+y+")");
				tiles[i][j].draw(g, x, y);
				if(items[i][j] != null){
					items[i][j].draw(g, x, y - (items[i][j].getHeight() * FloorTile.HEIGHT));
				}
				x += dx;
				y += dy;
			}
		}
	}
	
	public void drawBoxes(Graphics g, Item[][] items){
		for(int i = 0; i < items.length; i++){
			for(int j = 0; j < items[0].length; j++){
				if(items[i][j] != null){
					g.fillPolygon(itemBoundingBoxes[i][j]);
				}
			}
		}
	}

	//Mouse Listener Methods

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked: ("+e.getX()+", "+e.getY()+")");
		Point p = findPosition(e.getX(), e.getY());
		if(p != null){
			System.out.println("Area position: ("+p.x+", "+p.y+")");
		}
		else{
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
