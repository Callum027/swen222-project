package game.loading;
/**
 * Red flag class
 * @author Tsun
 *
 */
public class ParserError extends Exception {
	private String message;
public ParserError(String msg){
	super();
	message = msg;
}
public String getMessage(){
	return message;
}
}
