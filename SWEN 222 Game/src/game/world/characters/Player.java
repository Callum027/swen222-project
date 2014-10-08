package game.world.characters;

import game.Main;
import game.net.NetIO;
import game.net.Streamable;
import game.world.characters.classes.GameClass;
import game.world.characters.classes.MageClass;
import game.world.characters.classes.RogueClass;
import game.world.characters.classes.WarriorClass;
import game.world.items.Equipment;
import game.world.items.EquippedItems;
import game.world.items.MoveableItem;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Represents the character that the player controls. THis is the context class for our strategy pattern
 * @author Nick Tran
 *
 */
public class Player extends GameCharacter implements Streamable, Attackable{

	private int health;
	private int attack;
	private int defence;
	private EquippedItems equipped; //the items that is currently equipped to the player
	private GameClass gameClass; //either Warrior, Mage or Rogue

	public Player(Point point, String name, int uid, GameClass.playerClass playerClass){
		super(point, name, uid);
		assignClass(playerClass); //gives the player a class (behaviour)
	}

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

	public EquippedItems getEquipped() {
		return equipped;
	}

	public static Player read(InputStream is) throws IOException {
		byte id = NetIO.readByte(is);
		return Main.getGameWorld().getPlayer(id);
	}
}
