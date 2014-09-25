package game.ui;

import game.Main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is a test class and is not to be used as a final product Borrowing
 * code from it for reuse is acceptable
 *
 * @author Harry
 *
 */
public class StartWindow implements ActionListener {

	private JButton launch;
	private JButton quit;
	private JWindow window; // makes a window rather than a frame, means it is
							// borderless
	private JFrame frame;
	private final int gameX = 1280;
	private final int gameY = 720;
	private JButton done;
	private JFrame size;
	private Cursor cursor;

	public StartWindow() {
		frame = new JFrame("Main Menu or something");
		frame.setSize(new Dimension(gameX, gameY)); // sets the window size to
													// be the max size on the
													// screen
		frame.setLocationRelativeTo(null);
		launch = new JButton("Something, uhh, really cool");
		quit = new JButton("Quit");
		launch.addActionListener(this); // sets up the listeners for the buttons
		quit.addActionListener(this);
		JPanel panel = new JPanel(); // makes a panel so that buttons etc can be
										// displayed on the screen.
		panel.add(launch);
		panel.add(quit);
		frame.add(panel); // adds the panel to the window so that buttons etc
							// can be pressed etc.
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		panel.setBackground(Color.white);
		frame.setCursor(cursor);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == launch) {

			Main.gameWindow = new GameWindow(gameX, gameY, cursor);
			frame.dispose();
		}

		if (e.getSource() == quit) {
			int quit = getQuitCommand();
			if (quit == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			if (window != null) {
				window.toFront();
			}
		}

	}

	private int getQuitCommand() {

		return JOptionPane.showOptionDialog(frame,
				"Are you sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);
	}
}
