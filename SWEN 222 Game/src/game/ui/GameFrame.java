package game.ui;

import game.ui.application.EquipPanel;
import game.ui.application.InventoryPanel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Makes the frame that the game is going to run in
 *
 * @author Harry
 *
 */
public class GameFrame implements ActionListener {

	private JButton quit;
	private JFrame frame;

	/**
	 * Is called automatically from Main
	 *
	 * @param gameWindowX
	 *            the width of the window
	 * @param gameWindowY
	 *            the height of the window
	 * @param cursor
	 *            enables the game to run with a custom cursor
	 */
	public GameFrame(int gameWindowX, int gameWindowY, Cursor cursor) {
		frame = new JFrame("Game frame or something");
		//frame.setLocationRelativeTo(null); //makes the frame appear in the middle of the screen rather than in the top left corner
		frame.setResizable(false);
		frame.setLayout(new FlowLayout()); //sets the frame to have a layout so that the screens are in proportion
		JPanel panel = new JPanel();
		quit = new JButton("Quit");
		panel.add(quit);
		panel.setPreferredSize(new Dimension((int) (gameWindowX * 0.75), gameWindowY));

		frame.add(panel);
		quit.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		panel.setBackground(Color.cyan);
		JPanel appPane  = new JPanel();
		appPane.setLayout(new BoxLayout(appPane, BoxLayout.Y_AXIS));
		InventoryPanel iP = new InventoryPanel();
		EquipPanel eP = new EquipPanel();
		appPane.add(eP);
		appPane.add(iP);
		frame.add(appPane);
		frame.setCursor(cursor);
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			int quit = getQuitCommand();
			if (quit == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

	}

	/**
	 * A simple command that promts the dialog box to quit the game only ever called after quit is pressed
	 * @return an int from the dialog box that was choosen
	 */
	private int getQuitCommand() {

		return JOptionPane.showOptionDialog(frame,
				"Are you sure you want to quit?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, null, null);

	}

}
