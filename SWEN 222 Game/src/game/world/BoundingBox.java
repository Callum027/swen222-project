package game.world;

import java.awt.Polygon;

public class BoundingBox extends Polygon{

	private static final long serialVersionUID = 1L;

	private Position p;

	public BoundingBox(int[] xPoints, int[] yPoints, int nPoints, Position p){
		super(xPoints, yPoints, nPoints);
		this.p = p;
	}

	public Position getPosition(){
		return p;
	}

}
