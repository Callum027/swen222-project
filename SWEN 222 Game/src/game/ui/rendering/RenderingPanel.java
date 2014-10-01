package game.ui.rendering;

import game.world.Area;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class RenderingPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	// fields
	private final int WIDTH = 960;
	private final int HEIGHT = 720;
	private Area area;

	/**
	 * Constructor:
	 *
	 */
	public RenderingPanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
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
			int length = (tiles.length + tiles[0].length) - 1;
			int width = (((length - 1) * ((FloorTile.WIDTH / 2 )+ 1))) + FloorTile.WIDTH;
			int height = ((length - 1) * (FloorTile.HEIGHT / 2)) - 135;
			drawFloors(g, width, height, tiles);
		}
	}

	/**
	 *
	 */
	private void drawFloors(Graphics g, int width, int height, Tile[][] tiles){
		int dx = (FloorTile.WIDTH / 2) + 1;
		int dy = FloorTile.HEIGHT / 2;
		int startX = ((WIDTH - width) / 2) + ((tiles.length - 1) * dx);
		int startY = (HEIGHT - height) / 2;
		for(int i = 0; i < tiles.length; i++){
			int x = startX - (dx * i);
			int y = startY + (dy * i);
			for(int j = 0; j < tiles[i].length; j++){
				System.out.println("Drawing tile at ("+x+", "+y+")");
				tiles[i][j].draw(g, x, y);
				x += dx;
				y += dy;
			}
		}
	}

}
