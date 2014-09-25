package game.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JWindow;

/**
 * This class is a test class and not to be used as a final product Borrowing
 * code from it for reuse is acceptable
 *
 * @author Harry
 *
 */
public class GameWindow implements ActionListener {

	private JWindow window;
	private JButton quit;
	private JFrame frame;
	private boolean fullscreen;

	/**
	 * Is called automatically from StartWindow
	 *
	 * @param gameWindowX
	 *            the width of the window
	 * @param gameWindowY
	 *            the height of the window
	 */
	public GameWindow(int gameWindowX, int gameWindowY, Cursor cursor) {
		fullscreen = false;
		frame = new JFrame("Game frame or something");
		frame.setSize(new Dimension(gameWindowX, gameWindowY));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLayout(new GridLayout(1, 2));
		JPanel panel = new JPanel();
		quit = new JButton("Quit");
		panel.add(quit);
		panel.setSize((int) (gameWindowX * 0.75), gameWindowY);
		frame.add(panel, BorderLayout.CENTER);
		quit.addActionListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		panel.setBackground(Color.GREEN);
		JPanel appPanel = new JPanel(new GridLayout(2, 1));
		frame.add(appPanel);

		InventoryPanel iP = new InventoryPanel((int) (gameWindowX * 0.25),
				gameWindowY / 2);
		iP.setSize((int) (gameWindowX * 0.25), gameWindowY / 2);
		EquipPanel eP = new EquipPanel((int) (gameWindowX * 0.25),
				gameWindowY / 2);
		eP.setSize((int) (gameWindowX * 0.25), gameWindowY / 2);
		appPanel.add(eP);
		appPanel.add(iP);

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
