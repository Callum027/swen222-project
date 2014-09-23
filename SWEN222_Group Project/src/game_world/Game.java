package game_world;

/**
 * Our main game engine. Helps us to run the game. 
 * Communicates closely with the server. 
 * Acts as an "Interface" for the Game World. 
 * @author Nick Tran
 */
public class Game implements Runnable{
	
	public static boolean running = false;
	public Thread gameThread; 	//Threads help our game to multitask.
	
	/**
	 * Starts the game and the game thread.
	 */
	public synchronized void  start(){
		if (running){
			return; 
		}
		running = true; 
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/**
	 * Stops the game and waits for the game thread to terminate. 
	 */
	public synchronized void stop(){
		if (!running){
			return;
		}
		running = false;
		try {
			gameThread.join(); //Waits for the thread to die. 
		}catch (InterruptedException e){e.printStackTrace();}
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime(); //current time in nanoseconds
		final double amountOfTicks = 60D; //The D converts the 60 to a double.
		double threshold = 1_000_000_000 / amountOfTicks; //How much we want the timer to tick per second. 
		double delta = 0;
		//loop to limit our ticks to 60 times per second. Ticks every time delta is equal to 1.
		//delta is only equal to 1 when 1/60th of a second has passed so it ticks 60 times per second. 
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / threshold;
			lastTime = now;
			if (delta >= 1){ 
				tick();
				delta--; //resets delta.
			}
			render(); //renders as fast as we can. 
		}
		stop();
	}

	/**
	 * Update all the game components. 
	 * Update all of the inner-workings of the game. 
	 */
	private void tick() {
		
		
	}
	
	/**
	 * Draw all of the objects and components of the game.
	 */
	private void render() {
		
		
	}
	
	/**
	 * This is the main method.
	 * It is used to create and run the game.
	 * @param args Unused. 
	 */
	public static void main(String[] args) {
		Game game = new Game(); 
	}
}
