package game.exceptions;

public class TargetOutOfRangeException extends GameException{

	public TargetOutOfRangeException(){
		super(false,"Cannot attack target. Target is out of range");
	}

}
