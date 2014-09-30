package game;

import java.awt.Cursor;

import game.ui.*;

public class Main {

	private GameFrame gameWindow;

	public Main() {

		gameWindow = new GameFrame(1280, 720, Cursor.getDefaultCursor());
	}

	public static void main(String arr[]) {
		new Main();
	}
}
