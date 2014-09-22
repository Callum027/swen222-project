import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private Dimension screenSize;
	JTextField fieldX;
	JTextField fieldY;
	boolean fullScreen;
	private JWindow window; // makes a window rather than a frame, means it is a
							// borderless window
	private JFrame frame;
	private int gameX;
	private int gameY;
	private JButton done;
	JFrame size;

	public StartWindow(boolean fullScreen) {
		this.fullScreen = fullScreen;
		if (fullScreen) {
			startFullScreen();
		} else {
			size = new JFrame("Please enter Dimensions");
			JLabel labelX = new JLabel("Width of the window?");
			JLabel labelY = new JLabel("Height of the window?");
			JPanel panel = new JPanel();
			fieldX = new JTextField();
			fieldY = new JTextField();
			fieldX.setColumns(10);
			fieldY.setColumns(10);
			done = new JButton("Done");
			panel.add(labelX);
			panel.add(fieldX);
			panel.add(labelY);
			panel.add(fieldY);
			panel.add(done);
			done.addActionListener(this);
			size.add(panel);
			size.setSize(200, 200);
			size.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			size.setVisible(true);
			

		}
	}

	private void startWindowed() {
		frame = new JFrame("Main Menu or something");
		frame.setSize(new Dimension(gameX, gameY)); // sets the window size to
													// be the max size on the
													// screen

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
		frame.setVisible(true);

	}

	/**
	 * Makes a starting window and adds some buttons to it
	 */
	private void startFullScreen() {
		screenSize = Toolkit.getDefaultToolkit().getScreenSize(); /* gets the screen size
		*(Hopefully needs to be tested) works on my laptop, 
		*need to test uni computers
		*/
		window = new JWindow();
		window.setSize(screenSize); // sets the window size to be the max size
									// on the screen

		launch = new JButton("Something, uhh, really cool");
		quit = new JButton("Quit");
		launch.addActionListener(this); // sets up the listeners for the buttons
		quit.addActionListener(this);
		JPanel panel = new JPanel(); // makes a panel so that buttons etc can be
										// displayed on the screen.
		panel.add(launch);
		panel.add(quit);
		window.add(panel); // adds the panel to the window so that buttons etc
							// can be pressed etc.
		window.setAlwaysOnTop(true);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == launch) {
			if (fullScreen) {
				Main.gameWindow = new GameWindow(screenSize);
				window.dispose(); /* 
				*closes this window, due to it not being the only window
				*it won't close the program
				*/
			} else {
				Main.gameWindow = new GameWindow(gameX, gameY);
				frame.dispose();
			}

		}
		if (e.getSource() == quit) {
			System.exit(0);
		}
		if (e.getSource() == done) {
			try {
				gameX = Integer.parseInt(fieldX.getText());
				gameY = Integer.parseInt(fieldY.getText());
				size.dispose();
				startWindowed();
				
			} catch (NumberFormatException e1) {
				System.out.println("You entered in not a number! " + e1);
			}

		}
	}

}
