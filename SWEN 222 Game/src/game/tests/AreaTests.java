package game.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import game.world.Area;
import game.world.Position;
import game.world.items.Container;
import game.world.items.Equipment;
import game.world.items.Furniture;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

import org.junit.Test;

public class AreaTests {

	/*  Layout of Drawable objects in Area.
	 *
	 *    0  1  2  3  4
	 *    __ __ __ __ __
	 *  0|  |  |  |  |S2|
	 *   |__|__|__|__|__|
	 *  1|B |  |  |  |  |
	 *   |__|__|__|__|__|
	 *  2|  |  |CP|S1|  |
	 *   |__|__|__|__|__|
	 *  3|  |SH|  |C |  |
	 *   |__|__|__|__|__|
	 *  4|  |  |H |  |  |
	 *   |__|__|__|__|__|
	 */

	// fields
	private int width = 5;
	private int height = 5;

	// Items to be used in tests
	Furniture shelf1 = new Furniture(new Position(3, 2), 2, 1, "shelf", null);
	Furniture shelf2 = new Furniture(new Position(4, 0), 2, 2, "shelf", null);
	Equipment chestPlate = new Equipment(new Position(2, 2), 1, 3, "Iron-Armour2", 1, 1, 1, 1);
	Equipment helmet = new Equipment(new Position(2, 4), 1, 4, "iron-hat", 1, 1, 1, 1);
	Container chest = new Container(new Position(3, 3), 1, 5, "Chest", 0);
	Equipment boots = new Equipment(new Position(0, 1), 1, 6, "iron-boots", 1, 1, 1, 1);
	Equipment shield = new Equipment(new Position(1, 3), 1, 7, "iron-shield",1 , 1, 1, 1);

	@Test public void getNeighboursTest_1(){
		Area area = setupArea();
		Position p = new Position(1,1);
		List<Position> moveable = new ArrayList<Position>();
		moveable.add(new Position(1, 0));
		moveable.add(new Position(1, 2));
		moveable.add(new Position(0, 1));
		moveable.add(new Position(2, 1));
		Set<Position> neighbours = area.getNeighbours(p);
		if(neighbours.size() != moveable.size()){
			fail("Finding Neighbours : Incorrect number of neighbours found.");
		}

		for(Position pos : neighbours){
			if(!moveable.contains(pos)){
				fail("Finding Neighbours : "+pos+" should be a neighbour");
			}
		}
	}

	@Test public void getNeighboursTest_2(){
		Area area = setupArea();
		Position p = new Position(3,1);
		List<Position> moveable = new ArrayList<Position>();
		moveable.add(new Position(3, 0));
		moveable.add(new Position(4, 1));
		moveable.add(new Position(2, 1));
		Set<Position> neighbours = area.getNeighbours(p);
		if(neighbours.size() != moveable.size()){
			fail("Finding Neighbours : Incorrect number of neighbours found.");
		}

		for(Position pos : neighbours){
			if(!moveable.contains(pos)){
				fail("Finding Neighbours : "+pos+" should be a neighbour");
			}
		}
	}

	@Test public void getNeighboursTest_3(){
		Area area = setupArea();
		Position p = new Position(4,4);
		List<Position> moveable = new ArrayList<Position>();
		moveable.add(new Position(3, 4));
		moveable.add(new Position(4, 3));
		Set<Position> neighbours = area.getNeighbours(p);
		if(neighbours.size() != moveable.size()){
			fail("Finding Neighbours : Incorrect number of neighbours found.");
		}

		for(Position pos : neighbours){
			if(!moveable.contains(pos)){
				fail("Finding Neighbours : "+pos+" should be a neighbour");
			}
		}
	}

	@Test public void getNeighboursTest_4(){
		Area area = setupArea();
		Position p = new Position(-1,5);
		Set<Position> neighbours = area.getNeighbours(p);
		if(neighbours.size() != 0){
			fail("Finding Neighbours : no neighbours should be found.");
		}
	}

	@Test public void validMoveablePosition_1(){
		Area area = setupArea();
		Position p = new Position(0, 0);
		if(!area.isMoveablePosition(p)){
			fail("Moveable Position: "+p+" should be moveable");
		}
	}

	@Test public void validMoveablePosition_2(){
		Area area = setupArea();
		Position p = new Position(2, 4);
		if(!area.isMoveablePosition(p)){
			fail("Moveable Position: "+p+" should be moveable");
		}
	}

	@Test public void invalidMoveablePosition_1(){
		Area area = setupArea();
		Position p = new Position(-1, 6);
		if(area.isMoveablePosition(p)){
			fail("Moveable Position: "+p+" should not be moveable");
		}
	}

	@Test public void invalidMoveablePosition_2(){
		Area area = setupArea();
		Position p = new Position(4, 0);
		if(area.isMoveablePosition(p)){
			fail("Moveable Position: "+p+" should not be moveable");
		}
	}

	/**
	 * Set up an area to be used in tests.
	 *
	 * @return
	 * 		--- area
	 */
	private Area setupArea(){
		Tile[][] tiles = new FloorTile[height][width];
		Tile[][][] walls = new WallTile[4][][];
		Area area = new Area(tiles, walls, 1);
		area.addItem(shelf1);
		area.addItem(shelf2);
		area.addItem(chestPlate);
		area.addItem(helmet);
		area.addItem(chest);
		area.addItem(boots);
		area.addItem(shield);
		return area;
	}
}
