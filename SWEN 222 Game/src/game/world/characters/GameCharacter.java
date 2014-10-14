package game.world.characters;

import game.exceptions.GameException;
import game.exceptions.InvalidGameCharacterException;
import game.exceptions.UnsupportedGameCharacterException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.BoundingBox;
import game.world.Drawable;
import game.world.Position;
import game.world.items.MoveableItem;
import game.world.tiles.FloorTile;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Represents the various game characters within our game like Enemies, NPCs and the Player
 * @author Nick Tran
 *
 */
public abstract class GameCharacter implements Streamable, Drawable{

	/*
	 * Each game character utilizes a bunch if items one way or another.
	 * The player has a list of items in their inventory
	 * The enemy has a list of items that they drop when they die
	 * The Merchant has a list of items that they can sell
	 */
	private ArrayList<MoveableItem> items;

	private final int MAXIMUM_CAPACITY = 18; //the limit on the amount of items each character can hold
	private Position position;
	private final String name;
	private int direction;
	private int height = 2; // occupies a space 2 squares high
	private final int id;
	private int cats; //the amount of money/points the player has

	/**
	 * The constructor
	 * @param position the position where we spawn the character
	 * @param name the name of the character
	 * @param id the unique identifier that is assigned to this character
	 */
	public GameCharacter(Position position, String name, int id) {
		this.position = position;
		this.name = name;
		this.setCats(0);
		this.setDirection(0);
		this.id = id;
		items = new ArrayList<MoveableItem>(MAXIMUM_CAPACITY);
	}

	/**
	 * moves the character to a specified position
	 * essentially the setter for the position field
	 * @param position the specified position
	 */
	public void moveToPosition(Position position){
		this.position = position;
	}

	/**
	 * creates a container that contains the items that the character is holding when the character dies
	 */
	public void dropItems(){

	}

	/**
	 * adds an item to the list of items that the character is holding
	 * @param item the item that we're adding
	 * @return true if the addition is successful
	 */
	public boolean addItem(MoveableItem item){
		return items.add(item);
	}

	/**
	 * removes an item to the list of items that the character is holding
	 * @param item the item that we're removing
	 * @return true if the removal is successful
	 */
	public boolean removeItem(MoveableItem item){
		return items.remove(item);
	}

	/**
	 * retrieves the name of the character
	 * @return the name of the character
	 */
	public String getName() {
		return name;
	}

	/**
	 * retrieves the items that the character is holding
	 * @return the ArrayList of items
	 */
	public ArrayList<MoveableItem> getItems() {
		return items;
	}

	/**
	 * retrieves the current position of the character
	 * @return the current position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * retrieves the direction of the direction of the character
	 * @return the direction as an integer
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * sets the direction as the specified direction
	 * @param direction the specified direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * retrieves the cats (points/money)
	 * @return the cats!
	 */
	public int getCats() {
		return cats;
	}

	/**
	 * sets the cats
	 * @param cats the cats we're setting the current cats to
	 */
	public void setCats(int cats) {
		this.cats = cats;
	}

	/**
	 * retrieves the identifier that maps to this particular character
	 * @return the id of this character
	 */
	public int getId() {
		return id;
	}

	public abstract void draw(Graphics g, int x, int y, int direction);

	public BoundingBox getBoundingBox(int x, int y, Position p){
		int itemY = y - (height * FloorTile.HEIGHT);
		int dy = FloorTile.HEIGHT / 2;
		int[] xPoints = new int[] { x, x + (FloorTile.WIDTH / 2),
				x + FloorTile.WIDTH, x + FloorTile.WIDTH,
				x + (FloorTile.WIDTH / 2), x };
		int[] yPoints = new int[] { itemY + dy, itemY, itemY + dy,
				itemY + ((height + 1) * FloorTile.HEIGHT) - dy,
				itemY + ((height + 1) * FloorTile.HEIGHT),
				itemY + ((height + 1) * FloorTile.HEIGHT) - dy };
		return new BoundingBox(xPoints, yPoints, xPoints.length, p);
	}

	public abstract GameCharacter.Type getType();

	public static GameCharacter read(InputStream is) throws IOException, GameException {
		GameCharacter.Type t = GameCharacter.Type.read(is);

		switch (t) {
			case PLAYER:
				return Player.read(is);
			case ENEMY:
				return Enemy.read(is);
			case MERCHANT:
				return Merchant.read(is);
			default:
				throw new UnsupportedGameCharacterException(t);
		}
	}

	public void write(OutputStream os) throws IOException {
		getType().write(os);
	}

	/**
	 * A Type enumeration system to uniquely identify types of GamePackets,
	 * using unique IDs.
	 *
	 * @author Callum
	 *
	 */
	public enum Type implements Streamable {
		PLAYER(0),
		ENEMY(1),
		MERCHANT(2);

		// The unique ID of the event.
		private final byte id;

		/*
		 * construct a GamePacket.Type object.
		 * @param i ID number
		 */
		private Type(int i) {
			id = (byte)i;
		}

		/**
		 * Get the type's unique ID.
		 *
		 * @return type ID
		 */
		public byte getID() {
			return id;
		}

		/**
		 * Return the GameCharacter.Type version of a given ID.
		 *
		 * @param id ID to convert
		 * @return GameCharacter.Type version
		 */
		public static Type getTypeFromID(byte id) throws GameException {
			for (Type t: values())
				if (t.getID() == id)
					return t;

			throw new InvalidGameCharacterException(id);
		}

		/**
		 * Read a GamePacket.Type from the input stream. Returns null if it fails.
		 *
		 * @param is InputStream
		 * @return GamePacket.Type
		 */
		public static Type read(InputStream is) throws IOException, GameException {
			return getTypeFromID(NetIO.readByte(is));
		}

		public void write(OutputStream os) throws IOException {
			NetIO.writeByte(os, id);
		}
	}
}
