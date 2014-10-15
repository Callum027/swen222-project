package game.ui;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * StartPanel lists the options that a user can select
 * when starting the game.
 *
 * @author David Sheridan
 *
 */
public class StartPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	// fields

	/**
	 * Constructor:
	 * Constructs an instance of a StartPanel.
	 */
	public StartPanel(){
		// initialise components for start panel
		JTextField name = new JTextField();
		JButton newGame = new JButton("New Game");
		JButton hostGame = new JButton("Host New Game");
		JButton joinGame = new JButton("Join Game");

		// add components to StartPanel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(name);
		add(newGame);
		add(hostGame);
		add(joinGame);
	}


}
