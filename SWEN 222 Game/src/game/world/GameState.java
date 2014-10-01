package game.world;

public class GameState {
	private GameWorld world;
	private GameEvent[] newEvents;
	
	public GameState(GameWorld w, GameEvent... nes) {
		world = w;
		newEvents = new GameEvent[nes.length];
		
		System.arraycopy(nes, 0, newEvents, 0, nes.length);
	}
	
	public GameWorld getWorld() {
		return world;
	}
	
	public GameEvent[] getNewEvents() {
		GameEvent[] nes = new GameEvent[newEvents.length];
		System.arraycopy(newEvents, 0, nes, 0, newEvents.length);
		
		return nes;
	}
}
