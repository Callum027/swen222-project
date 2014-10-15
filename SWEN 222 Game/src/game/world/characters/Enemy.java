package game.world.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map.Entry;

import game.Main;
import game.exceptions.EnemyIDNotFoundException;
import game.exceptions.GameException;
import game.net.NetIO;
import game.world.Area;
import game.world.Position;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.MageClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;
import game.world.items.MoveableItem;

/**
 * The enemies within the game. When they die, they drop items and cats. This, like the Players, are also context classes for the strategy pattern
 * @author Nick Tran
 *
 */
public class Enemy extends GameCharacter implements Attackable{

	/*
	 * The enemy stats
	 */
	private int health;
	private int attack;
	private int defence;

	private GameClass gameClass; //either Warrior, Mage or Rogue
	private Image[] images;
	private int height = 2;
	private boolean isDead;

	/**
	 * The Constructor
	 * @param position the position the enemy is spawned at
	 * @param name the name of the enemy
	 * @param uid the unique identifier  that is assigned to this enemy
	 * @param playerClass the class of the enemy. This is to identify the different strategies of the enemy
	 */
	public Enemy(Position position, String name, int uid, GameClass.playerClass playerClass){
		super(position, name, uid);
		assignClass(playerClass); //gives the player a class (behaviour)
		images = new Image[]{Main.getImage("SpriteTEST.png")};
	}

	/**
	 * Gives the enemy a behaviour (class)
	 * @param playerClass the enum that identifies the player's class
	 */
	public void assignClass(GameClass.playerClass playerClass){
		switch (playerClass){
			case WARRIOR:
				gameClass = new WarriorClass(this);
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
	public void attack(Attackable target) {
		gameClass.attack(target);
	}

	@Override
	public int calculateDamage() {
		return gameClass.calculateDamage();
	}

	@Override
	/**
	 * retrieves the health of the player
	 * @return the health of the player
	 */
	public int getHealth() {
		return health;
	}

	@Override
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
	public void write(OutputStream os) throws IOException {
		super.write(os);
		NetIO.writeByte(os, (byte)super.getId());
	}

	/**
	 * reads an enemy from the inputstream
	 * @param is the inputstream
	 * @return the enemy with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Enemy read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		Enemy enemy = null;

		/*
		 * iterates over all the areas and returns the enemy with the given id
		 */
		for (Entry<Integer,Area> entry : Main.getGameWorld().getAreas().entrySet()){
			if (enemy != null){
				return enemy;
			}
			enemy = entry.getValue().getEnemy(id);
		}

		if (enemy == null)
			throw new EnemyIDNotFoundException(id);

		return enemy;
	}

	@Override
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[0], x, y, null);

	}

	public Integer getID() {
		return super.getId();
	}

	@Override
	public int getHeight() {
		return height;
	}

	public MoveableItem getItem(int id) {
		return super.getItems().get(id);
	}

	public GameCharacter.Type getType() {
		return GameCharacter.Type.ENEMY;
	}

	@Override
	public int calculateDistance(Attackable target, Attackable attacker) {
		return gameClass.calculateDistance(target, attacker);
	}

	@Override
	public boolean getIsDead() {
		return isDead;
	}

	@Override
	public void setIsDead(boolean deadOrAlive) {
		this.isDead = deadOrAlive;
	}
}
