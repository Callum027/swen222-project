package game;

import game.ui.*;

public class Main {

	private StartWindow startWindow;
	public static GameWindow gameWindow;

	public Main() {

		startWindow = new StartWindow();
	}

	public static void main(String arr[]) {
		new Main();
	}
}
