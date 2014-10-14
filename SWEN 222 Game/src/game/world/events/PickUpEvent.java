package game.world.events;

import game.world.GameEvent;

public class PickUpEvent extends GameEvent{
	
	public PickUpEvent(){
		
	}

	@Override
	public Type getType() {
		return GameEvent.Type.PICK_UP;
	}

}
