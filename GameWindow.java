import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * This class is a test class and not to be used as a final product
 * Borrowing code from it for reuse is acceptable
 * @author Harry
 *
 */
public class GameWindow implements ActionListener {

	private JWindow window;
	private JButton quit;
	private JFrame frame;
	
	/**
	 * Takes two ints to make a window rather than a dimension, this will be used for windowed games
	 * @param gameWindowX the width of the window
	 * @param gameWindowY the height of the window
	 */
	public GameWindow(int gameWindowX, int gameWindowY) {
		frame = new JFrame("Game frame or something");
		frame.setSize(gameWindowX, gameWindowY);
		frame.setVisible(true);
		JPanel panel = new JPanel();
		quit = new JButton("Quit");
		panel.add(quit);
		frame.add(panel);
		quit.addActionListener(this);
	}

	/**
	 * Called automatically from StartWindow (So far) This will be used for full screen games
	 * @param screenSize the dimension of the window (Fullscreen)
	 */
	public GameWindow(Dimension screenSize) {
		window = new JWindow();
		window.setSize(screenSize);
		window.setVisible(true); //sets the window to be visible
		JPanel panel = new JPanel();
		quit = new JButton("Quit");
		panel.add(quit);
		window.add(panel);
		window.setAlwaysOnTop(true); //means the window is always on top, useful so you can't click off by mistake
		quit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==quit){
			System.exit(0);
		}
		
	}

}
