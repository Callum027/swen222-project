package game.world;

import java.awt.Point;
import java.awt.Polygon;

public class BoundingBox extends Polygon{

	private static final long serialVersionUID = 1L;
	
	private Point p;
	
	public BoundingBox(int[] xPoints, int[] yPoints, int nPoints, Point p){
		super(xPoints, yPoints, nPoints);
		this.p = p;
	}
	
	public Point getPosition(){
		return p;
	}

}
