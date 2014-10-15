package game.ui;

import game.world.items.MoveableItem;

import java.awt.event.MouseEvent;

public interface GameComponent {

	public void mouseClicked(GameFrame frame, MouseEvent e);
	public void mouseReleased(GameFrame frame, MouseEvent e);
	public void mousePressed(GameFrame frame, MouseEvent e);
	public int addItem(MoveableItem selectedItem);

}
