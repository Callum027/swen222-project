package game.world.characters;

import game.Main;
import game.exceptions.GameException;
import game.exceptions.PlayerIDNotFoundException;
import game.net.NetIO;
import game.net.Streamable;
import game.world.Position;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.MageClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;
import game.world.items.EquippedItems;
import game.world.items.MoveableItem;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents the character that the player controls. This is the context class for our strategy pattern
 * @author Nick Tran
 *
 */
public class Player extends GameCharacter implements Streamable, Attackable{

	/*
	 * the player stats
	 */
	private int health;
	private int attack;
	private int defence;

	private EquippedItems equipped; //the items that is currently equipped to the player
	private GameClass gameClass; //either Warrior, Mage or Rogue
	private Image[] images;
	private int height = 2;

	/**
	 * The constructor
	 * @param position the position the player spawns at
	 * @param name the name of our player
	 * @param uid the unique identifier of our player
	 * @param playerClass the class of our player
	 */
	public Player(Position position, String name, int uid, GameClass.playerClass playerClass){
		super(position, name, uid);
		assignClass(playerClass); //gives the player a class (behaviour)
		images = new Image[]{Main.getImage("SpriteTEST.png")};
	}

	/**
	 * Gives the player a behaviour (class)
	 * @param playerClass the enum that identifies the player's class
	 */
	public void assignClass(GameClass.playerClass playerClass){
		switch (playerClass){
			case WARRIOR:
				gameClass = new WarriorClass();
				break;
			case ROGUE:
				gameClass = new RogueClass();
				break;
			case MAGE:
				gameClass = new MageClass();
				break;
		}
	}

	@Override
	public void attack() {
		gameClass.attack();
	}

	@Override
	public void calculateDamage() {
		gameClass.calculateDamage();
	}

	@Override
	public void write(OutputStream os) throws IOException {
		NetIO.writeByte(os, (byte)super.getId());
	}

	/**
	 * gets the items that are currently equipped by the player
	 * @return an ArrayList containing the equipped items
	 */
	public EquippedItems getEquipped() {
		return equipped;
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * reads a player from the inputstream
	 * @param is the inputstream
	 * @return the player with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Player read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		Player player = Main.getGameWorld().getPlayer(id);

		if (player == null)
			throw new PlayerIDNotFoundException(id);

		return player;
	}

	/**
	 * retrieves the health of the player
	 * @return the health of the player
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * changes the player's health by the specified amount
	 * @param health
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * retrieves the player's attack stat
	 * @return the player's attack stat
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * changes the attack stat to the specified number given
	 * @param attack the attack we're changing the current stat to
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}

	/**
	 * retrieves the defence stat of the player
	 * @return the defense stat
	 */
	public int getDefence() {
		return defence;
	}

	/**
	 * changes the defence stat to the specified number given
	 * @param defence the defence we're changing the current stat to
	 */
	public void setDefence(int defence) {
		this.defence = defence;
	}

	@Override
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[0], x, y, null);

	}

	public MoveableItem getItem(int id) {
		return super.getItems().get(id);
	}
}
