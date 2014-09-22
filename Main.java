
public class Main {

	private StartWindow startWindow;
	public static GameWindow gameWindow;
	
	public Main(String arr){
		if(arr.equals("fullscreen")){
			startWindow = new StartWindow(true);
		}
		else if(arr.equals("windowed")){
			startWindow = new StartWindow(false);
		}
	}
	
	public static void main(String arr[]){
		if(arr[0].equals("fullscreen")){
			new Main(arr[0]);
		}
		else{
			new Main("windowed");
		}
	}
}
