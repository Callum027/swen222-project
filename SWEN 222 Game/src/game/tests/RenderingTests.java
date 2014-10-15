package game.tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import game.ui.GameFrame;
import game.ui.rendering.RenderingPanel;
import game.world.Area;
import game.world.Drawable;
import game.world.Position;
import game.world.items.Container;
import game.world.items.Equipment;
import game.world.items.Furniture;
import game.world.tiles.FloorTile;
import game.world.tiles.Tile;
import game.world.tiles.WallTile;

/**
 * Runs tests that determine that Drawable objects are being sorted so that
 * they are ordered in the correct position for rendering and checking bounding
 * boxes.
 *
 * @author David Sheridan
 *
 */
public class RenderingTests {

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

	/**
	 * Test to check that the NorthComparator in RenderingPanel orders
	 * Drawable objects correctly.
	 */
	@Test public void northComaratorTest(){
		RenderingPanel render = setupRenderingPanel();
		RenderingPanel.NorthComparator comp = render.new NorthComparator(width, height);
		List<Drawable> drawable = setupDrawableObjectList();
		List<Drawable> order = setupNorthOrder();
		Collections.sort(drawable, comp);
		for(int i = 0; i < drawable.size(); i++){
			if(!drawable.get(i).equals(order.get(i))){
				fail("Ordering Incorrect. Expecting "+order.get(i)+", got "+drawable.get(i));
			}
		}

	}

	/**
	 * Test to check that the EastComparator in RenderingPanel orders
	 * Drawable objects correctly.
	 */
	@Test public void eastComaratorTest(){
		RenderingPanel render = setupRenderingPanel();
		RenderingPanel.EastComparator comp = render.new EastComparator(width, height);
		List<Drawable> drawable = setupDrawableObjectList();
		List<Drawable> order = setupEastOrder();
		Collections.sort(drawable, comp);
		for(int i = 0; i < drawable.size(); i++){
			if(!drawable.get(i).equals(order.get(i))){
				fail("Ordering Incorrect. Expecting "+order.get(i)+", got "+drawable.get(i));
			}
		}

	}

	/**
	 * Test to check that the SouthComparator in RenderingPanel orders
	 * Drawable objects correctly.		drawable.add(helmet);
		drawable.add(chest);
		drawable.add(shelf1);
		drawable.add(shield);
		drawable.add(shelf2);
		drawable.add(chestPlate);
		drawable.add(boots);
	 */
	@Test public void southComaratorTest(){
		RenderingPanel render = setupRenderingPanel();
		RenderingPanel.SouthComparator comp = render.new SouthComparator(width, height);
		List<Drawable> drawable = setupDrawableObjectList();
		List<Drawable> order = setupSouthOrder();
		Collections.sort(drawable, comp);
		for(int i = 0; i < drawable.size(); i++){
			if(!drawable.get(i).equals(order.get(i))){
				fail("Ordering Incorrect. Expecting "+order.get(i)+", got "+drawable.get(i));
			}
		}

	}

	/**
	 * Test to check that the WestComparator in RenderingPanel orders
	 * Drawable objects correctly.
	 */
	@Test public void westComaratorTest(){
		RenderingPanel render = setupRenderingPanel();
		RenderingPanel.WestComparator comp = render.new WestComparator(width, height);
		List<Drawable> drawable = setupDrawableObjectList();
		List<Drawable> order = setupWestOrder();
		Collections.sort(drawable, comp);
		for(int i = 0; i < drawable.size(); i++){
			if(!drawable.get(i).equals(order.get(i))){
				fail("Ordering Incorrect. Expecting "+order.get(i)+", got "+drawable.get(i));
			}
		}

	}

	/**
	 * Returns a List that contains the correct ordering of Drawable objects
	 * to be matched against the output of sorting a List containing the same
	 * items with a NorthComparator.
	 *
	 * @return
	 * 		--- list of drawable items in correct order
	 */
	private List<Drawable> setupNorthOrder(){
		List<Drawable> drawable = new ArrayList<Drawable>();
		drawable.add(helmet);
		drawable.add(chest);
		drawable.add(shelf1);
		drawable.add(shelf2);
		drawable.add(chestPlate);
		drawable.add(shield);
		drawable.add(boots);
		return drawable;
	}

	/**
	 * Returns a List that contains the correct ordering of Drawable objects
	 * to be matched against the output of sorting a List containing the same
	 * items with a EastComparator.
	 *
	 * @return
	 * 		--- list of drawable items in correct order
	 */
	private List<Drawable> setupEastOrder(){
		List<Drawable> drawable = new ArrayList<Drawable>();
		drawable.add(helmet);
		drawable.add(shield);
		drawable.add(boots);
		drawable.add(chestPlate);
		drawable.add(chest);
		drawable.add(shelf1);
		drawable.add(shelf2);
		return drawable;
	}

	/**
	 * Returns a List that contains the correct ordering of Drawable objects
	 * to be matched against the output of sorting a List containing the same
	 * items with a SouthComparator.
	 *
	 * @return
	 * 		--- list of drawable items in correct order
	 */
	private List<Drawable> setupSouthOrder(){
		List<Drawable> drawable = new ArrayList<Drawable>();
		drawable.add(boots);
		drawable.add(shelf2);
		drawable.add(chestPlate);
		drawable.add(shield);
		drawable.add(shelf1);
		drawable.add(helmet);
		drawable.add(chest);
		return drawable;
	}

	/**
	 * Returns a List that contains the correct ordering of Drawable objects
	 * to be matched against the output of sorting a List containing the same
	 * items with a WestComparator.
	 *
	 * @return
	 * 		--- list of drawable items in correct order
	 */
	private List<Drawable> setupWestOrder(){
		List<Drawable> drawable = new ArrayList<Drawable>();
		drawable.add(shelf2);
		drawable.add(shelf1);
		drawable.add(chestPlate);
		drawable.add(chest);
		drawable.add(boots);
		drawable.add(helmet);
		drawable.add(shield);
		return drawable;
	}

	/**
	 * Sets up a list of Drawable objects to be sorted using a Comparator.
	 * @return
	 */
	private List<Drawable> setupDrawableObjectList(){
		List<Drawable> drawable = new ArrayList<Drawable>();
		drawable.add(shelf1);
		drawable.add(shelf2);
		drawable.add(chestPlate);
		drawable.add(helmet);
		drawable.add(chest);
		drawable.add(boots);
		drawable.add(shield);
		return drawable;
	}

	/**
	 * Returns a RenderingPanel to be used in various tests.
	 *
	 * @return
	 * 		--- rendering panel
	 */
	private RenderingPanel setupRenderingPanel(){
		RenderingPanel render = new RenderingPanel(GameFrame.NORTH);
		return render;
	}
}
