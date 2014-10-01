package game.loading;

import game.world.GameWorld;

import java.io.File;
import java.util.Scanner;
/**
 * This Class holds all the methods for loading a world from an xml file.(will make comments look swanky later.
 * @author Tsun
 *
 */
public class Parser {
public GameWorld parseWorld(Scanner scan){
	try{
	scan.useDelimiter("\\s*(?=<)|(?<=>)\\s*");
	if(!scan.hasNext()) throw new ParserError("Empty xml file.");
	//shouldn't get here if there is no next.
	GameWorld world = new GameWorld();
	if(!scan.next().equals("World"))throw new ParserError("No World declaration.");
	while(scan.hasNext()){
		String temp = scan.next();
		if(temp.equals("World")) break;
		world.addArea(parseArea(scan));
	}
	return world;
	}catch(ParserError pError){
	System.out.println("Parser Error: "+pError.getMessage());
}
	return null;
}

private File parseArea(Scanner scan) {
	try{
		if(!scan.next().equals("Area"))throw new ParserError("Invalid declaration, should be an Area.");
		
	}catch(ParserError pError){
		System.out.println("Parser Error: "+pError.getMessage());
	}
	return null;
}
}