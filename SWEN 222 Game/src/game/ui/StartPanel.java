package game.ui;

import java.awt.event.ActionListener;

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
	private JTextField name;

	/**
	 * Constructor:
	 * Constructs an instance of a StartPanel.
	 */
	public StartPanel(ActionListener action){
		// initialise components for start panel
		name = new JTextField();
		name.addActionListener(action);
		JButton newGame = new JButton("New Game");
		newGame.addActionListener(action);
		JButton hostGame = new JButton("Host New Game");
		hostGame.addActionListener(action);
		JButton joinGame = new JButton("Join Game");
		joinGame.addActionListener(action);

		// add components to StartPanel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(name);
		add(newGame);
		add(hostGame);
		add(joinGame);
	}

	public String getNameText(){
		return name.getText();
	}
}
