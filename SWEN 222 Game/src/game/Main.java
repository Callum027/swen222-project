package game;

import java.awt.Cursor;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.ui.*;

public class Main {

	private GameFrame gameWindow;
	private static final String IMAGE_PATH = "ui/graphics/";

	public Main() {
		File tiles = new File("tiles.txt");
		gameWindow = new GameFrame(1280, 720, Cursor.getDefaultCursor());
	}
	

	public static Image getImage(String filename){
		java.net.URL imageURL = Main.class.getResource(IMAGE_PATH+filename);
		try{
			Image image = ImageIO.read(imageURL);
			return image;
		}catch(IOException e){throw new RuntimeException("Unable to locate "+filename);}
	}

	public static void main(String arr[]) {
		new Main();
	}
}
