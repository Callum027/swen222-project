package game.world.characters;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;

import game.Main;
import game.world.Interactable;
import game.world.Position;
import game.world.items.MoveableItem;

/**
 * A class for all the non-playable characters aside from the enemies and bosses of the game
 * @author Nick Tran
 *
 */
public class Merchant extends GameCharacter implements Interactable{

	private Image[] images;
	private int height = 2;

	/**
	 * The Constructor:
	 * @param position the position that the Merchant will be spawned at
	 * @param name the name of the merchant
	 * @param id the unique identifier assigned to this particular character
	 */
	public Merchant(Position position, String name, int id){
		super(position, name, id);
		images = new Image[]{Main.getImage("SpriteTEST.png")};
	}

	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * the method called when the player buys an item form the merchant
	 * @param item the item  the merchant is selling to the player
	 * @param player the player the merchant is selling to
	 */
	public void sellWares(MoveableItem item, Player player){
		player.setCats(player.getCats()-item.getWorth()); //deducts money from the player
		player.getItems().add(item);
		getItems().remove(item);
		this.setCats(this.getCats()+item.getWorth()); //increase the Merchant's money
	}

	@Override
	public void interact(Player player) {

	}

	@Override
	public void write(OutputStream os) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[0], x, y, null);

	}
}
