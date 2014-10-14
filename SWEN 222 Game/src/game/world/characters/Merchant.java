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
import game.exceptions.MerchantIDNotFoundException;
import game.net.NetIO;
import game.world.Area;
import game.world.Position;
import game.world.items.MoveableItem;

/**
 * A class for all the non-playable characters aside from the enemies and bosses of the game
 * @author Nick Tran
 *
 */
public class Merchant extends GameCharacter{

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

	public void interact(Player player) {

	}

	@Override
	public void write(OutputStream os) throws IOException {
		super.write(os);
		NetIO.writeByte(os, (byte)super.getId());
	}

	/**
	 * reads a merchant from the inputstream
	 * @param is the inputstream
	 * @return the merchant with the given id that is received form the inputstream
	 * @throws IOException
	 * @throws GameException
	 */
	public static Merchant read(InputStream is) throws IOException, GameException {
		byte id = NetIO.readByte(is);
		Merchant merchant = null;

		/*
		 * iterates over all the areas and returns the merchant with the given id
		 */
		for (Entry<Integer,Area> entry : Main.getGameWorld().getAreas().entrySet()){
			if (merchant != null){
				return merchant;
			}
			merchant = entry.getValue().getMerchant(id);
		}

		if (merchant == null)
			throw new MerchantIDNotFoundException(id);

		return merchant;
	}

	@Override
	public void draw(Graphics g, int x, int y, int direction) {
		g.drawImage(images[0], x, y, null);

	}

	public GameCharacter.Type getType() {
		return GameCharacter.Type.MERCHANT;
	}
}
