package game.ui;

import game.ui.application.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
		frame.setSize(new Dimension(gameWindowX, gameWindowY));
		frame.setLocationRelativeTo(null); //makes the frame appear in the middle of the screen rather than in the top left corner
		frame.setResizable(false);
		frame.setLayout(new GridBagLayout()); //sets the frame to have a layout so that the screens are in proportion
		GridBagConstraints c = new GridBagConstraints(); //allows for constraints to be put on the layout
		JPanel panel = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = gameWindowY;
		c.weightx = 3.0;
		c.gridx = 0;
		c.gridwidth = 2;
		c.gridy = 1;
		c.gridheight = 3;
		quit = new JButton("Quit");
		panel.add(quit);
		panel.setSize((int) (gameWindowX * 0.75), gameWindowY);
		frame.add(panel, c);
		quit.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		panel.setBackground(Color.GREEN);

		InventoryPanel iP = new InventoryPanel((int) (gameWindowX * 0.25),
				gameWindowY / 2);
		iP.setSize((int) (gameWindowX * 0.25), gameWindowY / 2);

		EquipPanel eP = new EquipPanel((int) (gameWindowX * 0.25),
				gameWindowY / 2);
		eP.setSize((int) (gameWindowX * 0.25), gameWindowY / 2);
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.ipady = 480;
		c.gridwidth = 1;
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		frame.add(eP, c);
		c.ipady = 240;
		c.weighty = 0.0;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = 1;
		frame.add(iP, c);
		frame.setCursor(cursor);
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
